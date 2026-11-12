package Aplicacao;

import Entidades.Carro;
import Entidades.Moto;
import Entidades.Motor;
import Servicos.Locadora;
import Servicos.Repositorio;
import Servicos.RepositorioVeiculos;

public class Programa {

    public static void main(String[] args) {
        Repositorio repositorio = new RepositorioVeiculos();

        repositorio.adicionar(new Carro("ABC1234", "Onix", 2022, 120, new Motor(82, "Flex"), "Hatch"));
        repositorio.adicionar(new Carro("DEF5678", "Corolla", 2023, 180, new Motor(177, "Flex"), "Sedan"));
        repositorio.adicionar(new Carro("JKL3456", "Hilux", 2020, 250, new Motor(150, "Diesel"), "Picape"));
        repositorio.adicionar(new Moto("GHI9012", "CG 160", 2021, 60, new Motor(16, "Gasolina"), 160));

        Locadora locadora = new Locadora(repositorio);
        locadora.menu();

    }
}