/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author tulio
 */
public class Cliente extends EntidadeBase {
    private String nome;
    private String email;
    private String endereco;
    private String estado;
    private String paisNascimento;
    private LocalDate dataNascimento;
    private List<Fatura> faturas = new ArrayList<>();
    
    public Cliente(
            String nome, String email, String endereco, String estado, String paisNascimento, LocalDate dataNascimento){
        this.setId(id);
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.estado = estado;
        this.paisNascimento = paisNascimento;
        this.dataNascimento = dataNascimento;
    }
    
    
    //Area para validação de campos do cliente
    
    @Override
    public void validar() throws Exception{
        if (nome == null || nome.isBlank()){
            throw new Exception("Nome do cliente é obrigatório.");
        }
        
        if (email == null || !email.contains("@")){
            throw new Exception("Email inválido.");
        }
        
        if (endereco == null || endereco.isBlank()){
            throw new Exception("Estado é obrigatório");
        }
        
        if (paisNascimento == null || paisNascimento.isBlank()){
            throw new Exception("País de nascimento é obrigatório");
        }
        
        if(dataNascimento == null){
            throw new Exception("Data de nascimento é obrigatória");
        }
        
        if(dataNascimento.isAfter(LocalDate.now())){
            throw new Exception("Data de nascimento inválida");
        } //Data de nascimento acima da data atual
    }
    
    public void adicionarFatura(Fatura f){
        faturas.add(f);
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPaisNascimento() {
        return paisNascimento;
    }

    public void setPaisNascimento(String paisNascimento) {
        this.paisNascimento = paisNascimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getDataNascimentoFormatada() {
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(data);
    }
    
    public List<Fatura> getFaturas(){
        return faturas;
    }
    
    //Data de nascimento no formato para a interface
    
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
