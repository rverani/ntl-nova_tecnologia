import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../core/usuario.service';
import { TIPOS_ORIGEM, Usuario } from '../core/usuario.model';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-list.component.html',
  styleUrl: './usuario-list.component.css'
})
export class UsuarioListComponent {

  tiposOrigem = TIPOS_ORIGEM;
  origemSelecionada = 'E';
  usuarios: Usuario[] = [];
  carregando = false;
  mensagemErro = '';

  constructor(private usuarioService: UsuarioService) {
    this.buscar();
  }

  buscar(): void {
    this.carregando = true;
    this.mensagemErro = '';

    this.usuarioService.listarPorOrigem(this.origemSelecionada).subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.carregando = false;
      },
      error: (err) => {
        this.mensagemErro = 'Nao foi possivel carregar os usuarios. Verifique se a API esta rodando.';
        this.carregando = false;
        console.error(err);
      }
    });
  }
}
