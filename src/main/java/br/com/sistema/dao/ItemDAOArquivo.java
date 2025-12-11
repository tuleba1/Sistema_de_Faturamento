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
                pw.println(
                        i.getId() + ";" +
                        i.getNome() + ";" +
                        i.getPreco()
                );
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

                if (dados.length < 3) continue;

                Item item = new Item(
                        dados[1],
                        Double.parseDouble(dados[2])
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
    public void salvar(Item entidade) {
        List<Item> itens = carregarArquivo();
        entidade.setId(gerarNovoId(itens));
        itens.add(entidade);
        salvarNoArquivo(itens);
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
    public void atualizar(Item entidade) {
        List<Item> itens = carregarArquivo();

        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId() == entidade.getId()) {
                itens.set(i, entidade);
                salvarNoArquivo(itens);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        List<Item> itens = carregarArquivo();
        itens.removeIf(i -> i.getId() == id);
        salvarNoArquivo(itens);
    }


    private int gerarNovoId(List<Item> itens) {
        if (itens.isEmpty()) return 1;
        return itens.get(itens.size() - 1).getId() + 1;
    }
}
