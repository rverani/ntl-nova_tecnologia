export interface Usuario {
  idUsuario: number;
  nomeUsuario: string;
  matriculaUsuario: string;
  dataNascimento: string;
  email: string;
  origem: string;
  descricaoOrigem: string;
}

export const TIPOS_ORIGEM = [
  { codigo: 'M', descricao: 'Magistrado' },
  { codigo: 'F', descricao: 'Funcionario' },
  { codigo: 'T', descricao: 'Terceirizado' },
  { codigo: 'A', descricao: 'Aposentado' },
  { codigo: 'P', descricao: 'Pensionista' },
  { codigo: 'C', descricao: 'Cotista' },
  { codigo: 'E', descricao: 'Externo' }
];
