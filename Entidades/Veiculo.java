package Entidades;

import java.time.LocalDate;

import Excecoes.DadoInvalidoException;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private int ano;
    private double valorDiaria;
    private Motor motor;

    private static final int ANO_MINIMO = 1980;

    public Veiculo(String placa, String modelo, int ano, double valorDiaria, Motor motor) {
        this.placa = normalizarPlaca(placa);
        this.modelo = modelo;
        this.ano = validarAno(ano);
        this.valorDiaria = validarValorDiaria(valorDiaria);
        this.motor = validarMotor(motor);
    }

    public static String normalizarPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new DadoInvalidoException("Informe a placa do veiculo.");
        }
        return placa.trim().toUpperCase();
    }

    private static int validarAno(int ano) {
        int anoMaximo = LocalDate.now().getYear() + 1;
        if (ano < ANO_MINIMO || ano > anoMaximo) {
            throw new DadoInvalidoException("O ano deve estar entre " + ANO_MINIMO + " e " + anoMaximo + ".");
        }
        return ano;
    }

    private static double validarValorDiaria(double valorDiaria) {
        if (valorDiaria <= 0) {
            throw new DadoInvalidoException("O valor da diaria deve ser maior que zero.");
        }
        return valorDiaria;
    }

    private static Motor validarMotor(Motor motor) {
        if (motor == null) {
            throw new DadoInvalidoException("O veiculo precisa de um motor.");
        }
        return motor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = normalizarPlaca(placa);
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = validarAno(ano);
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = validarValorDiaria(valorDiaria);
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = validarMotor(motor);
    }

    public abstract Double calcularDiaria();

    public abstract String getTipo();

    public abstract boolean aceitaCnh(Cnh cnh);
}
