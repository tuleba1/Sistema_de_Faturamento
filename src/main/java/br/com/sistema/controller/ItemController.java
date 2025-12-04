/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.controller;

import br.com.sistema.model.Item;
import br.com.sistema.service.ItemService;
import java.util.List;

/**
 *
 * @author tulio
 */
public class ItemController {
      private ItemService service;

    public ItemController(ItemService service){
        this.service = service;
    }

    public void cadastrar(String nome, int quantidade, double preco) throws Exception {
        Item item = new Item(nome, quantidade, (int) preco);
        item.validar();
        service.cadastrar(item);
    }

    public List<Item> listar() {
        return service.listarTodos();
    }

    public Item buscar(int id) {
        return service.buscarPorId(id);
    }

    public void atualizar(Item item) throws Exception {
        service.atualizar(item);
    }

    public void deletar(int id) {
        service.remover(id);
    }
}
