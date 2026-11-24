package Entidades;

import Excecoes.DadoInvalidoException;

public class Motor {
    private int potencia;
    private String combustivel;

    public Motor(int potencia, String combustivel) {
        this.potencia = validarPotencia(potencia);
        this.combustivel = validarCombustivel(combustivel);
    }

    private static int validarPotencia(int potencia) {
        if (potencia <= 0) {
            throw new DadoInvalidoException("A potencia do motor deve ser maior que zero.");
        }
        return potencia;
    }

    private static String validarCombustivel(String combustivel) {
        if (combustivel == null || combustivel.trim().isEmpty()) {
            throw new DadoInvalidoException("Informe o combustivel do motor.");
        }
        return combustivel.trim();
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = validarPotencia(potencia);
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = validarCombustivel(combustivel);
    }
}
