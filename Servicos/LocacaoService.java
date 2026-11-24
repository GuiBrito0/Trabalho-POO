package Servicos;

import java.time.LocalDate;
import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Locacao;
import Entidades.Veiculo;
import Excecoes.ClienteMenorDeIdadeException;
import Excecoes.CnhIncompativelException;
import Excecoes.NaoEncontradoException;
import Excecoes.RegraDeNegocioException;
import Excecoes.VeiculoIndisponivelException;

public class LocacaoService {
    private static final int IDADE_MINIMA = 21;

    private Repositorio veiculos;
    private RepositorioClientes clientes;
    private RepositorioLocacoes locacoes;

    public LocacaoService(Repositorio veiculos, RepositorioClientes clientes, RepositorioLocacoes locacoes) {
        this.veiculos = veiculos;
        this.clientes = clientes;
        this.locacoes = locacoes;
    }

    public Locacao alugar(String cpf, String placa, LocalDate retirada, int dias) {
        Cliente cliente = buscarCliente(cpf);
        Veiculo veiculo = buscarVeiculo(placa);

        if (locacoes.abertaDoVeiculo(veiculo.getPlaca()) != null) {
            throw new VeiculoIndisponivelException("O veiculo " + veiculo.getPlaca() + " ja esta alugado.");
        }
        if (!veiculo.aceitaCnh(cliente.getCnh())) {
            throw new CnhIncompativelException("A CNH categoria " + cliente.getCnh() + " nao permite alugar "
                    + veiculo.getTipo().toLowerCase() + ".");
        }
        if (cliente.idadeEm(retirada) < IDADE_MINIMA) {
            throw new ClienteMenorDeIdadeException("O cliente precisa ter " + IDADE_MINIMA + " anos ou mais.");
        }

        Locacao locacao = new Locacao(cliente, veiculo, retirada, dias);
        locacoes.adicionar(locacao);
        return locacao;
    }

    public Locacao devolver(String placa, LocalDate data) {
        Locacao locacao = locacoes.abertaDoVeiculo(placa);
        if (locacao == null) {
            throw new NaoEncontradoException("Nao ha locacao aberta para esse veiculo.");
        }
        locacao.devolver(data);
        return locacao;
    }

    public void excluirVeiculo(String placa) {
        Veiculo veiculo = buscarVeiculo(placa);
        if (locacoes.abertaDoVeiculo(veiculo.getPlaca()) != null) {
            throw new VeiculoIndisponivelException("Nao e possivel excluir um veiculo alugado.");
        }
        veiculos.remover(veiculo.getPlaca());
    }

    public void excluirCliente(String cpf) {
        Cliente cliente = buscarCliente(cpf);
        if (locacoes.clienteTemLocacaoAberta(cliente.getCpf())) {
            throw new RegraDeNegocioException("Nao e possivel excluir um cliente com locacao aberta.");
        }
        clientes.remover(cliente.getCpf());
    }

    public ArrayList<Veiculo> veiculosDisponiveis() {
        ArrayList<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo v : veiculos.listar()) {
            if (locacoes.abertaDoVeiculo(v.getPlaca()) == null) {
                disponiveis.add(v);
            }
        }
        return disponiveis;
    }

    public ArrayList<Locacao> locacoesAbertas() {
        ArrayList<Locacao> abertas = new ArrayList<>();
        for (Locacao l : locacoes.listar()) {
            if (l.isAberta()) {
                abertas.add(l);
            }
        }
        return abertas;
    }

    public ArrayList<Locacao> historicoDoCliente(String cpf) {
        Cliente cliente = buscarCliente(cpf);
        return locacoes.doCliente(cliente.getCpf());
    }

    private Cliente buscarCliente(String cpf) {
        Cliente cliente = clientes.buscarPorCpf(cpf);
        if (cliente == null) {
            throw new NaoEncontradoException("Cliente nao encontrado.");
        }
        return cliente;
    }

    private Veiculo buscarVeiculo(String placa) {
        Veiculo veiculo = veiculos.buscarPorPlaca(placa);
        if (veiculo == null) {
            throw new NaoEncontradoException("Veiculo nao encontrado.");
        }
        return veiculo;
    }
}
