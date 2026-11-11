package Servicos;

import java.util.*;

import Entidades.Carro;
import Entidades.Moto;
import Entidades.Motor;
import Entidades.Veiculo;

public class Locadora {
    private Repositorio repositorio;
    private Scanner sc = new Scanner(System.in);

    public Locadora(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void menu() {
        int opcao;

        do {
            System.out.println("Locadora de Veiculos");
            System.out.println("Escolha o que deseja: ");
            System.out.println("1 - Incluir no Sistema");
            System.out.println("2 - Alterar um Veiculo");
            System.out.println("3 - Excluir um Veiculo");
            System.out.println("4 - Listar");
            System.out.println("5 - Buscar por placa");
            System.out.println("0 - Sair");
            opcao = sc.nextInt();
            sc.nextLine();
            
            switch (opcao) {
                case 1:
                    incluir();
                    break;
                case 2:
                    alterar();
                    break;
                case 3:
                    excluir();
                    break;
                case 4:
                    listar();
                    break;
                case 5:
                    buscar();
                    break;
                case 0:
                    System.out.println("Saindo do Sistema");
                default:
                    System.out.println("Opção Indisponível");
            }
        } while (opcao != 0);
    }
    
    public void incluir() {
        System.out.println("Tipo (1 - Carro / 2 - Moto) :");
        int tipo = sc.nextInt();
        sc.nextLine();
       
        System.out.print("Placa: ");
        String placa = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Ano: ");
        int ano = sc.nextInt();
        System.out.print("Valor da diaria: ");
        double valorDiaria = sc.nextDouble();
        System.out.print("Potencia do motor: ");
        int potencia = sc.nextInt();
        sc.nextLine();
        System.out.print("Combustivel: ");
        String combustivel = sc.nextLine();

        Motor motor = new Motor(potencia, combustivel);
        if (tipo == 1) {
            System.out.println("Categorias (Suv, Sedan, Hatch, Picape)");
            System.out.print("Escolha uma: ");
            String categoria = sc.nextLine();
            Carro carro = new Carro(placa, modelo, ano, valorDiaria, motor, categoria);
            repositorio.adicionar(carro);
            System.out.println("Carro registrado!");
        } else if (tipo == 2) {
            System.out.print("Cilindradas: ");
            int cilindradas = sc.nextInt();
            sc.nextLine();
            Moto moto = new Moto(placa, modelo, ano, valorDiaria, motor, cilindradas);
            repositorio.adicionar(moto);
            System.out.println("Moto registrada!");
        } else {
            System.out.println("Tipo de Veiculo Invalido.");
        }
    }
    
    public void alterar(){
        System.out.print("Informe a placa do veiculo: ");
        String placa = sc.nextLine();
        Veiculo v = repositorio.buscarPorPlaca(placa);

        if (v != null) {
            System.out.print("Novo modelo: ");
            v.setModelo(sc.nextLine());
            System.out.print("Novo ano: ");
            v.setAno(sc.nextInt());
            System.out.print("Novo valor da diaria:");
            v.setValorDiaria(sc.nextDouble());
            sc.nextLine();
            System.out.print("O veiculo foi alterado!");
        } else {
            System.out.print("Veiculo não encontrado!");
        }

    }

    public void excluir () {
        System.out.print("Informe a placa do Veiculo: ");
        String placa = sc.nextLine();
        
        if (repositorio.remover(placa)) {
            System.out.println("Veiculo removido do sistema");
        } else {
            System.out.println("Veiculo não consta no sistema");
        }
    }

    public void listar() {
        ordenarPorAno();
        
        ArrayList<Veiculo> lista = repositorio.listar();
        System.out.println();
        System.out.printf("%-6s %-9s %-12s %-6s %-10s%n", "TIPO", "PLACA", "MODELO", "ANO", "DIARIA");
        System.out.println();

        for (int i = 0; i < lista.size(); i++) {
            Veiculo v = lista.get(i);
            System.out.printf("%-6s %-9s %-12s %-6d R$ %.2f%n", v.getTipo(), v.getPlaca(), v.getModelo(), v.getAno(), v.calcularDiaria());
        }
        
    }

    public void buscar() {
        System.out.print("Informe a placa do veiculo: ");
        String placa = sc.nextLine();
        Veiculo v = repositorio.buscarPorPlaca(placa);

        if (v != null ) {
        System.out.println("Tipo: " + v.getTipo());
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Modelo: " + v.getModelo());
        System.out.println("Ano: " + v.getAno());
        System.out.println("Motor: " + v.getMotor().getPotencia() + " Cavalos: " + v.getMotor().getCombustivel());
        System.out.printf("Diaria: R$ %.2f%n",v.calcularDiaria());
        } else {
            System.out.println("Veiculo não encontrado!");
        }
    }

    public void ordenarPorAno() {
        ArrayList<Veiculo> lista = repositorio.listar();

        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.size(); j++) {
                if(lista.get(j).getAno() > lista.get(j + 1).getAno()) {
                    Veiculo v = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, v);
                }
                
            }
        }
    }

}
