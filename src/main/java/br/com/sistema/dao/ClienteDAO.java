
package br.com.sistema.dao;
import br.com.sistema.model.Cliente;
import java.util.List;

public interface ClienteDAO {
    void salvar(Cliente cliente);
    Cliente buscarPorId(int id);
    List<Cliente> listarTodos();
    void atualizar(Cliente cliente);
    void deletar(int id);
}