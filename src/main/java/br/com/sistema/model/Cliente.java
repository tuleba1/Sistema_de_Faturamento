package br.com.sistema.model;

import java.time.LocalDate;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String endereco;
    private String cpf;
    private LocalDate dataNascimento;

    public Cliente() {}

    public Cliente(int id, String nome, String email, String endereco, String cpf, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public Cliente(String nome, String email, String endereco, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }


    public void validar() throws Exception {

        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome é obrigatório.");
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Exception("CPF é obrigatório.");
        }

        String cpfDigits = cpf.replaceAll("\\D", "");
        if (cpfDigits.length() != 11) {
            throw new Exception("CPF inválido (precisa ter 11 dígitos).");
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@") || !email.contains(".")) {
                throw new Exception("Email inválido.");
            }
        }

        if (dataNascimento != null && dataNascimento.isAfter(LocalDate.now())) {
            throw new Exception("Data de nascimento não pode ser no futuro.");
        }
    }

    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
}
