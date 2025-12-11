package br.com.sistema.dao;

import br.com.sistema.model.Cliente;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOArquivo implements ClienteDAO {

    private final String nomeArquivo;

    public ClienteDAOArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

 
    private void salvarNoArquivo(List<Cliente> clientes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nomeArquivo))) {

            for (Cliente c : clientes) {
                pw.println(
                        c.getId() + ";" +
                        c.getNome() + ";" +
                        c.getEmail() + ";" +
                        c.getEndereco() + ";" +
                        c.getCpf() + ";" +
                        c.getDataNascimento()  
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    private List<Cliente> carregarArquivo() {
        List<Cliente> lista = new ArrayList<>();

        File file = new File(nomeArquivo);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length < 6) continue; 

                String nome = dados[1];
                String email = dados[2];
                String endereco = dados[3];
                String cpf = dados[4];
                LocalDate nasc = LocalDate.parse(dados[5]);

                Cliente c = new Cliente(
                        nome,
                        email,
                        endereco,
                        cpf,
                        nasc
                );

                c.setId(Integer.parseInt(dados[0]));

                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    @Override
    public void salvar(Cliente cliente) {
        List<Cliente> clientes = carregarArquivo();
        clientes.add(cliente);
        salvarNoArquivo(clientes);
    }


    @Override
    public Cliente buscarPorId(int id) {
        return carregarArquivo().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cliente> listarTodos() {
        return carregarArquivo();
    }


    @Override
    public void atualizar(Cliente cliente) {
        List<Cliente> lista = carregarArquivo();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == cliente.getId()) {
                lista.set(i, cliente);
                salvarNoArquivo(lista);
                return;
            }
        }
    }

    @Override
    public void deletar(int id) {
        List<Cliente> lista = carregarArquivo();
        lista.removeIf(c -> c.getId() == id);
        salvarNoArquivo(lista);
    }
}
