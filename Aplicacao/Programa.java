package Aplicacao;

import java.time.LocalDate;
import java.util.Scanner;

import Entidades.Carro;
import Entidades.Cliente;
import Entidades.Cnh;
import Entidades.Moto;
import Entidades.Motor;
import Servicos.Entrada;
import Servicos.LocacaoService;
import Servicos.Locadora;
import Servicos.Repositorio;
import Servicos.RepositorioClientes;
import Servicos.RepositorioClientesEmMemoria;
import Servicos.RepositorioLocacoes;
import Servicos.RepositorioLocacoesEmMemoria;
import Servicos.RepositorioVeiculos;

public class Programa {

    public static void main(String[] args) {
        Repositorio veiculos = new RepositorioVeiculos();
        RepositorioClientes clientes = new RepositorioClientesEmMemoria();
        RepositorioLocacoes locacoes = new RepositorioLocacoesEmMemoria();
        LocacaoService servico = new LocacaoService(veiculos, clientes, locacoes);

        veiculos.adicionar(new Carro("ABC1234", "Onix", 2022, 120, new Motor(82, "Flex"), "Hatch"));
        veiculos.adicionar(new Carro("DEF5678", "Corolla", 2023, 180, new Motor(177, "Flex"), "Sedan"));
        veiculos.adicionar(new Carro("JKL3456", "Hilux", 2020, 250, new Motor(150, "Diesel"), "Picape"));
        veiculos.adicionar(new Moto("GHI9012", "CG 160", 2021, 60, new Motor(16, "Gasolina"), 160));

        clientes.adicionar(new Cliente("Ana Souza", "12345678909", LocalDate.of(1990, 3, 15), Cnh.B));
        clientes.adicionar(new Cliente("Bruno Lima", "98765432100", LocalDate.of(1985, 7, 2), Cnh.A));
        // Menor de idade de propósito, para demonstrar a regra dos 21 anos.
        clientes.adicionar(new Cliente("Carla Dias", "11122233344", LocalDate.of(2008, 1, 20), Cnh.B));

        servico.alugar("98765432100", "GHI9012", LocalDate.now().minusDays(2), 5);

        Locadora locadora = new Locadora(servico, veiculos, clientes, new Entrada(new Scanner(System.in)));
        locadora.menu();
    }
}
