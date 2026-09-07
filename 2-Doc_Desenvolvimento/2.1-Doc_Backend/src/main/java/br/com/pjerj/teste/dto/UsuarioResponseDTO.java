package br.com.pjerj.teste.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nomeUsuario;
    private String matriculaUsuario;
    private LocalDate dataNascimento;
    private String email;
    private String origem;
    private String descricaoOrigem;
}
