import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from './usuario.model';

// Ajuste conforme o ambiente. Em producao, prefira um proxy/gateway
// em vez de expor usuario/senha no front-end.
const API_BASE_URL = 'http://localhost:8081/api';
const API_USER = 'SYSTEM';
const API_PASS = '121291';

@Injectable({ providedIn: 'root' })
export class UsuarioService {

  constructor(private http: HttpClient) {}

  private authHeaders(): HttpHeaders {
    const token = btoa(`${API_USER}:${API_PASS}`);
    return new HttpHeaders({ Authorization: `Basic ${token}` });
  }

  listarPorOrigem(origem: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${API_BASE_URL}/usuarios`, {
      headers: this.authHeaders(),
      params: { origem }
    });
  }

  buscarPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${API_BASE_URL}/usuarios/${id}`, {
      headers: this.authHeaders()
    });
  }

  cadastrar(usuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.post<Usuario>(`${API_BASE_URL}/usuarios`, usuario, {
      headers: this.authHeaders()
    });
  }
}
