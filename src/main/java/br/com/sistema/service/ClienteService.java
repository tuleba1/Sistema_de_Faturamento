/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.service;

import br.com.sistema.dao.ClienteDAO;
import br.com.sistema.model.Cliente;
import java.util.List;

/**
 *
 * @author tulio
 */
public class ClienteService {
    private ClienteDAO clienteDAO;
    private int idSequence = 1;
    public ClienteService(ClienteDAO clienteDAO){
        this.clienteDAO= clienteDAO;
    }
    
    public void cadastrarCliente(Cliente cliente) throws Exception{
    cliente.setId(idSequence++);
    cliente.validar();
    clienteDAO.salvar(cliente);
    }
    
    public List<Cliente> listarClientes(){
        return clienteDAO.listarTodos();

    }
    
    public Cliente buscarPorId(int id){
        return clienteDAO.buscarPorId(id);
    }
    
    public void atualizarCliente(Cliente cliente) throws Exception {
        cliente.validar();
        clienteDAO.atualizar(cliente);
    }
    
    public void deletarCliente(int id){
        clienteDAO.deletar(id);
    }
}

    
