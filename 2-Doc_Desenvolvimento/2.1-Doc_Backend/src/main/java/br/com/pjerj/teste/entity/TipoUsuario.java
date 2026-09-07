package br.com.pjerj.teste.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TIPO_USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {

    @Id
    @Column(name = "ID_TIPOUSUARIO")
    private Long idTipoUsuario;

    @Column(name = "ORIGEM", length = 1, nullable = false)
    private String origem;

    @Column(name = "DESCR", length = 100, nullable = false)
    private String descricao;
}
