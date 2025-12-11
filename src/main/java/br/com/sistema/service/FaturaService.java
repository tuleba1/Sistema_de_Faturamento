package br.com.sistema.service;

import br.com.sistema.dao.FaturaDAO;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import br.com.sistema.model.ItemFatura;
import java.util.List;

public class FaturaService {

    private FaturaDAO faturaDAO;

    public FaturaService(FaturaDAO faturaDAO) {
        this.faturaDAO = faturaDAO;
    }

    public Fatura buscar(int numero) {
        return faturaDAO.buscarPorNumero(numero);
    }

    public List<Fatura> listar() {
        return faturaDAO.listarTodas();
    }

    public boolean remover(int numero) {
        return faturaDAO.remover(numero);
    }

    // AGORA FUNCIONA: adicionar item COM QUANTIDADE
    public void adicionarItem(int numero, Item item, int quantidade) {
        Fatura f = faturaDAO.buscarPorNumero(numero);

        if (f != null) {
            ItemFatura itemF = new ItemFatura(item, quantidade);
            f.adicionarItem(itemF);
        }
    }
}
