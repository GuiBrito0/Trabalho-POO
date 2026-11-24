package Testes;

import java.time.LocalDate;
import java.util.Scanner;

import Entidades.Carro;
import Entidades.Cliente;
import Entidades.Cnh;
import Entidades.Locacao;
import Entidades.Moto;
import Entidades.Motor;
import Excecoes.ClienteMenorDeIdadeException;
import Excecoes.CnhIncompativelException;
import Excecoes.DadoInvalidoException;
import Excecoes.EntradaEncerradaException;
import Excecoes.NaoEncontradoException;
import Excecoes.RegistroDuplicadoException;
import Excecoes.RegraDeNegocioException;
import Excecoes.VeiculoIndisponivelException;
import Servicos.Entrada;
import Servicos.LocacaoService;
import Servicos.RepositorioClientes;
import Servicos.RepositorioClientesEmMemoria;
import Servicos.RepositorioLocacoes;
import Servicos.RepositorioLocacoesEmMemoria;
import Servicos.RepositorioVeiculos;

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
        executar("onixTresDias", Testes::onixTresDias);
        executar("corollaSeisDiasSemDesconto", Testes::corollaSeisDiasSemDesconto);
        executar("corollaSeteDiasComDesconto", Testes::corollaSeteDiasComDesconto);
        executar("multaPorAtraso", Testes::multaPorAtraso);
        executar("devolucaoNoPrazoSemMulta", Testes::devolucaoNoPrazoSemMulta);
        executar("devolucaoAntecipadaCobraContratado", Testes::devolucaoAntecipadaCobraContratado);
        executar("mesmoDiaUmaDiaria", Testes::mesmoDiaUmaDiaria);
        executar("devolverAntesDaRetiradaRecusa", Testes::devolverAntesDaRetiradaRecusa);
        executar("devolverDuasVezesRecusa", Testes::devolverDuasVezesRecusa);
        executar("diariaCongelada", Testes::diariaCongelada);
        executar("placaDuplicadaRecusada", Testes::placaDuplicadaRecusada);
        executar("buscaPlacaIgnoraCaixaEEspacos", Testes::buscaPlacaIgnoraCaixaEEspacos);
        executar("cpfDuplicadoRecusado", Testes::cpfDuplicadoRecusado);
        executar("buscaCpfFormatado", Testes::buscaCpfFormatado);
        executar("locacaoAbertaDoVeiculo", Testes::locacaoAbertaDoVeiculo);
        executar("clienteTemLocacaoAberta", Testes::clienteTemLocacaoAberta);
        executar("historicoDoCliente", Testes::historicoDoCliente);
        executar("alugarEDevolver", Testes::alugarEDevolver);
        executar("veiculoAlugadoNaoAlugaDeNovo", Testes::veiculoAlugadoNaoAlugaDeNovo);
        executar("cnhIncompativel", Testes::cnhIncompativel);
        executar("menorDeIdade", Testes::menorDeIdade);
        executar("idadeNoDiaDoAniversario", Testes::idadeNoDiaDoAniversario);
        executar("naoEncontrados", Testes::naoEncontrados);
        executar("excluirVeiculoAlugadoBloqueado", Testes::excluirVeiculoAlugadoBloqueado);
        executar("excluirClienteComLocacaoAbertaBloqueado", Testes::excluirClienteComLocacaoAbertaBloqueado);
        executar("devolverNaoAlugadoRecusa", Testes::devolverNaoAlugadoRecusa);
        executar("devolverDuasVezesViaServicoRecusa", Testes::devolverDuasVezesViaServicoRecusa);
        executar("relatorios", Testes::relatorios);
        executar("inteiroRepeteEmTexto", Testes::inteiroRepeteEmTexto);
        executar("decimalComVirgula", Testes::decimalComVirgula);
        executar("dataInvalidaRepete", Testes::dataInvalidaRepete);
        executar("dataOuHojeComEnter", Testes::dataOuHojeComEnter);
        executar("textoVazioRepete", Testes::textoVazioRepete);
        executar("fimDaEntrada", Testes::fimDaEntrada);

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

    // ---------- Task 4: locação ----------

    static final LocalDate RETIRADA = LocalDate.of(2026, 11, 1);

    static Cliente ana() {
        return new Cliente("Ana", "12345678909", LocalDate.of(1990, 3, 15), Cnh.B);
    }

    static Carro onix() {
        return new Carro("ABC1234", "Onix", 2022, 120, motor(), "Hatch");
    }

    static Carro corolla() {
        return new Carro("DEF5678", "Corolla", 2023, 180, motor(), "Sedan");
    }

    static void onixTresDias() {
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 3);
        l.devolver(l.getDataPrevista());
        igual(360.0, l.getTotal());
        igual(0.0, l.getDesconto());
    }

    static void corollaSeisDiasSemDesconto() {
        Locacao l = new Locacao(ana(), corolla(), RETIRADA, 6);
        l.devolver(l.getDataPrevista());
        igual(1200.0, l.getTotal());
    }

    static void corollaSeteDiasComDesconto() {
        Locacao l = new Locacao(ana(), corolla(), RETIRADA, 7);
        l.devolver(l.getDataPrevista());
        igual(1260.0, l.getValorAluguel());
        igual(140.0, l.getDesconto());
    }

    static void multaPorAtraso() {
        Locacao l = new Locacao(ana(), corolla(), RETIRADA, 7);
        l.devolver(l.getDataPrevista().plusDays(2));
        igual(2, l.getDiasAtraso());
        igual(200.0, l.getMulta());
        igual(1460.0, l.getTotal());
    }

    static void devolucaoNoPrazoSemMulta() {
        Locacao l = new Locacao(ana(), corolla(), RETIRADA, 3);
        l.devolver(l.getDataPrevista());
        igual(0, l.getDiasAtraso());
        igual(0.0, l.getMulta());
    }

    static void devolucaoAntecipadaCobraContratado() {
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 3);
        l.devolver(RETIRADA.plusDays(1));
        igual(360.0, l.getTotal());
    }

    static void mesmoDiaUmaDiaria() {
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 1);
        l.devolver(RETIRADA);
        igual(120.0, l.getTotal());
    }

    static void devolverAntesDaRetiradaRecusa() {
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 3);
        lanca(DadoInvalidoException.class, () -> l.devolver(RETIRADA.minusDays(1)));
    }

    static void devolverDuasVezesRecusa() {
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 3);
        l.devolver(RETIRADA.plusDays(3));
        lanca(DadoInvalidoException.class, () -> l.devolver(RETIRADA.plusDays(3)));
    }

    static void diariaCongelada() {
        Carro carro = onix();
        Locacao l = new Locacao(ana(), carro, RETIRADA, 3);
        carro.setValorDiaria(500);
        igual(120.0, l.getDiariaAplicada());
    }

    // ---------- Task 5: repositórios ----------

    static void placaDuplicadaRecusada() {
        RepositorioVeiculos repo = new RepositorioVeiculos();
        repo.adicionar(onix());
        lanca(RegistroDuplicadoException.class,
                () -> repo.adicionar(new Carro("abc1234", "Outro", 2020, 100, motor(), "Hatch")));
    }

    static void buscaPlacaIgnoraCaixaEEspacos() {
        RepositorioVeiculos repo = new RepositorioVeiculos();
        repo.adicionar(onix());
        verdadeiro(repo.buscarPorPlaca("abc1234 ") != null);
        verdadeiro(repo.remover("abc1234"));
        verdadeiro(repo.buscarPorPlaca("ABC1234") == null);
    }

    static void cpfDuplicadoRecusado() {
        RepositorioClientes repo = new RepositorioClientesEmMemoria();
        repo.adicionar(ana());
        lanca(RegistroDuplicadoException.class, () -> repo.adicionar(
                new Cliente("Outra", "123.456.789-09", LocalDate.of(1991, 1, 1), Cnh.A)));
    }

    static void buscaCpfFormatado() {
        RepositorioClientes repo = new RepositorioClientesEmMemoria();
        repo.adicionar(ana());
        verdadeiro(repo.buscarPorCpf("123.456.789-09") != null);
        verdadeiro(repo.buscarPorCpf("00000000000") == null);
    }

    static void locacaoAbertaDoVeiculo() {
        RepositorioLocacoes repo = new RepositorioLocacoesEmMemoria();
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 3);
        repo.adicionar(l);
        verdadeiro(repo.abertaDoVeiculo("abc1234") == l);
        l.devolver(RETIRADA.plusDays(3));
        verdadeiro(repo.abertaDoVeiculo("ABC1234") == null);
    }

    static void clienteTemLocacaoAberta() {
        RepositorioLocacoes repo = new RepositorioLocacoesEmMemoria();
        Locacao l = new Locacao(ana(), onix(), RETIRADA, 3);
        repo.adicionar(l);
        verdadeiro(repo.clienteTemLocacaoAberta("123.456.789-09"));
        l.devolver(RETIRADA.plusDays(3));
        verdadeiro(!repo.clienteTemLocacaoAberta("12345678909"));
    }

    static void historicoDoCliente() {
        RepositorioLocacoes repo = new RepositorioLocacoesEmMemoria();
        Cliente outro = new Cliente("Caio", "98765432100", LocalDate.of(1985, 7, 2), Cnh.A);
        repo.adicionar(new Locacao(ana(), onix(), RETIRADA, 3));
        repo.adicionar(new Locacao(outro, corolla(), RETIRADA, 3));
        igual(1, repo.doCliente("12345678909").size());
        igual(2, repo.listar().size());
    }

    // ---------- Task 6: serviço de locação ----------

    static RepositorioVeiculos veiculosRepo;
    static RepositorioClientes clientesRepo;

    static LocacaoService servico() {
        veiculosRepo = new RepositorioVeiculos();
        clientesRepo = new RepositorioClientesEmMemoria();
        veiculosRepo.adicionar(onix());
        veiculosRepo.adicionar(new Moto("GHI9012", "CG 160", 2021, 60, motor(), 160));
        clientesRepo.adicionar(ana());
        clientesRepo.adicionar(new Cliente("Caio", "98765432100", LocalDate.of(1985, 7, 2), Cnh.A));
        clientesRepo.adicionar(new Cliente("Bia", "11122233344", LocalDate.of(2008, 1, 1), Cnh.B));
        clientesRepo.adicionar(new Cliente("Davi", "55566677788", LocalDate.of(2000, 5, 10), Cnh.B));
        return new LocacaoService(veiculosRepo, clientesRepo, new RepositorioLocacoesEmMemoria());
    }

    static void alugarEDevolver() {
        LocacaoService s = servico();
        Locacao l = s.alugar("12345678909", "ABC1234", RETIRADA, 3);
        verdadeiro(l.isAberta());
        Locacao fechada = s.devolver("abc1234", RETIRADA.plusDays(3));
        verdadeiro(!fechada.isAberta());
        igual(360.0, fechada.getTotal());
    }

    static void veiculoAlugadoNaoAlugaDeNovo() {
        LocacaoService s = servico();
        s.alugar("12345678909", "ABC1234", RETIRADA, 3);
        lanca(VeiculoIndisponivelException.class, () -> s.alugar("12345678909", "ABC1234", RETIRADA, 2));
    }

    static void cnhIncompativel() {
        LocacaoService s = servico();
        lanca(CnhIncompativelException.class, () -> s.alugar("12345678909", "GHI9012", RETIRADA, 2));
        lanca(CnhIncompativelException.class, () -> s.alugar("98765432100", "ABC1234", RETIRADA, 2));
    }

    static void menorDeIdade() {
        LocacaoService s = servico();
        lanca(ClienteMenorDeIdadeException.class, () -> s.alugar("11122233344", "ABC1234", RETIRADA, 2));
    }

    static void idadeNoDiaDoAniversario() {
        LocacaoService s = servico();
        lanca(ClienteMenorDeIdadeException.class,
                () -> s.alugar("55566677788", "ABC1234", LocalDate.of(2021, 5, 9), 2));
        Locacao l = s.alugar("55566677788", "ABC1234", LocalDate.of(2021, 5, 10), 2);
        verdadeiro(l.isAberta());
    }

    static void naoEncontrados() {
        LocacaoService s = servico();
        lanca(NaoEncontradoException.class, () -> s.alugar("00000000000", "ABC1234", RETIRADA, 2));
        lanca(NaoEncontradoException.class, () -> s.alugar("12345678909", "ZZZ9999", RETIRADA, 2));
    }

    static void excluirVeiculoAlugadoBloqueado() {
        LocacaoService s = servico();
        s.alugar("12345678909", "ABC1234", RETIRADA, 3);
        lanca(VeiculoIndisponivelException.class, () -> s.excluirVeiculo("ABC1234"));
        s.devolver("ABC1234", RETIRADA.plusDays(3));
        s.excluirVeiculo("ABC1234");
        verdadeiro(veiculosRepo.buscarPorPlaca("ABC1234") == null);
        lanca(NaoEncontradoException.class, () -> s.excluirVeiculo("ABC1234"));
    }

    static void excluirClienteComLocacaoAbertaBloqueado() {
        LocacaoService s = servico();
        s.alugar("12345678909", "ABC1234", RETIRADA, 3);
        lanca(RegraDeNegocioException.class, () -> s.excluirCliente("12345678909"));
        s.devolver("ABC1234", RETIRADA.plusDays(3));
        s.excluirCliente("12345678909");
        verdadeiro(clientesRepo.buscarPorCpf("12345678909") == null);
    }

    static void devolverNaoAlugadoRecusa() {
        LocacaoService s = servico();
        lanca(NaoEncontradoException.class, () -> s.devolver("ABC1234", RETIRADA));
    }

    static void devolverDuasVezesViaServicoRecusa() {
        LocacaoService s = servico();
        s.alugar("12345678909", "ABC1234", RETIRADA, 3);
        s.devolver("ABC1234", RETIRADA.plusDays(3));
        lanca(NaoEncontradoException.class, () -> s.devolver("ABC1234", RETIRADA.plusDays(3)));
    }

    static void relatorios() {
        LocacaoService s = servico();
        s.alugar("12345678909", "ABC1234", RETIRADA, 3);
        igual(1, s.veiculosDisponiveis().size());
        igual("GHI9012", s.veiculosDisponiveis().get(0).getPlaca());
        igual(1, s.locacoesAbertas().size());
        s.devolver("ABC1234", RETIRADA.plusDays(3));
        s.alugar("12345678909", "ABC1234", RETIRADA.plusDays(4), 1);
        igual(1, s.locacoesAbertas().size());
        igual(2, s.historicoDoCliente("123.456.789-09").size());
        lanca(NaoEncontradoException.class, () -> s.historicoDoCliente("00000000000"));
    }

    // ---------- Task 7: entrada ----------

    static Entrada entrada(String texto) {
        return new Entrada(new Scanner(texto));
    }

    static void inteiroRepeteEmTexto() {
        igual(5, entrada("abc\n5\n").inteiro("Numero: "));
    }

    static void decimalComVirgula() {
        igual(12.5, entrada("12,5\n").decimal("Valor: "));
    }

    static void dataInvalidaRepete() {
        igual(LocalDate.of(2024, 3, 5), entrada("31/02/2024\n05/03/2024\n").data("Data: "));
    }

    static void dataOuHojeComEnter() {
        igual(LocalDate.now(), entrada("\n").dataOuHoje("Data: "));
    }

    static void textoVazioRepete() {
        igual("Ana", entrada("\n  \nAna\n").texto("Nome: "));
    }

    static void fimDaEntrada() {
        lanca(EntradaEncerradaException.class, () -> entrada("").inteiro("Numero: "));
    }
}
