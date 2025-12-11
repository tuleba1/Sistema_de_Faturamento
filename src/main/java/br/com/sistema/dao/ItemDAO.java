package br.com.sistema.dao;

import br.com.sistema.model.Item;
import java.util.List;

public interface ItemDAO {
    void salvar(Item entidade);
    Item buscarPorId(int id);
    List<Item> buscarTodos();
    void atualizar(Item entidade);
    void remover(int id);
}
