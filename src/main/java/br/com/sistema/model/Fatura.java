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
    private List<Calculavel> itens = new ArrayList<>();
    
    public Fatura(int numero){
        this.numero = numero;
    }
    
    public void adicionarItem(Calculavel item){
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
}
