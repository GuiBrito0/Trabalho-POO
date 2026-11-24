package Excecoes;

public class NaoEncontradoException extends RegraDeNegocioException {
    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
