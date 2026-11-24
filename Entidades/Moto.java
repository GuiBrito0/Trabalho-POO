package Entidades;

import Excecoes.DadoInvalidoException;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String placa, String modelo, int ano, double valorDiaria, Motor motor, int cilindradas) {
        super(placa, modelo, ano, valorDiaria, motor);
        this.cilindradas = validarCilindradas(cilindradas);
    }

    private static int validarCilindradas(int cilindradas) {
        if (cilindradas <= 0) {
            throw new DadoInvalidoException("As cilindradas devem ser maiores que zero.");
        }
        return cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = validarCilindradas(cilindradas);
    }

    @Override
    public Double calcularDiaria() {
        if (cilindradas > 500) {
            return getValorDiaria() * 1.3;
        }
        return getValorDiaria();
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    @Override
    public boolean aceitaCnh(Cnh cnh) {
        return cnh == Cnh.A || cnh == Cnh.AB;
    }
}
