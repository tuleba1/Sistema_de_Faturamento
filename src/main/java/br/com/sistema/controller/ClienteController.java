package br.com.sistema.controller;

import br.com.sistema.model.Cliente;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteController {

    private final List<Cliente> clientes = new ArrayList<>();
    private final AtomicInteger proxId = new AtomicInteger(1);

    private final String FILE_NAME = "clientes.txt";

    public ClienteController() {}


 
    public Cliente cadastrar(String nome, String email, String endereco, String cpf, LocalDate dataNascimento) throws Exception {

        Cliente c = new Cliente(nome, email, endereco, cpf, dataNascimento);
        c.validar();

        if (buscarPorCPF(cpf) != null)
            throw new Exception("Já existe cliente com esse CPF.");

        c.setId(proxId.getAndIncrement());
        clientes.add(c);

        salvarEmArquivo();

        return c;
    }


    public Cliente buscarPorCPF(String cpf) {
        if (cpf == null) return null;
        String cleaned = cpf.replaceAll("\\D", "");

        return clientes.stream()
                .filter(c -> c.getCpf() != null && c.getCpf().replaceAll("\\D", "").equals(cleaned))
                .findFirst()
                .orElse(null);
    }

    public Cliente buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

 
    public void removerPorId(int id) {
        clientes.removeIf(c -> c.getId() == id);
        salvarEmArquivo();
    }

    public void atualizar(Cliente cliente) throws Exception {
        cliente.validar();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {

                Cliente outro = buscarPorCPF(cliente.getCpf());
                if (outro != null && outro.getId() != cliente.getId())
                    throw new Exception("CPF já pertence a outro cliente.");

                clientes.set(i, cliente);
                salvarEmArquivo();
                return;
            }
        }
        throw new Exception("Cliente não encontrado.");
    }

    public void salvarEmArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Cliente c : clientes) {
                pw.println(
                        c.getId() + ";" +
                        c.getNome() + ";" +
                        c.getCpf() + ";" +
                        c.getEmail() + ";" +
                        c.getEndereco() + ";" +
                        (c.getDataNascimento() != null ? c.getDataNascimento() : "")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarDoArquivo() {
        clientes.clear();
        proxId.set(1);

        File arquivo = new File(FILE_NAME);
        if (!arquivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");

                Cliente c = new Cliente(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[3],
                        partes[4],
                        partes[2],
                        partes[5].isEmpty() ? null : LocalDate.parse(partes[5])
                );

                clientes.add(c);

                if (c.getId() >= proxId.get()) {
                    proxId.set(c.getId() + 1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
