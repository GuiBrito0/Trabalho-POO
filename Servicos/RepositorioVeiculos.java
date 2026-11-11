package Servicos;

import java.util.ArrayList;

import Entidades.Veiculo;

public class RepositorioVeiculos implements Repositorio{
    private ArrayList<Veiculo> veiculos = new ArrayList<>();
    
    @Override 
    public void adicionar(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    @Override 
    public boolean remover(String placa) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equals(placa)) {
                veiculos.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override 
    public Veiculo buscarPorPlaca(String placa) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equals(placa)) {
                return veiculos.get(i);
            }
        }
        return null;
    }

    @Override 
    public ArrayList<Veiculo> listar() {
        return veiculos;
    }

}
