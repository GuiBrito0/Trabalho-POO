package Servicos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Excecoes.EntradaEncerradaException;

public class Entrada {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private Scanner scanner;

    public Entrada(Scanner scanner) {
        this.scanner = scanner;
    }

    public String texto(String rotulo) {
        while (true) {
            String linha = lerLinha(rotulo);
            if (!linha.trim().isEmpty()) {
                return linha.trim();
            }
            System.out.println("Este campo nao pode ficar vazio.");
        }
    }

    public int inteiro(String rotulo) {
        while (true) {
            String linha = lerLinha(rotulo).trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    public double decimal(String rotulo) {
        while (true) {
            String linha = lerLinha(rotulo).trim().replace(',', '.');
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor valido (ex: 120,50).");
            }
        }
    }

    public LocalDate data(String rotulo) {
        while (true) {
            String linha = lerLinha(rotulo).trim();
            try {
                return LocalDate.parse(linha, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida. Use o formato dd/MM/yyyy.");
            }
        }
    }

    public LocalDate dataOuHoje(String rotulo) {
        while (true) {
            String linha = lerLinha(rotulo).trim();
            if (linha.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(linha, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida. Use o formato dd/MM/yyyy ou Enter para hoje.");
            }
        }
    }

    private String lerLinha(String rotulo) {
        System.out.print(rotulo);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            throw new EntradaEncerradaException();
        }
    }
}
