/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.sistema.dao;

import java.util.List;

/**
 *
 * @author tulio
 */
interface DAO<T> {
        void salvar(T entidade);

    T buscarPorId(int id);

    List<T> buscarTodos();

    void atualizar(T entidade);

    void remover(int id);
}
