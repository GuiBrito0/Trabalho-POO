package Excecoes;

public class ClienteMenorDeIdadeException extends RegraDeNegocioException {
    public ClienteMenorDeIdadeException(String mensagem) {
        super(mensagem);
    }
}
