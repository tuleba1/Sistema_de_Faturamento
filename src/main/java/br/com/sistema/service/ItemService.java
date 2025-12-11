package br.com.sistema.service;

import br.com.sistema.dao.ItemDAO;
import br.com.sistema.model.Item;
import java.util.List;

public class ItemService {

    private final ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void salvar(Item item) throws Exception {
        if (item == null) {
            throw new Exception("Item inválido.");
        }
        item.validar();
        itemDAO.salvar(item);
    }

    public List<Item> buscarTodos() {
        return itemDAO.buscarTodos();
    }

    public Item buscarPorId(int id) {
        return itemDAO.buscarPorId(id);
    }

    public void atualizar(Item item) throws Exception {
        if (item == null) {
            throw new Exception("Item inválido.");
        }
        item.validar();
        itemDAO.atualizar(item);
    }

    public void remover(int id) {
        itemDAO.remover(id);
    }
}
