package br.com.sistema.model;

public class ItemFatura implements Calculavel {

    private Item item;
    private int quantidade;

    public ItemFatura() {}

    public ItemFatura(Item item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

  
    @Override
    public double calcularSubtotal() {
        if (item == null) return 0.0;
        return item.getPreco() * quantidade;
    }

    public double getSubtotal() {
        return calcularSubtotal();
    }

    @Override
    public String toString() {
        return item.getNome() + " x" + quantidade + " = R$ " + calcularSubtotal();
    }
}
