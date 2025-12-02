/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.dao;

import br.com.sistema.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOArrayList implements ClienteDAO {

    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {            
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        return clientes;
    }

    @Override
    public void atualizar(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {
                clientes.set(i, cliente);
                return;
            }
        }
    }

    @Override
    public void deletar(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }
}