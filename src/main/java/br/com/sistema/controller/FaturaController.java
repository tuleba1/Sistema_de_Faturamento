package br.com.sistema.controller;

import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import br.com.sistema.model.ItemFatura;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FaturaController {

    private final List<Fatura> faturas = new ArrayList<>();
    private int proxId = 1;

    private final ClienteController clienteController;
    private final ItemController itemController;

    private final String FILE_NAME = "faturas.txt";

    public FaturaController(ClienteController cc, ItemController ic) {
        this.clienteController = cc;
        this.itemController = ic;
    }

    public Fatura criarFatura(String cpfCliente, String mes, String ano) {
        Cliente cliente = clienteController.buscarPorCPF(cpfCliente);

        if (cliente == null)
            throw new RuntimeException("Cliente não encontrado!");

        Fatura f = new Fatura(proxId++, cliente, mes, ano);
        faturas.add(f);

        salvarEmArquivo();

        return f;
    }

    public void adicionarItem(Fatura fatura, int codigoItem, int quantidade) {
        Item item = itemController.buscarPorId(codigoItem);

        if (item == null)
            throw new RuntimeException("Item não encontrado!");

        ItemFatura itf = new ItemFatura(item, quantidade);
        fatura.adicionarItem(itf);

        salvarEmArquivo();
    }

    public List<Fatura> listar() {
        return new ArrayList<>(faturas);
    }

  
    public void salvarEmArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Fatura f : faturas) {
                pw.println(
                        f.getId() + ";" +
                        f.getCliente().getCpf() + ";" +
                        f.getMes() + ";" +
                        f.getAno() + ";" +
                        f.getItens().size() + ";" +
                        f.getTotal()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarDoArquivo() {
        faturas.clear();
        proxId = 1;

        File arquivo = new File(FILE_NAME);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");

                int id = Integer.parseInt(p[0]);
                String cpf = p[1];
                String mes = p[2];
                String ano = p[3];

                Cliente cliente = clienteController.buscarPorCPF(cpf);

                Fatura f = new Fatura(id, cliente, mes, ano);
                faturas.add(f);

                if (id >= proxId) proxId = id + 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
