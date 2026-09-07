package br.com.pjerj.teste.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequestDTO {

    @NotBlank(message = "Nome do usuario e obrigatorio")
    @Schema(example = "Viviane Goes Delzi")
    private String nomeUsuario;

    @Schema(example = "RJ162042")
    private String matriculaUsuario;

    @NotNull(message = "Data de nascimento e obrigatoria")
    @Schema(example = "2025-01-13")
    private LocalDate dataNascimento;

    @Email(message = "E-mail invalido")
    @Schema(example = "teste@teste.com.br")
    private String email;

    @NotBlank(message = "Origem e obrigatoria")
    @Schema(example = "E", description = "Codigo do tipo de usuario: M, F, T, A, P, C, E")
    private String origem;
}
