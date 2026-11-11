package Aplicacao;

import Entidades.Carro;
import Entidades.Moto;
import Servicos.Locadora;
import Servicos.Repositorio;
import Servicos.RepositorioVeiculos;

public class Programa {

    public static void main(String[] args) {
        Repositorio repositorio = new RepositorioVeiculos();
        Locadora locadora = new Locadora(repositorio);
        locadora.menu();
    }
}