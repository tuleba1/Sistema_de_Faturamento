package br.com.sistema.model;

import java.util.ArrayList;
import java.util.List;

public class Fatura {

    private int id;
    private Cliente cliente;
    private String mes;
    private String ano;
    private List<ItemFatura> itens = new ArrayList<>();

    public Fatura() {}

    // -----------------------------
    // GETTERS E SETTERS
    // -----------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public List<ItemFatura> getItens() {
        return itens;
    }

    public void setItens(List<ItemFatura> itens) {
        this.itens = itens;
    }


    public void adicionarItem(ItemFatura item) {
        itens.add(item);
    }

  
    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemFatura::calcularSubtotal)
                .sum();
    }
}
