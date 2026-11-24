package Servicos;

import java.util.ArrayList;

import Entidades.Veiculo;
import Excecoes.RegistroDuplicadoException;

public class RepositorioVeiculos implements Repositorio {
    private ArrayList<Veiculo> veiculos = new ArrayList<>();

    @Override
    public void adicionar(Veiculo veiculo) {
        if (buscarPorPlaca(veiculo.getPlaca()) != null) {
            throw new RegistroDuplicadoException("Ja existe um veiculo com a placa " + veiculo.getPlaca() + ".");
        }
        veiculos.add(veiculo);
    }

    @Override
    public boolean remover(String placa) {
        Veiculo v = buscarPorPlaca(placa);
        if (v == null) {
            return false;
        }
        veiculos.remove(v);
        return true;
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        String procurada = Veiculo.normalizarPlaca(placa);
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equals(procurada)) {
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
