/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.dao;

import br.com.sistema.model.Fatura;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class FaturaDAOArrayList implements FaturaDAO {
    
    private List<Fatura> faturas = new ArrayList<>();
    
    @Override
    public void salvar(Fatura fatura){
        faturas.add(fatura);
    }
    
    @Override
    public Fatura buscarPorNumero(int numero){
        for(Fatura f : faturas){
            if(f.getNumero() == numero){
                return f;
            }
        }
        return null;
    }
    
    @Override
    public List<Fatura> listarTodas(){
        return new ArrayList<>(faturas);
    }
    
    @Override
    public boolean remover(int numero){
        return faturas.removeIf(f -> f.getNumero() == numero);
    }
}
