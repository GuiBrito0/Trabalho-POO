package Servicos;

import java.util.ArrayList;

import Entidades.Cliente;
import Excecoes.RegistroDuplicadoException;

public class RepositorioClientesEmMemoria implements RepositorioClientes {
    private ArrayList<Cliente> clientes = new ArrayList<>();

    @Override
    public void adicionar(Cliente cliente) {
        if (buscarPorCpf(cliente.getCpf()) != null) {
            throw new RegistroDuplicadoException("Ja existe um cliente com o CPF " + cliente.getCpf() + ".");
        }
        clientes.add(cliente);
    }

    @Override
    public boolean remover(String cpf) {
        Cliente c = buscarPorCpf(cpf);
        if (c == null) {
            return false;
        }
        clientes.remove(c);
        return true;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        String procurado = Cliente.normalizarCpf(cpf);
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(procurado)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> listar() {
        return clientes;
    }
}
