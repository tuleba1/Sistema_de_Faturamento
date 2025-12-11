
package br.com.sistema.dao;

import br.com.sistema.model.Fatura;
import java.util.List;

public interface FaturaDAO {
    void salvar(Fatura fatura);
    Fatura buscarPorNumero(int numero);
    List<Fatura> listarTodas();
    boolean remover(int numero);
}
