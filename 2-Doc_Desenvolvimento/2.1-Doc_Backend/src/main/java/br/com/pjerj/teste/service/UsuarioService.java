package br.com.pjerj.teste.service;

import br.com.pjerj.teste.dto.UsuarioRequestDTO;
import br.com.pjerj.teste.dto.UsuarioResponseDTO;
import br.com.pjerj.teste.entity.TipoUsuario;
import br.com.pjerj.teste.entity.Usuario;
import br.com.pjerj.teste.exception.ResourceNotFoundException;
import br.com.pjerj.teste.repository.TipoUsuarioRepository;
import br.com.pjerj.teste.repository.UsuarioProcedureRepository;
import br.com.pjerj.teste.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final UsuarioProcedureRepository usuarioProcedureRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                           TipoUsuarioRepository tipoUsuarioRepository,
                           UsuarioProcedureRepository usuarioProcedureRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.usuarioProcedureRepository = usuarioProcedureRepository;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO request) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByOrigem(request.getOrigem())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Origem invalida: " + request.getOrigem()));

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(request.getNomeUsuario());
        usuario.setMatriculaUsuario(request.getMatriculaUsuario());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setEmail(request.getEmail());
        usuario.setOrigem(request.getOrigem());

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo, tipoUsuario.getDescricao());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado: " + id));

        String descricao = tipoUsuarioRepository.findByOrigem(usuario.getOrigem())
                .map(TipoUsuario::getDescricao)
                .orElse(null);

        return toResponse(usuario, descricao);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorOrigem(String origem) {
        return usuarioProcedureRepository.buscarPorOrigem(origem);
    }

    private UsuarioResponseDTO toResponse(Usuario usuario, String descricaoOrigem) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nomeUsuario(usuario.getNomeUsuario())
                .matriculaUsuario(usuario.getMatriculaUsuario())
                .dataNascimento(usuario.getDataNascimento())
                .email(usuario.getEmail())
                .origem(usuario.getOrigem())
                .descricaoOrigem(descricaoOrigem)
                .build();
    }
}
