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
    private List<Item> itens = new ArrayList<>();
    private Cliente cliente;
    
    public Fatura(int numero){
        this.numero = numero;
        this.cliente = cliente;
    }

    public Fatura(int numero, Cliente cliente) {
        throw new UnsupportedOperationException("Sem suporte"); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void adicionarItem(Item item){
        itens.add(item);
    }
    
    @Override
    public double calcularTotal(){
        return itens.stream()
                .mapToDouble(Calculavel::calcularTotal)
                .sum();
    }
    
    public int getNumero(){
        return numero;
    }
    
    public List<Item> getItens() {
    return itens;
}
    
    public Cliente getCliente(){
        return cliente;
    }
}
