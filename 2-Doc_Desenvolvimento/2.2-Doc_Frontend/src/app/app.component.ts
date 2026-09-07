import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioListComponent } from './usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './usuario-form/usuario-form.component';
import { UsuarioBuscaComponent } from './usuario-busca/usuario-busca.component';

type Tela = 'lista' | 'cadastro' | 'busca';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, UsuarioListComponent, UsuarioFormComponent, UsuarioBuscaComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  telaAtual: Tela = 'lista';

  irPara(tela: Tela): void {
    this.telaAtual = tela;
  }
}
