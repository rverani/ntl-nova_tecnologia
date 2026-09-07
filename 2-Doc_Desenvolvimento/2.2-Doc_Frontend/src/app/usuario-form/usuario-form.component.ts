import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../core/usuario.service';
import { TIPOS_ORIGEM, Usuario } from '../core/usuario.model';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-form.component.html',
  styleUrl: './usuario-form.component.css'
})
export class UsuarioFormComponent {

  tiposOrigem = TIPOS_ORIGEM;

  form: Partial<Usuario> = {
    nomeUsuario: '',
    matriculaUsuario: '',
    dataNascimento: '',
    email: '',
    origem: 'E'
  };

  salvando = false;
  mensagemSucesso = '';
  mensagemErro = '';

  constructor(private usuarioService: UsuarioService) {}

  salvar(): void {
    this.salvando = true;
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.usuarioService.cadastrar(this.form).subscribe({
      next: (usuario) => {
        this.salvando = false;
        this.mensagemSucesso = `Usuario cadastrado com sucesso! ID: ${usuario.idUsuario}`;
        this.limparFormulario();
      },
      error: (err) => {
        this.salvando = false;
        this.mensagemErro = err?.error?.mensagem || 'Nao foi possivel cadastrar o usuario.';
        console.error(err);
      }
    });
  }

  private limparFormulario(): void {
    this.form = {
      nomeUsuario: '',
      matriculaUsuario: '',
      dataNascimento: '',
      email: '',
      origem: 'E'
    };
  }
}
