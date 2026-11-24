package Servicos;

import java.util.ArrayList;

import Entidades.Locacao;

public interface RepositorioLocacoes {
    void adicionar(Locacao locacao);

    ArrayList<Locacao> listar();

    Locacao abertaDoVeiculo(String placa);

    boolean clienteTemLocacaoAberta(String cpf);

    ArrayList<Locacao> doCliente(String cpf);
}
