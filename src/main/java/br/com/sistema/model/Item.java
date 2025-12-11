package br.com.sistema.model;

/**
 * Modelo Item com validação simples.
 */
public class Item {

    private int id;
    private String nome;
    private double preco;

    public Item() {
    }

    public Item(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Item(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // -----------------------
    // GETTERS / SETTERS
    // -----------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // -----------------------
    // VALIDAÇÃO
    // -----------------------
    /**
     * Valida os campos do item.
     *
     * Regras aplicadas:
     * - nome não pode ser nulo ou vazio
     * - preco não pode ser negativo
     *
     * @throws Exception com mensagem descritiva caso a validação falhe
     */
    public void validar() throws Exception {
        if (this.nome == null || this.nome.trim().isEmpty()) {
            throw new Exception("Nome do item é obrigatório.");
        }

        // opcional: verificar tamanho mínimo/máximo do nome
        if (this.nome.trim().length() < 2) {
            throw new Exception("Nome do item deve ter ao menos 2 caracteres.");
        }

        if (Double.isNaN(this.preco) || Double.isInfinite(this.preco)) {
            throw new Exception("Preço do item inválido.");
        }

        if (this.preco < 0) {
            throw new Exception("Preço não pode ser negativo.");
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}
