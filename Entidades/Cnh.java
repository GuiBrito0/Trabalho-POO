package Entidades;

import Excecoes.DadoInvalidoException;

public enum Cnh {
    A, B, AB;

    public static Cnh de(String texto) {
        if (texto != null) {
            for (Cnh c : values()) {
                if (c.name().equalsIgnoreCase(texto.trim())) {
                    return c;
                }
            }
        }
        throw new DadoInvalidoException("Categoria de CNH invalida (use A, B ou AB).");
    }
}
