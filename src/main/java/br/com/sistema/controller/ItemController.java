package br.com.sistema.controller;

import br.com.sistema.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemController {

    private final List<Item> itens = new ArrayList<>();
    private final AtomicInteger proxId = new AtomicInteger(1);

    private final String FILE_NAME = "itens.txt";

    public Item cadastrar(String nome, double preco) throws Exception {
        Item item = new Item(nome, preco);
        item.validar();

        item.setId(proxId.getAndIncrement());
        itens.add(item);

        salvarEmArquivo();

        return item;
    }

    public List<Item> listar() {
        return new ArrayList<>(itens);
    }

    public Item buscarPorId(int id) {
        return itens.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    
    public void salvarEmArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Item i : itens) {
                pw.println(i.getId() + ";" + i.getNome() + ";" + i.getPreco());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarDoArquivo() {
        itens.clear();
        proxId.set(1);

        File arquivo = new File(FILE_NAME);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");

                Item i = new Item(
                        Integer.parseInt(p[0]),
                        p[1],
                        Double.parseDouble(p[2])
                );

                itens.add(i);

                if (i.getId() >= proxId.get()) {
                    proxId.set(i.getId() + 1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
