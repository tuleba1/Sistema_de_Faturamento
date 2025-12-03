/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.dao;

import br.com.sistema.model.Fatura;
import java.util.List;

/**
 *
 * @author tulio
 */
public interface FaturaDAO {
    void salvar(Fatura fatura);
    Fatura buscarPorNumero(int numero);
    List<Fatura> listarTodas();
    boolean remover(int numero);
}
