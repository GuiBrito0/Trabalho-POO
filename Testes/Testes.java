package Testes;

import Entidades.Cnh;
import Excecoes.DadoInvalidoException;
import Excecoes.RegraDeNegocioException;
import Excecoes.VeiculoIndisponivelException;

public class Testes {
    private static int falhas = 0;
    private static int total = 0;

    public static void main(String[] args) {
        executar("cnhDeAceitaMinusculasEEspacos", Testes::cnhDeAceitaMinusculasEEspacos);
        executar("cnhDeRejeitaInvalida", Testes::cnhDeRejeitaInvalida);
        executar("excecoesSaoRegraDeNegocio", Testes::excecoesSaoRegraDeNegocio);

        System.out.println();
        System.out.println(total - falhas + "/" + total + " testes passaram");
        if (falhas > 0) {
            System.exit(1);
        }
    }

    // ---------- infraestrutura ----------

    private static void executar(String nome, Runnable teste) {
        total++;
        try {
            teste.run();
            System.out.println("OK: " + nome);
        } catch (Throwable e) {
            falhas++;
            System.out.println("FALHOU: " + nome + " - " + e);
        }
    }

    static void igual(Object esperado, Object atual) {
        if (esperado == null ? atual != null : !esperado.equals(atual)) {
            throw new AssertionError("esperado <" + esperado + "> mas veio <" + atual + ">");
        }
    }

    static void igual(double esperado, double atual) {
        if (Math.abs(esperado - atual) > 0.005) {
            throw new AssertionError("esperado <" + esperado + "> mas veio <" + atual + ">");
        }
    }

    static void verdadeiro(boolean condicao) {
        if (!condicao) {
            throw new AssertionError("condição era falsa");
        }
    }

    static void lanca(Class<? extends Throwable> tipo, Runnable codigo) {
        try {
            codigo.run();
        } catch (Throwable e) {
            if (tipo.isInstance(e)) {
                return;
            }
            throw new AssertionError("esperava " + tipo.getSimpleName() + " mas veio " + e);
        }
        throw new AssertionError("esperava " + tipo.getSimpleName() + " mas nada foi lançado");
    }

    // ---------- Task 1: Cnh e exceções ----------

    static void cnhDeAceitaMinusculasEEspacos() {
        igual(Cnh.AB, Cnh.de(" ab "));
    }

    static void cnhDeRejeitaInvalida() {
        lanca(DadoInvalidoException.class, () -> Cnh.de("X"));
    }

    static void excecoesSaoRegraDeNegocio() {
        verdadeiro(new VeiculoIndisponivelException("x") instanceof RegraDeNegocioException);
    }
}
