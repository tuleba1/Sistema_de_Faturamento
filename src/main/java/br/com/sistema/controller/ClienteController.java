/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.controller;

import br.com.sistema.model.Cliente;
import br.com.sistema.service.ClienteService;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author tulio
 */
public class ClienteController {
    
    private ClienteService clienteService;
    
    public ClienteController(ClienteService service){
        this.clienteService = service;
    }
    
    public void cadastrar(
            String nome,
            String email,
            String endereco,
            String estado,
            String paisNascimento,
            LocalDate dataNascimento
    )
            throws Exception{
        Cliente cliente = new Cliente(
        0,
        nome,
        email,
        endereco,
        estado,
        paisNascimento,
        dataNascimento);
        
        clienteService.cadastrarCliente(cliente);
        
    }
    
    public List<Cliente> listar(){
        return clienteService.listarClientes();
    }
    
    public Cliente buscar(int id){
        return clienteService.buscarPorId(id);
    }
    
    public void atualizar(Cliente cliente) throws Exception {
        clienteService.atualizarCliente(cliente);
    }
    
    public void deletar(int id) {
        clienteService.deletarCliente(id);
    }
}
