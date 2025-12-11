package br.com.sistema.controller;

import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import br.com.sistema.model.ItemFatura;
import java.util.ArrayList;
import java.util.List;

public class FaturaController {
    private List<Fatura> faturas = new ArrayList<>();
    private ClienteController clienteController;
    private ItemController itemController;
    private int proxId = 1;

    public FaturaController(ClienteController clienteController, ItemController itemController) {
        this.clienteController = clienteController;
        this.itemController = itemController;
    }

    public Fatura criarFatura(String cpfCliente, String mes, String ano) {
        Cliente cliente = clienteController.buscarPorCPF(cpfCliente);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado com o CPF: " + cpfCliente);
        }
        Fatura f = new Fatura();
        f.setId(proxId++);
        f.setCliente(cliente);   // setCliente
        f.setMes(mes);           // setMes
        f.setAno(ano);           // setAno
        faturas.add(f);
        return f;
    }

    public void adicionarItem(Fatura fatura, int codigoItem, int quantidade) {
        if (fatura == null) throw new RuntimeException("Nenhuma fatura selecionada!");
        Item item = itemController.buscarPorId(codigoItem);
        if (item == null) throw new RuntimeException("Item não encontrado com código: " + codigoItem);

        ItemFatura itf = new ItemFatura();
        itf.setItem(item);            // setItem
        itf.setQuantidade(quantidade);// setQuantidade

        fatura.adicionarItem(itf);    // utiliza getItens internamente
    }

    public List<Fatura> listarPorCliente(String cpfCliente) {
        List<Fatura> res = new ArrayList<>();
        for (Fatura f : faturas) {
            if (f.getCliente() != null && cpfCliente.equals(f.getCliente().getCpf())) {
                res.add(f);
            }
        }
        return res;
    }

    public List<Fatura> listarPorMesAno(String mes, String ano) {
        List<Fatura> res = new ArrayList<>();
        for (Fatura f : faturas) {
            if (mes.equals(f.getMes()) && ano.equals(f.getAno())) res.add(f);
        }
        return res;
    }

    public List<Fatura> listarTodas() {
        return faturas;
    }
}
