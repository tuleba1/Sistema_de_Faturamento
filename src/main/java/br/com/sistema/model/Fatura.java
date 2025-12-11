package br.com.sistema.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fatura {

    private int id;
    private Cliente cliente;
    private String mes;  
    private String ano;   
    private List<ItemFatura> itens = new ArrayList<>();

    public Fatura() {}

    public Fatura(int id, Cliente cliente, String mes, String ano) {
        this.id = id;
        this.cliente = cliente;
        this.mes = mes;
        this.ano = ano;
    }

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

    // -----------------------------
    // MÉTODOS DE NEGÓCIO
    // -----------------------------

    public void adicionarItem(ItemFatura item) {
        if (item != null) itens.add(item);
    }

    /**
     * Soma todos os subtotais dos itens (compatível com ItemFatura.calcularSubtotal).
     */
    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemFatura::calcularSubtotal)
                .sum();
    }

    /**
     * Alias para compatibilidade com telas e controllers.
     */
    public double getTotal() {
        return calcularTotal();
    }

    /**
     * Retorna uma data válida baseada em mes/ano.
     * Exemplo:
     * mes = "03", ano = "2025" -> retorna LocalDate.of(2025, 3, 1)
     */
    public LocalDate getData() {
        try {
            int m = Integer.parseInt(mes);
            int a = Integer.parseInt(ano);
            return LocalDate.of(a, m, 1);
        } catch (Exception e) {
            return null; // evita quebra caso mês inválido
        }
    }

    @Override
    public String toString() {
        return "Fatura #" + id + " - " + mes + "/" + ano;
    }
}
