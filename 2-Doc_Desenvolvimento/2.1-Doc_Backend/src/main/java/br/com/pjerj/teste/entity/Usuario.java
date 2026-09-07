package br.com.pjerj.teste.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USU")
    private Long idUsuario;

    @Column(name = "NOME_USU", length = 150, nullable = false)
    private String nomeUsuario;

    @Column(name = "MATR_USU", length = 30)
    private String matriculaUsuario;

    @Column(name = "DATA_NASC")
    private LocalDate dataNascimento;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "ORIGEM", length = 1, nullable = false)
    private String origem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORIGEM", referencedColumnName = "ORIGEM", insertable = false, updatable = false)
    private TipoUsuario tipoUsuario;
}
