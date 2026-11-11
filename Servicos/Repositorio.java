package Servicos;

import java.util.ArrayList;

import Entidades.Veiculo;

public interface Repositorio {
    void adicionar(Veiculo veiculo);
    boolean remover (String placa);
    Veiculo buscarPorPlaca (String placa);
    ArrayList<Veiculo> listar();
}
