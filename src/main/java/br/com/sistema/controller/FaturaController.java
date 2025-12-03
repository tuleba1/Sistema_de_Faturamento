/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.controller;

import br.com.sistema.model.Calculavel;
import br.com.sistema.model.Fatura;
import br.com.sistema.service.FaturaService;
import java.util.List;

/**
 *
 * @author tulio
 */
public class FaturaController {
    private FaturaService faturaService;
    
    public FaturaController(FaturaService faturaService){
        this.faturaService = faturaService;
    }
    
    
    public void criarFatura(int numero){
        Fatura fatura = new Fatura(numero);
        faturaService.criar(fatura);
    }
    
    public Fatura buscarFatura(int numero){
        return faturaService.buscar(numero);
    }
    
    public List<Fatura> listarFaturas(){
        return faturaService.listar();
    }
    
    public boolean removerFatura(int numero){
        return faturaService.remover(numero);
    }
    
    public boolean adicionarItemNaFatura(int numeroFatura, Calculavel item){
        Fatura f = faturaService.buscar(numeroFatura);
        
        if (f == null){
            System.out.println("Fatura não encontrada: " + numeroFatura);
            return false;
        }
        
        f.adicionarItem(item);
        return true;
    }
    
    public double calcularTotal(int numeroFatura){
        Fatura f = faturaService.buscar(numeroFatura);
        
        if (f == null){
            System.out.println("Faturanão enbcontrada: " + numeroFatura);
            return 0;
        }
        
        return f.calcularTotal();
    }
    
}
