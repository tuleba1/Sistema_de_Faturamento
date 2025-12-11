package br.com.sistema.controller;

import br.com.sistema.model.Cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ClienteController {

    private final List<Cliente> clientes = new ArrayList<>();
    private final AtomicInteger proxId = new AtomicInteger(1);

    public ClienteController() {
     
    }

   
    public Cliente cadastrar(String nome, String email, String endereco, String cpf, LocalDate dataNascimento) throws Exception {
        Cliente c = new Cliente(nome, email, endereco, cpf, dataNascimento);
    
        c.validar();


        Cliente existente = buscarPorCPF(cpf);
        if (existente != null) {
            throw new Exception("Já existe cliente cadastrado com este CPF.");
        }

  
        c.setId(proxId.getAndIncrement());
        clientes.add(c);

        return c;
    }

    public Cliente buscarPorCPF(String cpf) {
        if (cpf == null) return null;
        String cleaned = cpf.replaceAll("\\D", "");
        for (Cliente c : clientes) {
            if (c.getCpf() != null && c.getCpf().replaceAll("\\D", "").equals(cleaned)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    public void removerPorId(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }

    public void atualizar(Cliente cliente) throws Exception {
        if (cliente == null) throw new Exception("Cliente inválido.");
        cliente.validar();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {

                Cliente porCpf = buscarPorCPF(cliente.getCpf());
                if (porCpf != null && porCpf.getId() != cliente.getId()) {
                    throw new Exception("Outro cliente já utiliza este CPF.");
                }
                clientes.set(i, cliente);
                return;
            }
        }
        throw new Exception("Cliente não encontrado para atualizar.");
    }
}
