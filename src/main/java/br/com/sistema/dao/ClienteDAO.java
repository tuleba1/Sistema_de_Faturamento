/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.dao;
import br.com.sistema.model.Cliente;
import java.util.List;
/**
 *
 * @author tulio
 */
public interface ClienteDAO {
    void salvar(Cliente cliente);
    Cliente buscarPorId(int id);
    List<Cliente> listarTodos();
    void atualizar(Cliente cliente);
    void deletar(int id);
}