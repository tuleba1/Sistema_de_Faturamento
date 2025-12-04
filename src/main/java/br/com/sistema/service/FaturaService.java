/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.service;

import br.com.sistema.dao.ClienteDAO;
import br.com.sistema.dao.FaturaDAO;
import br.com.sistema.dao.ItemDAO;
import br.com.sistema.model.Calculavel;
import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import java.util.List;

/**
 *
 * @author tulio
 */
public class FaturaService {
    private FaturaDAO faturaDAO;
    
    public FaturaService (FaturaDAO faturaDAO){
        this.faturaDAO = faturaDAO;
    }
    
    public void criar (Fatura fatura, Cliente cliente){
        cliente.adicionarFatura(fatura);
        faturaDAO.salvar(fatura);
    }
    
    public Fatura buscar(int numero){
        return faturaDAO.buscarPorNumero(numero);
    }
    
    public List<Fatura> listar(){
        return faturaDAO.listarTodas();
    }
    
    public boolean remover(int numero){
        return faturaDAO.remover(numero);
    }
    
    public void adicionarItem(int numero, Calculavel item){
        Fatura f = faturaDAO.buscarPorNumero(numero);
        if  (f != null){
            f.adicionarItem((Item) item);
        }
    }

    public void criar(Fatura fatura) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
    
}

    

