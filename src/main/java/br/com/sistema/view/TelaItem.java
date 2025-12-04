package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.controller.FaturaController;
import br.com.sistema.controller.ItemController;
import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaItem extends JFrame {

    private final ItemController itemController;
    private final ClienteController clienteController;
    private final FaturaController faturaController;

    // Campos
    private JTextField txtNome;
    private JTextField txtQuantidade;
    private JTextField txtValor;

    private JComboBox<String> comboCliente;
    private JComboBox<String> comboFatura;

    private JTextArea txtSaida;

    public TelaItem(ItemController itemController,
                    ClienteController clienteController,
                    FaturaController faturaController) {

        this.itemController = itemController;
        this.clienteController = clienteController;
        this.faturaController = faturaController;

        setTitle("Gerenciamento de Itens");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Campo Nome
        c.gridy = row++;
        form.add(new JLabel("Nome do Item:"), c);
        txtNome = new JTextField();
        c.gridy = row++;
        form.add(txtNome, c);

        // Quantidade
        c.gridy = row++;
        form.add(new JLabel("Quantidade:"), c);
        txtQuantidade = new JTextField();
        c.gridy = row++;
        form.add(txtQuantidade, c);

        // Valor
        c.gridy = row++;
        form.add(new JLabel("Valor Unitário:"), c);
        txtValor = new JTextField();
        c.gridy = row++;
        form.add(txtValor, c);

        // Cliente
        c.gridy = row++;
        form.add(new JLabel("Cliente:"), c);

        comboCliente = new JComboBox<>();
        carregarClientes();
        c.gridy = row++;
        form.add(comboCliente, c);

        // Fatura
        c.gridy = row++;
        form.add(new JLabel("Fatura do Cliente:"), c);

        comboFatura = new JComboBox<>();
        comboCliente.addActionListener(e -> carregarFaturasCliente());
        carregarFaturasCliente();
        c.gridy = row++;
        form.add(comboFatura, c);

        // Botões
        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton btnCadastrar = new JButton("Cadastrar Item");
        JButton btnAdicionar = new JButton("Adicionar à Fatura");

        botoes.add(btnCadastrar);
        botoes.add(btnAdicionar);

        c.gridy = row++;
        form.add(botoes, c);

        add(form, BorderLayout.NORTH);

        // Saída
        txtSaida = new JTextArea();
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);

        // Eventos
        btnCadastrar.addActionListener(e -> cadastrarItem());
        btnAdicionar.addActionListener(e -> adicionarItemAFatura());
    }

    // ------------------------------------------
    // CARREGAR CLIENTES NO COMBOBOX
    // ------------------------------------------
    private void carregarClientes() {
        comboCliente.removeAllItems();
        List<Cliente> clientes = clienteController.listar();

        for (Cliente c : clientes) {
            comboCliente.addItem(c.getId() + " - " + c.getNome());
        }
    }

    // ------------------------------------------
    // CARREGAR FATURAS DO CLIENTE ESCOLHIDO
    // ------------------------------------------
    private void carregarFaturasCliente() {
        comboFatura.removeAllItems();

        if (comboCliente.getSelectedItem() == null) return;

        String texto = comboCliente.getSelectedItem().toString();
        int idCliente = Integer.parseInt(texto.split(" - ")[0]);
        Cliente cliente = clienteController.buscar(idCliente);

        List<Fatura> faturas = faturaController.listarFaturasDoCliente(cliente);

        for (Fatura f : faturas) {
            comboFatura.addItem(f.getNumero() + "");
        }
    }

    private void cadastrarItem() {
        try {
            String nome = txtNome.getText();
            int qtd = Integer.parseInt(txtQuantidade.getText());
            double valor = Double.parseDouble(txtValor.getText());

            itemController.cadastrar(nome, qtd, valor);

            JOptionPane.showMessageDialog(this, "Item cadastrado com sucesso!");

            txtSaida.setText("Item cadastrado:\nNome: " + nome);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    // ------------------------------------------
    // ADICIONAR ITEM À FATURA
    // ------------------------------------------
    private void adicionarItemAFatura() {
        try {
            if (comboCliente.getSelectedItem() == null ||
                comboFatura.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(this, "Selecione cliente e fatura.");
                return;
            }

            // Pegar Cliente
            String textoCli = comboCliente.getSelectedItem().toString();
            int idCliente = Integer.parseInt(textoCli.split(" - ")[0]);
            Cliente cliente = clienteController.buscar(idCliente);

            // Pegar Fatura
            int numeroFatura = Integer.parseInt(comboFatura.getSelectedItem().toString());
            Fatura fatura = faturaController.buscar(numeroFatura);

            // Criar item (draft)
            String nome = txtNome.getText();
            int qtd = Integer.parseInt(txtQuantidade.getText());
            double valor = Double.parseDouble(txtValor.getText());

            Item item = new Item(nome, qtd, (int) valor);
            itemController.cadastrar(item.getNome(), item.getQuantidade(), item.getPreco());
            item.setId(itemController.listar().get(itemController.listar().size() - 1).getId());

            faturaController.adicionarItemNaFatura(numeroFatura, item);

            JOptionPane.showMessageDialog(this, "Item adicionado à fatura!");

            txtSaida.setText("Item adicionado na fatura " + numeroFatura + 
                             " do cliente " + cliente.getNome());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + ex.getMessage());
        }
    }
}
