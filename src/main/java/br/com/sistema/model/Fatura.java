/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tulio
 */
public class Fatura implements Calculavel {
    private int numero;
    private Cliente cliente;
    private String mes;
    private int ano;

    private List<Calculavel> itens = new ArrayList<>();

    public Fatura(int numero, Cliente cliente, String mes, int ano) {
        this.numero = numero;
        this.cliente = cliente;
        this.mes = mes;
        this.ano = ano;
    }

    public void adicionarItem(Calculavel item) {
        itens.add(item);
    }

    @Override
    public double calcularTotal() {
          double total = 0;

          for (Calculavel item : itens) {
              total += item.calcularTotal(); 
          }
          return total;
    }
    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public List<Calculavel> getItens() {
        return itens;
    }
}
