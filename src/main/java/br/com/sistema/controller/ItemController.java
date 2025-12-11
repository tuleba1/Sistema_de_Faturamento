package br.com.sistema.controller;

import br.com.sistema.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ItemController {

    private final List<Item> itens = new ArrayList<>();
    private final AtomicInteger proxId = new AtomicInteger(1);

    public ItemController() {
     
    }

  
    public Item cadastrar(String nome, double valor) throws Exception {
        Item item = new Item(nome, valor);
        item.validar(); 

       
        for (Item i : itens) {
            if (i.getNome() != null && i.getNome().equalsIgnoreCase(nome.trim())) {
                throw new Exception("Já existe um item cadastrado com esse nome.");
            }
        }

        item.setId(proxId.getAndIncrement());
        itens.add(item);

        return item;
    }

    public List<Item> listarTodos() {
        return new ArrayList<>(itens);
    }

    public Item buscarPorId(int id) {
        for (Item i : itens) {
            if (i.getId() == id) return i;
        }
        return null;
    }

    public void atualizar(Item item) throws Exception {
        if (item == null) throw new Exception("Item inválido.");
        item.validar();

        for (int idx = 0; idx < itens.size(); idx++) {
            if (itens.get(idx).getId() == item.getId()) {
                // garante que não haja outro com mesmo nome
                for (Item other : itens) {
                    if (other.getId() != item.getId() && other.getNome().equalsIgnoreCase(item.getNome())) {
                        throw new Exception("Outro item já utiliza este nome.");
                    }
                }
                itens.set(idx, item);
                return;
            }
        }
        throw new Exception("Item não encontrado para atualizar.");
    }

    public void remover(int id) {
        itens.removeIf(i -> i.getId() == id);
    }
}
