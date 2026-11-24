package Servicos;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Locacao;
import Entidades.Veiculo;

public class RepositorioLocacoesEmMemoria implements RepositorioLocacoes {
    private ArrayList<Locacao> locacoes = new ArrayList<>();

    @Override
    public void adicionar(Locacao locacao) {
        locacoes.add(locacao);
    }

    @Override
    public ArrayList<Locacao> listar() {
        return locacoes;
    }

    @Override
    public Locacao abertaDoVeiculo(String placa) {
        String procurada = Veiculo.normalizarPlaca(placa);
        for (int i = 0; i < locacoes.size(); i++) {
            Locacao l = locacoes.get(i);
            if (l.isAberta() && l.getVeiculo().getPlaca().equals(procurada)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public boolean clienteTemLocacaoAberta(String cpf) {
        String procurado = Cliente.normalizarCpf(cpf);
        for (int i = 0; i < locacoes.size(); i++) {
            Locacao l = locacoes.get(i);
            if (l.isAberta() && l.getCliente().getCpf().equals(procurado)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Locacao> doCliente(String cpf) {
        String procurado = Cliente.normalizarCpf(cpf);
        ArrayList<Locacao> resultado = new ArrayList<>();
        for (int i = 0; i < locacoes.size(); i++) {
            if (locacoes.get(i).getCliente().getCpf().equals(procurado)) {
                resultado.add(locacoes.get(i));
            }
        }
        return resultado;
    }
}
