package Testes;

import java.time.LocalDate;

import Entidades.Carro;
import Entidades.Cliente;
import Entidades.Cnh;
import Entidades.Moto;
import Entidades.Motor;
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
        executar("placaNormalizada", Testes::placaNormalizada);
        executar("veiculoRejeitaDadosInvalidos", Testes::veiculoRejeitaDadosInvalidos);
        executar("diariaPorTipo", Testes::diariaPorTipo);
        executar("aceitaCnhPorTipo", Testes::aceitaCnhPorTipo);
        executar("cpfFormatadoVirasoDigitos", Testes::cpfFormatadoVirasoDigitos);
        executar("clienteRejeitaDadosInvalidos", Testes::clienteRejeitaDadosInvalidos);
        executar("idadeNoAniversario", Testes::idadeNoAniversario);

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

    static void igual(double esperado, Number atual) {
        igual(esperado, atual.doubleValue());
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

    // ---------- Task 2: veículos ----------

    static Motor motor() {
        return new Motor(82, "Flex");
    }

    static void placaNormalizada() {
        Carro c = new Carro(" abc1234 ", "Onix", 2022, 120, motor(), "Hatch");
        igual("ABC1234", c.getPlaca());
    }

    static void veiculoRejeitaDadosInvalidos() {
        int anoAtual = LocalDate.now().getYear();
        lanca(DadoInvalidoException.class, () -> new Carro("ABC1234", "Onix", 1979, 120, motor(), "Hatch"));
        lanca(DadoInvalidoException.class, () -> new Carro("ABC1234", "Onix", anoAtual + 2, 120, motor(), "Hatch"));
        lanca(DadoInvalidoException.class, () -> new Carro("ABC1234", "Onix", 2022, 0, motor(), "Hatch"));
        lanca(DadoInvalidoException.class, () -> new Carro("  ", "Onix", 2022, 120, motor(), "Hatch"));
        lanca(DadoInvalidoException.class, () -> new Carro("ABC1234", "Onix", 2022, 120, motor(), "Esportivo"));
        lanca(DadoInvalidoException.class, () -> new Motor(0, "Flex"));
        lanca(DadoInvalidoException.class, () -> new Moto("GHI9012", "CG", 2021, 60, motor(), 0));
    }

    static void diariaPorTipo() {
        igual(120.0, new Carro("AAA1111", "Onix", 2022, 120, motor(), "Hatch").calcularDiaria());
        igual(200.0, new Carro("AAA1112", "Corolla", 2023, 180, motor(), "Sedan").calcularDiaria());
        igual(300.0, new Carro("AAA1113", "Hilux", 2020, 250, motor(), "Picape").calcularDiaria());
        igual(140.0, new Carro("AAA1114", "Compass", 2021, 100, motor(), "Suv").calcularDiaria());
        igual(130.0, new Moto("BBB1111", "Grande", 2021, 100, motor(), 600).calcularDiaria());
        igual(60.0, new Moto("BBB1112", "CG 160", 2021, 60, motor(), 160).calcularDiaria());
    }

    static void aceitaCnhPorTipo() {
        Carro carro = new Carro("AAA1111", "Onix", 2022, 120, motor(), "Hatch");
        verdadeiro(carro.aceitaCnh(Cnh.B));
        verdadeiro(carro.aceitaCnh(Cnh.AB));
        verdadeiro(!carro.aceitaCnh(Cnh.A));
        Moto moto = new Moto("BBB1112", "CG 160", 2021, 60, motor(), 160);
        verdadeiro(moto.aceitaCnh(Cnh.A));
        verdadeiro(moto.aceitaCnh(Cnh.AB));
        verdadeiro(!moto.aceitaCnh(Cnh.B));
    }

    // ---------- Task 3: cliente ----------

    static void cpfFormatadoVirasoDigitos() {
        Cliente c = new Cliente("Ana", "123.456.789-09", LocalDate.of(1990, 3, 15), Cnh.B);
        igual("12345678909", c.getCpf());
    }

    static void clienteRejeitaDadosInvalidos() {
        LocalDate nasc = LocalDate.of(1990, 3, 15);
        lanca(DadoInvalidoException.class, () -> new Cliente("Ana", "123", nasc, Cnh.B));
        lanca(DadoInvalidoException.class, () -> new Cliente(" ", "12345678909", nasc, Cnh.B));
        lanca(DadoInvalidoException.class,
                () -> new Cliente("Ana", "12345678909", LocalDate.now().plusDays(1), Cnh.B));
        lanca(DadoInvalidoException.class, () -> new Cliente("Ana", "12345678909", nasc, null));
    }

    static void idadeNoAniversario() {
        Cliente c = new Cliente("Ana", "12345678909", LocalDate.of(2000, 5, 10), Cnh.B);
        igual(20, c.idadeEm(LocalDate.of(2021, 5, 9)));
        igual(21, c.idadeEm(LocalDate.of(2021, 5, 10)));
    }
}
