package Servicos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

import Entidades.Carro;
import Entidades.Cliente;
import Entidades.Cnh;
import Entidades.Locacao;
import Entidades.Moto;
import Entidades.Motor;
import Entidades.Veiculo;
import Excecoes.DadoInvalidoException;
import Excecoes.EntradaEncerradaException;
import Excecoes.RegraDeNegocioException;

public class Locadora {
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocacaoService servico;
    private Repositorio veiculos;
    private RepositorioClientes clientes;
    private Entrada entrada;

    public Locadora(LocacaoService servico, Repositorio veiculos, RepositorioClientes clientes, Entrada entrada) {
        this.servico = servico;
        this.veiculos = veiculos;
        this.clientes = clientes;
        this.entrada = entrada;
    }

    // ---------- menus ----------

    public void menu() {
        try {
            int opcao;
            do {
                System.out.println("========================");
                System.out.println("Locadora de Veiculos");
                System.out.println("1 - Veículos");
                System.out.println("2 - Clientes");
                System.out.println("3 - Locações");
                System.out.println("4 - Relatórios");
                System.out.println("0 - Sair");
                opcao = entrada.inteiro("Opção: ");

                switch (opcao) {
                    case 1:
                        menuVeiculos();
                        break;
                    case 2:
                        menuClientes();
                        break;
                    case 3:
                        menuLocacoes();
                        break;
                    case 4:
                        menuRelatorios();
                        break;
                    case 0:
                        System.out.println("Saindo do Sistema");
                        break;
                    default:
                        System.out.println("Opção Indisponível");
                }
            } while (opcao != 0);
        } catch (EntradaEncerradaException e) {
            System.out.println();
            System.out.println("Entrada encerrada. Saindo do Sistema");
        }
    }

    private void menuVeiculos() {
        String[] opcoes = { "Incluir", "Alterar", "Excluir", "Listar por ano", "Buscar por placa" };
        submenu("Veículos", opcoes, opcao -> {
            switch (opcao) {
                case 1:
                    incluirVeiculo();
                    break;
                case 2:
                    alterarVeiculo();
                    break;
                case 3:
                    excluirVeiculo();
                    break;
                case 4:
                    listarVeiculos();
                    break;
                case 5:
                    buscarVeiculo();
                    break;
            }
        });
    }

    private void menuClientes() {
        String[] opcoes = { "Cadastrar", "Listar", "Excluir" };
        submenu("Clientes", opcoes, opcao -> {
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    excluirCliente();
                    break;
            }
        });
    }

    private void menuLocacoes() {
        String[] opcoes = { "Alugar", "Devolver" };
        submenu("Locações", opcoes, opcao -> {
            switch (opcao) {
                case 1:
                    alugar();
                    break;
                case 2:
                    devolver();
                    break;
            }
        });
    }

    private void menuRelatorios() {
        String[] opcoes = { "Veículos disponíveis", "Veículos alugados", "Histórico de um cliente" };
        submenu("Relatórios", opcoes, opcao -> {
            switch (opcao) {
                case 1:
                    relatorioDisponiveis();
                    break;
                case 2:
                    relatorioAlugados();
                    break;
                case 3:
                    relatorioHistorico();
                    break;
            }
        });
    }

    private void submenu(String titulo, String[] opcoes, Consumer<Integer> acao) {
        int opcao;
        do {
            System.out.println("========================");
            System.out.println(titulo);
            for (int i = 0; i < opcoes.length; i++) {
                System.out.println((i + 1) + " - " + opcoes[i]);
            }
            System.out.println("0 - Voltar");
            opcao = entrada.inteiro("Opção: ");

            if (opcao == 0) {
                return;
            }
            if (opcao < 1 || opcao > opcoes.length) {
                System.out.println("Opção Indisponível");
            } else {
                int escolhida = opcao;
                executar(() -> acao.accept(escolhida));
            }
        } while (true);
    }

    // Toda regra de negócio violada vira uma mensagem, e o menu continua.
    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (RegraDeNegocioException e) {
            System.out.println("Não foi possível concluir: " + e.getMessage());
        }
    }

    private <T> T repetirAteValido(Supplier<T> leitura) {
        while (true) {
            try {
                return leitura.get();
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ---------- veículos ----------

    private void incluirVeiculo() {
        int tipo;
        do {
            tipo = entrada.inteiro("Tipo (1 - Carro / 2 - Moto): ");
            if (tipo != 1 && tipo != 2) {
                System.out.println("Tipo de Veiculo Invalido. Tente novamente.");
            }
        } while (tipo != 1 && tipo != 2);

        String placa = repetirAteValido(() -> Veiculo.normalizarPlaca(entrada.texto("Placa: ")));
        String modelo = entrada.texto("Modelo: ");
        int ano = entrada.inteiro("Ano: ");
        double valorDiaria = entrada.decimal("Valor da diaria: ");
        int potencia = entrada.inteiro("Potencia do motor: ");
        String combustivel = entrada.texto("Combustivel: ");
        Motor motor = new Motor(potencia, combustivel);

        if (tipo == 1) {
            System.out.println("Categorias (Suv, Sedan, Hatch, Picape)");
            String categoria = entrada.texto("Escolha uma: ");
            veiculos.adicionar(new Carro(placa, modelo, ano, valorDiaria, motor, categoria));
            System.out.println("Carro registrado!");
        } else {
            int cilindradas = entrada.inteiro("Cilindradas: ");
            veiculos.adicionar(new Moto(placa, modelo, ano, valorDiaria, motor, cilindradas));
            System.out.println("Moto registrada!");
        }
    }

    private void alterarVeiculo() {
        Veiculo v = veiculos.buscarPorPlaca(entrada.texto("Informe a placa do veiculo: "));
        if (v == null) {
            System.out.println("Veiculo não encontrado!");
            return;
        }

        String modelo = entrada.texto("Novo modelo: ");
        int ano = entrada.inteiro("Novo ano: ");
        double valorDiaria = entrada.decimal("Novo valor da diaria: ");

        // Se algum valor for recusado, o veículo volta a ficar como estava.
        String modeloAntigo = v.getModelo();
        int anoAntigo = v.getAno();
        double diariaAntiga = v.getValorDiaria();
        try {
            v.setModelo(modelo);
            v.setAno(ano);
            v.setValorDiaria(valorDiaria);
        } catch (RegraDeNegocioException e) {
            v.setModelo(modeloAntigo);
            v.setAno(anoAntigo);
            v.setValorDiaria(diariaAntiga);
            throw e;
        }
        System.out.println("O veiculo foi alterado!");
    }

    private void excluirVeiculo() {
        servico.excluirVeiculo(entrada.texto("Informe a placa do Veiculo: "));
        System.out.println("Veiculo removido do sistema");
    }

    private void listarVeiculos() {
        ArrayList<Veiculo> lista = new ArrayList<>(veiculos.listar());
        lista.sort(Comparator.comparingInt(Veiculo::getAno));
        imprimirVeiculos(lista);
    }

    private void imprimirVeiculos(ArrayList<Veiculo> lista) {
        System.out.println();
        System.out.printf("%-6s %-9s %-12s %-6s %-10s%n", "TIPO", "PLACA", "MODELO", "ANO", "DIARIA");
        for (Veiculo v : lista) {
            System.out.printf("%-6s %-9s %-12s %-6d R$ %.2f%n", v.getTipo(), v.getPlaca(), v.getModelo(), v.getAno(),
                    v.calcularDiaria());
        }
        if (lista.isEmpty()) {
            System.out.println("(nenhum veiculo)");
        }
    }

    private void buscarVeiculo() {
        Veiculo v = veiculos.buscarPorPlaca(entrada.texto("Informe a placa do veiculo: "));
        if (v == null) {
            System.out.println("Veiculo não encontrado!");
            return;
        }
        System.out.println("Tipo: " + v.getTipo());
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Modelo: " + v.getModelo());
        System.out.println("Ano: " + v.getAno());
        System.out.println("Motor: " + v.getMotor().getPotencia() + " cv - " + v.getMotor().getCombustivel());
        System.out.printf("Diaria: R$ %.2f%n", v.calcularDiaria());
    }

    // ---------- clientes ----------

    private void cadastrarCliente() {
        String nome = entrada.texto("Nome: ");
        String cpf = repetirAteValido(() -> Cliente.normalizarCpf(entrada.texto("CPF: ")));
        LocalDate nascimento = entrada.data("Data de nascimento (dd/MM/yyyy): ");
        Cnh cnh = repetirAteValido(() -> Cnh.de(entrada.texto("Categoria da CNH (A, B ou AB): ")));

        clientes.adicionar(new Cliente(nome, cpf, nascimento, cnh));
        System.out.println("Cliente cadastrado!");
    }

    private void listarClientes() {
        System.out.println();
        System.out.printf("%-20s %-13s %-12s %-4s%n", "NOME", "CPF", "NASCIMENTO", "CNH");
        for (Cliente c : clientes.listar()) {
            System.out.printf("%-20s %-13s %-12s %-4s%n", c.getNome(), c.getCpf(), c.getDataNascimento().format(DATA),
                    c.getCnh());
        }
        if (clientes.listar().isEmpty()) {
            System.out.println("(nenhum cliente)");
        }
    }

    private void excluirCliente() {
        servico.excluirCliente(entrada.texto("Informe o CPF do cliente: "));
        System.out.println("Cliente removido do sistema");
    }

    // ---------- locações ----------

    private void alugar() {
        String cpf = entrada.texto("CPF do cliente: ");
        String placa = entrada.texto("Placa do veiculo: ");
        LocalDate retirada = entrada.dataOuHoje("Data de retirada (Enter = hoje): ");
        int dias = entrada.inteiro("Quantidade de dias: ");

        Locacao l = servico.alugar(cpf, placa, retirada, dias);
        System.out.println("Aluguel registrado!");
        System.out.println("Cliente: " + l.getCliente().getNome());
        System.out.println("Veiculo: " + l.getVeiculo().getModelo() + " (" + l.getVeiculo().getPlaca() + ")");
        System.out.println("Retirada: " + l.getDataRetirada().format(DATA));
        System.out.println("Devolução prevista: " + l.getDataPrevista().format(DATA));
        System.out.printf("Diaria aplicada: R$ %.2f%n", l.getDiariaAplicada());
        if (l.getDesconto() > 0) {
            System.out.printf("Desconto (10%%): R$ %.2f%n", l.getDesconto());
        }
        System.out.printf("Valor estimado: R$ %.2f%n", l.getTotal());
    }

    private void devolver() {
        String placa = entrada.texto("Placa do veiculo: ");
        LocalDate data = entrada.dataOuHoje("Data da devolução (Enter = hoje): ");

        Locacao l = servico.devolver(placa, data);
        System.out.println("Devolução registrada!");
        System.out.println("Dias contratados: " + l.getDiasContratados());
        System.out.printf("Aluguel: R$ %.2f%n", l.getValorAluguel());
        if (l.getDesconto() > 0) {
            System.out.printf("Desconto já aplicado: R$ %.2f%n", l.getDesconto());
        }
        System.out.println("Dias de atraso: " + l.getDiasAtraso());
        System.out.printf("Multa: R$ %.2f%n", l.getMulta());
        System.out.printf("Total: R$ %.2f%n", l.getTotal());
    }

    // ---------- relatórios ----------

    private void relatorioDisponiveis() {
        imprimirVeiculos(servico.veiculosDisponiveis());
    }

    private void relatorioAlugados() {
        System.out.println();
        System.out.printf("%-9s %-12s %-20s %-12s %-12s%n", "PLACA", "MODELO", "CLIENTE", "RETIRADA", "PREVISTA");
        ArrayList<Locacao> abertas = servico.locacoesAbertas();
        for (Locacao l : abertas) {
            System.out.printf("%-9s %-12s %-20s %-12s %-12s%n", l.getVeiculo().getPlaca(), l.getVeiculo().getModelo(),
                    l.getCliente().getNome(), l.getDataRetirada().format(DATA), l.getDataPrevista().format(DATA));
        }
        if (abertas.isEmpty()) {
            System.out.println("(nenhum veiculo alugado)");
        }
    }

    private void relatorioHistorico() {
        ArrayList<Locacao> historico = servico.historicoDoCliente(entrada.texto("CPF do cliente: "));
        System.out.println();
        System.out.printf("%-9s %-12s %-12s %-12s %-10s%n", "PLACA", "MODELO", "RETIRADA", "DEVOLUCAO", "TOTAL");
        for (Locacao l : historico) {
            String devolucao = l.isAberta() ? "em aberto" : l.getDataDevolucao().format(DATA);
            String total = l.isAberta() ? "-" : String.format("R$ %.2f", l.getTotal());
            System.out.printf("%-9s %-12s %-12s %-12s %-10s%n", l.getVeiculo().getPlaca(), l.getVeiculo().getModelo(),
                    l.getDataRetirada().format(DATA), devolucao, total);
        }
        if (historico.isEmpty()) {
            System.out.println("(nenhuma locação)");
        }
    }
}
