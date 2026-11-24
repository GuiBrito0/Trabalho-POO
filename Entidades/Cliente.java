package Entidades;

import java.time.LocalDate;
import java.time.Period;

import Excecoes.DadoInvalidoException;

public class Cliente {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Cnh cnh;

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Cnh cnh) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DadoInvalidoException("Informe o nome do cliente.");
        }
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            throw new DadoInvalidoException("Data de nascimento invalida.");
        }
        if (cnh == null) {
            throw new DadoInvalidoException("Informe a categoria da CNH.");
        }
        this.nome = nome.trim();
        this.cpf = normalizarCpf(cpf);
        this.dataNascimento = dataNascimento;
        this.cnh = cnh;
    }

    public static String normalizarCpf(String cpf) {
        String digitos = cpf == null ? "" : cpf.replaceAll("\\D", "");
        if (digitos.length() != 11) {
            throw new DadoInvalidoException("O CPF deve ter 11 digitos.");
        }
        return digitos;
    }

    public int idadeEm(LocalDate data) {
        return Period.between(dataNascimento, data).getYears();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Cnh getCnh() {
        return cnh;
    }
}
