/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.dao;

import br.com.sistema.dao.DAO;
import br.com.sistema.model.EntidadeBase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tulio
 */
public abstract class AbstractDAOArrayList<T extends EntidadeBase> implements DAO<T> {

    protected final List<T> lista = new ArrayList<>();

    @Override
    public void salvar(T entidade) {
        lista.add(entidade);
    }

    @Override
    public T buscarPorId(int id) {
        for (T e : lista) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<T> buscarTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public void atualizar(T entidade) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == entidade.getId()) {
                lista.set(i, entidade);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        lista.removeIf(e -> e.getId() == id);
    }
}
