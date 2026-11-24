package Entidades;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import Excecoes.DadoInvalidoException;

public class Locacao {
    private static final int DIAS_PARA_DESCONTO = 7;
    private static final double PERCENTUAL_DESCONTO = 0.10;
    private static final double PERCENTUAL_MULTA_POR_DIA = 0.50;

    private Cliente cliente;
    private Veiculo veiculo;
    private double diariaAplicada;
    private LocalDate dataRetirada;
    private int diasContratados;
    private LocalDate dataDevolucao;

    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataRetirada, int dias) {
        if (dias < 1) {
            throw new DadoInvalidoException("O aluguel deve ter pelo menos 1 dia.");
        }
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.diariaAplicada = veiculo.calcularDiaria();
        this.dataRetirada = dataRetirada;
        this.diasContratados = dias;
    }

    public void devolver(LocalDate data) {
        if (dataDevolucao != null) {
            throw new DadoInvalidoException("Esta locacao ja foi devolvida.");
        }
        if (data.isBefore(dataRetirada)) {
            throw new DadoInvalidoException("A devolucao nao pode ser antes da retirada.");
        }
        this.dataDevolucao = data;
    }

    public boolean isAberta() {
        return dataDevolucao == null;
    }

    public int getDiasAtraso() {
        if (dataDevolucao == null) {
            return 0;
        }
        long atraso = ChronoUnit.DAYS.between(getDataPrevista(), dataDevolucao);
        return (int) Math.max(0, atraso);
    }

    public double getValorAluguel() {
        return diariaAplicada * diasContratados - getDesconto();
    }

    public double getDesconto() {
        if (diasContratados >= DIAS_PARA_DESCONTO) {
            return diariaAplicada * diasContratados * PERCENTUAL_DESCONTO;
        }
        return 0;
    }

    public double getMulta() {
        return getDiasAtraso() * PERCENTUAL_MULTA_POR_DIA * diariaAplicada;
    }

    public double getTotal() {
        return getValorAluguel() + getMulta();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public double getDiariaAplicada() {
        return diariaAplicada;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataPrevista() {
        return dataRetirada.plusDays(diasContratados);
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public int getDiasContratados() {
        return diasContratados;
    }
}
