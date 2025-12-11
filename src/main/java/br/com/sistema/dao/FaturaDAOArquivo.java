package br.com.sistema.dao;

import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import br.com.sistema.model.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FaturaDAOArquivo implements FaturaDAO {

    private final String nomeArquivo;

    public FaturaDAOArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    private void salvarNoArquivo(List<Fatura> faturas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nomeArquivo))) {

            for (Fatura f : faturas) {

                StringBuilder itens = new StringBuilder();
                for (var item : f.getItens()) {
                    itens.append(((Item)item).getId()).append(",");
                }

                pw.println(
                    f.getNumero() + ";" +
                    f.getCliente().getId() + ";" +
                    f.getMes() + ";" +
                    f.getAno() + ";" +
                    itens
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Fatura> carregarArquivo() {
        List<Fatura> lista = new ArrayList<>();

        File file = new File(nomeArquivo);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                int numero = Integer.parseInt(dados[0]);
                int clienteId = Integer.parseInt(dados[1]);
                String mes = dados[2];
                int ano = Integer.parseInt(dados[3]);

                Cliente cliente = new Cliente("","","","","", java.time.LocalDate.now());
                cliente.setId(clienteId);

                Fatura f = new Fatura(numero, cliente, mes, ano);

                lista.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void salvar(Fatura f) {
        List<Fatura> lista = carregarArquivo();
        lista.add(f);
        salvarNoArquivo(lista);
    }

    @Override
    public Fatura buscarPorNumero(int numero) {
        return carregarArquivo().stream()
                .filter(f -> f.getNumero() == numero)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Fatura> listarTodas() {
        return carregarArquivo();
    }

    @Override
    public boolean remover(int numero) {
        List<Fatura> lista = carregarArquivo();
        boolean ok = lista.removeIf(f -> f.getNumero() == numero);
        salvarNoArquivo(lista);
        return ok;
    }
}
