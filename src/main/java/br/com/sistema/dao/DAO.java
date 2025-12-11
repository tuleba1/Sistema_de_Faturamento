
package br.com.sistema.dao;

import java.util.List;


interface DAO<T> {
    void salvar(T entidade);

    T buscarPorId(int id);

    List<T> buscarTodos();

    void atualizar(T entidade);

    void remover(int id);
}
