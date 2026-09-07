import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../core/usuario.service';
import { Usuario } from '../core/usuario.model';

@Component({
  selector: 'app-usuario-busca',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-busca.component.html',
  styleUrl: './usuario-busca.component.css'
})
export class UsuarioBuscaComponent {

  id: number | null = null;
  usuario: Usuario | null = null;
  buscando = false;
  mensagemErro = '';
  jaBuscou = false;

  constructor(private usuarioService: UsuarioService) {}

  buscar(): void {
    if (this.id === null) {
      return;
    }

    this.buscando = true;
    this.jaBuscou = true;
    this.mensagemErro = '';
    this.usuario = null;

    this.usuarioService.buscarPorId(this.id).subscribe({
      next: (usuario) => {
        this.usuario = usuario;
        this.buscando = false;
      },
      error: (err) => {
        this.buscando = false;
        this.mensagemErro = err?.error?.mensagem || 'Usuario nao encontrado.';
        console.error(err);
      }
    });
  }
}
