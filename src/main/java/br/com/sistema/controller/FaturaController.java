package br.com.sistema.controller;

import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import br.com.sistema.service.ClienteService;
import br.com.sistema.service.FaturaService;
import br.com.sistema.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

public class FaturaController {

    private FaturaService faturaService;
    private ClienteService clienteService;
    private ItemService itemService;

    public FaturaController(FaturaService faturaService, ClienteService clienteService, ItemService itemService) {
        this.faturaService = faturaService;
        this.clienteService = clienteService;
        this.itemService = itemService;
    }

    // ---------------------------------------------------------
    // CRIAR FATURA PARA UM CLIENTE
    // ---------------------------------------------------------
    public void criarFatura(int numero, Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }

        Fatura fatura = new Fatura(numero, cliente);
        faturaService.criar(fatura);

        // adiciona a fatura ao cliente (se desejar manter histórico no cliente)
        cliente.adicionarFatura(fatura);
    }


    public boolean adicionarItemNaFatura(int numeroFatura, Item item) {
        Fatura f = faturaService.buscar(numeroFatura);

        if (f == null) {
            return false; // fatura inexistente
        }

        f.adicionarItem(item);
        return true;
    }


    public Fatura buscar(int numero) {
        return faturaService.buscar(numero);
    }


    public List<Fatura> listar() {
        return faturaService.listar();
    }

    public boolean removerFatura(int numero) {
        return faturaService.remover(numero);
    }

    public List<Fatura> listarFaturasDoCliente(Cliente cliente) {
        if (cliente == null) return List.of();

        return faturaService.listar()
                .stream()
                .filter(f -> f.getCliente() != null &&
                             f.getCliente().getId() == cliente.getId())
                .collect(Collectors.toList());
    }


    public double calcularTotalCliente(Cliente cliente) {
        return listarFaturasDoCliente(cliente)
                .stream()
                .mapToDouble(Fatura::calcularTotal)
                .sum();
    }
}
