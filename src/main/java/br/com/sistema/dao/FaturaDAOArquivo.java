package br.com.sistema.dao;

import br.com.sistema.model.Fatura;
import br.com.sistema.model.Cliente;
import br.com.sistema.model.Item;
import br.com.sistema.model.ItemFatura;
import br.com.sistema.service.ClienteService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FaturaDAOArquivo implements FaturaDAO {

    private final String nomeArquivo;
    private final ClienteService clienteService;
    private final ItemDAO itemDAO;

    public FaturaDAOArquivo(String nomeArquivo, ClienteService clienteService, ItemDAO itemDAO) {
        this.nomeArquivo = nomeArquivo;
        this.clienteService = clienteService;
        this.itemDAO = itemDAO;
    }

    private void salvarNoArquivo(List<Fatura> faturas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nomeArquivo))) {

            for (Fatura f : faturas) {
                StringBuilder itensStr = new StringBuilder();

             
                if (f.getItens() != null && !f.getItens().isEmpty()) {
                    for (ItemFatura itf : f.getItens()) {
                        if (itf != null && itf.getItem() != null) {
                            itensStr.append(itf.getItem().getId())
                                    .append(":")
                                    .append(itf.getQuantidade())
                                    .append("|");
                        }
                    }
                    
                    if (itensStr.length() > 0) itensStr.setLength(itensStr.length() - 1);
                }

          
                pw.println(
                        f.getId() + ";" +
                        (f.getCliente() != null ? f.getCliente().getId() : "") + ";" +
                        (f.getMes() != null ? f.getMes() : "") + ";" +
                        (f.getAno() != null ? f.getAno() : "") + ";" +
                        itensStr.toString()
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
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";", -1); 

                if (dados.length < 5) {
                    System.err.println("Linha inválida (campos insuficientes): " + linha);
                    continue;
                }

                try {
                    int numero = Integer.parseInt(dados[0].trim());
                    String clienteIdStr = dados[1].trim();
                    String mes = dados[2].trim();
                    String ano = dados[3].trim();
                    String itensRaw = dados[4].trim();

                 
                    Cliente cliente = null;
                    if (!clienteIdStr.isEmpty()) {
                        try {
                            int clienteId = Integer.parseInt(clienteIdStr);
                            cliente = clienteService.buscarPorId(clienteId);
                        } catch (NumberFormatException nfe) {
                            System.err.println("ID de cliente inválido na fatura " + numero + ": " + clienteIdStr);
                        }
                    }

                    if (cliente == null) {
                        System.err.println("Cliente não encontrado (fatura " + numero + "), fatura ignorada.");
                        continue;
                    }

               
                    Fatura f = new Fatura();
                    f.setId(numero);
                    f.setCliente(cliente);
                    f.setMes(mes);
                    f.setAno(ano);

     
                    if (!itensRaw.isEmpty()) {
                        String[] partes = itensRaw.split("\\|");
                        for (String p : partes) {
                            if (p.trim().isEmpty()) continue;
                            String[] it = p.split(":");
                            if (it.length != 2) {
                                System.err.println("Formato de item inválido na fatura " + numero + ": " + p);
                                continue;
                            }
                            try {
                                int itemId = Integer.parseInt(it[0].trim());
                                int quantidade = Integer.parseInt(it[1].trim());

                                Item item = itemDAO.buscarPorId(itemId);
                                if (item == null) {
                                    System.err.println("Item ID " + itemId + " não encontrado (fatura " + numero + "). Item ignorado.");
                                    continue;
                                }

                                ItemFatura itf = new ItemFatura(item, quantidade);
                                f.adicionarItem(itf);

                            } catch (NumberFormatException nfe) {
                                System.err.println("Número inválido ao parsear item na fatura " + numero + ": " + p);
                            }
                        }
                    }

                    lista.add(f);

                } catch (NumberFormatException ex) {
                    System.err.println("Número inválido no registro de fatura: " + linha);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void salvar(Fatura f) {
        List<Fatura> lista = carregarArquivo();
        
        boolean atualizado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == f.getId()) {
                lista.set(i, f);
                atualizado = true;
                break;
            }
        }
        if (!atualizado) lista.add(f);
        salvarNoArquivo(lista);
    }

    @Override
    public Fatura buscarPorNumero(int numero) {
        return carregarArquivo().stream()
                .filter(f -> f.getId() == numero)
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
        boolean ok = lista.removeIf(f -> f.getId() == numero);
        if (ok) salvarNoArquivo(lista);
        return ok;
    }
}
