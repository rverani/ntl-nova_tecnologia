package br.com.pjerj.teste.repository;

import br.com.pjerj.teste.dto.UsuarioResponseDTO;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Chama a procedure PRC_LISTAR_USUARIO_POR_ORIGEM, que retorna um SYS_REFCURSOR
 * com os usuarios de uma determinada origem.
 *
 * Os parametros sao declarados explicitamente (withoutProcedureColumnMetaDataAccess)
 * porque a introspeccao automatica de metadata do Oracle pode falhar dependendo de
 * schema/permissoes, gerando uma chamada sem parametros (ORA erro / bad SQL grammar).
 *
 * Como o parametro de saida e um REF CURSOR sem RowMapper explicito, o Spring o
 * converte automaticamente em uma List<Map<String, Object>> (uma linha = um mapa
 * coluna -> valor), e nao em um SqlRowSet.
 */
@Repository
public class UsuarioProcedureRepository {

    private final SimpleJdbcCall simpleJdbcCall;

    public UsuarioProcedureRepository(DataSource dataSource) {
        this.simpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("PRC_LISTAR_USUARIO_POR_ORIGEM")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_ORIGEM", Types.VARCHAR),
                        new SqlOutParameter("P_CURSOR", OracleTypes.CURSOR)
                );
    }

    @SuppressWarnings("unchecked")
    public List<UsuarioResponseDTO> buscarPorOrigem(String origem) {
        Map<String, Object> params = Map.of("P_ORIGEM", origem);

        Map<String, Object> out = simpleJdbcCall.execute(params);
        List<Map<String, Object>> linhas = (List<Map<String, Object>>) out.get("P_CURSOR");

        List<UsuarioResponseDTO> usuarios = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            usuarios.add(UsuarioResponseDTO.builder()
                    .idUsuario(toLong(linha.get("ID_USU")))
                    .nomeUsuario((String) linha.get("NOME_USU"))
                    .matriculaUsuario((String) linha.get("MATR_USU"))
                    .dataNascimento(toLocalDate(linha.get("DATA_NASC")))
                    .email((String) linha.get("EMAIL"))
                    .origem((String) linha.get("ORIGEM"))
                    .descricaoOrigem((String) linha.get("DESCR"))
                    .build());
        }
        return usuarios;
    }

    private Long toLong(Object valor) {
        return valor != null ? ((Number) valor).longValue() : null;
    }

    private LocalDate toLocalDate(Object valor) {
        if (valor == null) {
            return null;
        }
        if (valor instanceof java.sql.Date d) {
            return d.toLocalDate();
        }
        if (valor instanceof Timestamp t) {
            return t.toLocalDateTime().toLocalDate();
        }
        if (valor instanceof LocalDate ld) {
            return ld;
        }
        if (valor instanceof Date d) {
            return new java.sql.Date(d.getTime()).toLocalDate();
        }
        return null;
    }
}
