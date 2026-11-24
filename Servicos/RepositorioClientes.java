package Servicos;

import java.util.ArrayList;

import Entidades.Cliente;

public interface RepositorioClientes {
    void adicionar(Cliente cliente);

    boolean remover(String cpf);

    Cliente buscarPorCpf(String cpf);

    ArrayList<Cliente> listar();
}
