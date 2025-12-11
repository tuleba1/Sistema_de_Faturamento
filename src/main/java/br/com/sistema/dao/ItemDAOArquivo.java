package br.com.sistema.dao;

import br.com.sistema.model.Item;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOArquivo implements ItemDAO {

    private final String nomeArquivo;

    public ItemDAOArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    private void salvarNoArquivo(List<Item> itens) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Item i : itens) {
                pw.println(i.getId() + ";" +
                           i.getNome() + ";" +
                           i.getQuantidade() + ";" +
                           i.getPreco());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Item> carregarArquivo() {
        List<Item> lista = new ArrayList<>();

        File file = new File(nomeArquivo);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                Item item = new Item(
                        dados[1],
                        Integer.parseInt(dados[2]),
                        Double.parseDouble(dados[3])
                );
                item.setId(Integer.parseInt(dados[0]));
                lista.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void salvar(Item item) {
        List<Item> lista = carregarArquivo();
        lista.add(item);
        salvarNoArquivo(lista);
    }

    @Override
    public Item buscarPorId(int id) {
        return carregarArquivo().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Item> buscarTodos() {
        return carregarArquivo();
    }

    @Override
    public void atualizar(Item item) {
        List<Item> lista = carregarArquivo();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == item.getId()) {
                lista.set(i, item);
                salvarNoArquivo(lista);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        List<Item> lista = carregarArquivo();
        lista.removeIf(i -> i.getId() == id);
        salvarNoArquivo(lista);
    }
}
