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

  public Item(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

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
        if (quantidade <= 0) {
            throw new CampoInvalidoException("A quantidade deve ser maior que zero.");
        }
        if (preco <= 0) {
            throw new CampoInvalidoException("O preço deve ser maior que zero.");
        }
    }


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