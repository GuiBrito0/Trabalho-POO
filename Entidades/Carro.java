package Entidades;

import Excecoes.DadoInvalidoException;

public class Carro extends Veiculo {
    private String categoria;

    public Carro(String placa, String modelo, int ano, double valorDiaria, Motor motor, String categoria) {
        super(placa, modelo, ano, valorDiaria, motor);
        this.categoria = validarCategoria(categoria);
    }

    private static String validarCategoria(String categoria) {
        if (categoria != null) {
            String c = categoria.trim().toUpperCase();
            if (c.equals("SUV") || c.equals("SEDAN") || c.equals("HATCH") || c.equals("PICAPE")) {
                return categoria.trim();
            }
        }
        throw new DadoInvalidoException("Categoria invalida (use Suv, Sedan, Hatch ou Picape).");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = validarCategoria(categoria);
    }

    @Override
    public Double calcularDiaria() {
        if (categoria == null)
            return getValorDiaria();
        switch (categoria.toUpperCase()) {
            case "SUV":
                return getValorDiaria() + 40.0;
            case "PICAPE":
                return getValorDiaria() + 50.0;
            case "SEDAN":
                return getValorDiaria() + 20.0;
            default:
                return getValorDiaria();
        }
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public boolean aceitaCnh(Cnh cnh) {
        return cnh == Cnh.B || cnh == Cnh.AB;
    }
}
