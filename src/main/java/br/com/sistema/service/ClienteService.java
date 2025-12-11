package br.com.sistema.service;

import br.com.sistema.model.Cliente;
import br.com.sistema.util.FileManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private List<Cliente> clientes = new ArrayList<>();
    private final String CAMINHO = "clientes.txt";

    public ClienteService() {
        carregarDoArquivo();
    }


    public void cadastrarCliente(Cliente cliente) throws Exception {
        cliente.validar();

        // Evitar CPF duplicado
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cliente.getCpf())) {
                throw new Exception("CPF já cadastrado.");
            }
        }

 
        cliente.setId(gerarNovoId());

        clientes.add(cliente);
        salvarNoArquivo();
    }


    public List<Cliente> listarClientes() {
        return clientes;
    }


    public Cliente buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public void atualizarCliente(Cliente clienteAtualizado) throws Exception {
        clienteAtualizado.validar();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == clienteAtualizado.getId()) {
                clientes.set(i, clienteAtualizado);
                salvarNoArquivo();
                return;
            }
        }

        throw new Exception("Cliente não encontrado.");
    }


    public void deletarCliente(int id) {
        clientes.removeIf(c -> c.getId() == id);
        salvarNoArquivo();
    }



    private void salvarNoArquivo() {
        try {
            List<String> linhas = new ArrayList<>();

            for (Cliente c : clientes) {
                String linha = String.join(";",
                        String.valueOf(c.getId()),
                        c.getNome(),
                        c.getEmail(),
                        c.getEndereco(),
                        c.getCpf(),
                        c.getDataNascimento().toString() // formato ISO
                );
                linhas.add(linha);
            }

            FileManager.salvar(CAMINHO, linhas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarDoArquivo() {
        try {
            List<String> linhas = FileManager.ler(CAMINHO);

            for (String linha : linhas) {
                String[] partes = linha.split(";");

                if (partes.length < 6) continue;

                Cliente c = new Cliente(
                        partes[1],               // nome
                        partes[2],               // email
                        partes[3],               // endereco
                        partes[4],               // cpf
                        LocalDate.parse(partes[5]) // data nascimento
                );

                c.setId(Integer.parseInt(partes[0])); // id
                clientes.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int gerarNovoId() {
        if (clientes.isEmpty()) return 1;
        return clientes.get(clientes.size() - 1).getId() + 1;
    }
}
