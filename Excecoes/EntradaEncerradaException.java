package Excecoes;

public class EntradaEncerradaException extends RuntimeException {
    public EntradaEncerradaException() {
        super("A entrada de dados foi encerrada.");
    }
}
