/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.service;

import br.com.sistema.dao.ItemDAO;
import br.com.sistema.exception.CampoInvalidoException;
import br.com.sistema.model.Item;

import java.util.List;

public class ItemService {

    private final ItemDAO itemDAO;
    private int idSequence = 1; // controle simples de id

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void cadastrar(Item item) throws CampoInvalidoException {
        if (item == null) {
            throw new CampoInvalidoException("Item não pode ser nulo.");
        }
        item.setId(idSequence++); // atribui id automático
        item.validar();
        itemDAO.salvar(item);
    }

    public List<Item> listarTodos() {
        return itemDAO.buscarTodos();
    }

    public Item buscarPorId(int id) {
        return itemDAO.buscarPorId(id);
    }

    public void atualizar(Item item) throws CampoInvalidoException {
        if (item == null) {
            throw new CampoInvalidoException("Item não pode ser nulo.");
        }
        if (item.getId() <= 0) {
            throw new CampoInvalidoException("ID do item inválido.");
        }
        item.validar();
        itemDAO.atualizar(item);
    }

    public void remover(int id) {
        itemDAO.remover(id);
    }
}