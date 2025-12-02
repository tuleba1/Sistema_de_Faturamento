/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.model;

import br.com.sistema.exception.CampoInvalidoException;


/**
 *
 * @author tulio
 */
public class Item extends EntidadeBase implements Calculavel {

    private String nome;
    private double preco;
    private int quantidade;

    public Item() {}

    public Item(int id, String nome, double preco, int quantidade) {
        this.setId(id);
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    @Override
    public double calcularTotal() {
        return preco * quantidade;
    }

    @Override
    public void validar() throws CampoInvalidoException {
        if (nome == null || nome.isBlank()) {
            throw new CampoInvalidoException("Nome do item é obrigatório.");
        }
        if (preco < 0) {
            throw new CampoInvalidoException("Preço do item não pode ser negativo.");
        }
        if (quantidade < 0) {
            throw new CampoInvalidoException("Quantidade não pode ser negativa.");
        }
    }

    // getters / setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() {
        return getId() + " - " + nome + " (Qtd: " + quantidade + ", Preço: " + preco + ")";
    }
}