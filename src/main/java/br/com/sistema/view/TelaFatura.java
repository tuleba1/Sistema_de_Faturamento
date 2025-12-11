package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.controller.FaturaController;
import br.com.sistema.controller.ItemController;
import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class TelaFatura extends JFrame {

    private final FaturaController faturaController;
    private final ClienteController clienteController;
    private final ItemController itemController;

    private JTextField txtNumero;
    private JComboBox<String> comboMes;
    private JComboBox<Integer> comboAno;
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboItem;

    private JTextArea txtSaida;

    private DefaultListModel<String> itensSelecionadosModel = new DefaultListModel<>();
    private JList<String> listaItensSelecionados;
    private List<Item> itensSelecionados = new ArrayList<>();

    public TelaFatura(FaturaController faturaController,
                      ClienteController clienteController,
                      ItemController itemController) {

        this.faturaController = faturaController;
        this.clienteController = clienteController;
        this.itemController = itemController;

        setTitle("Gerenciamento de Faturas");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        carregarClientes();
        carregarItems();
        listarFaturasNaSaida();
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Número da fatura
        c.gridx = 0; c.gridy = row;
        painel.add(new JLabel("Número da Fatura:"), c);
        txtNumero = new JTextField();
        c.gridx = 1;
        painel.add(txtNumero, c);

        // Cliente
        row++;
        c.gridx = 0; c.gridy = row;
        painel.add(new JLabel("Cliente:"), c);
        comboCliente = new JComboBox<>();
        c.gridx = 1;
        painel.add(comboCliente, c);

        // Mês
        row++;
        c.gridx = 0; c.gridy = row;
        painel.add(new JLabel("Mês:"), c);

        comboMes = new JComboBox<>();
        String[] meses = new DateFormatSymbols(new Locale("pt", "BR")).getMonths();
        for (int i = 0; i < 12; i++) comboMes.addItem(meses[i]);
        c.gridx = 1;
        painel.add(comboMes, c);

        // Ano
        row++;
        int anoAtual = LocalDate.now().getYear();
        Integer[] anos = IntStream.rangeClosed(2000, anoAtual).boxed().toArray(Integer[]::new);

        c.gridx = 0; c.gridy = row;
        painel.add(new JLabel("Ano:"), c);
        comboAno = new JComboBox<>(anos);
        comboAno.setSelectedItem(anoAtual);
        c.gridx = 1;
        painel.add(comboAno, c);

        // Item para selecionar
        row++;
        c.gridx = 0; c.gridy = row;
        painel.add(new JLabel("Selecionar Item:"), c);

        comboItem = new JComboBox<>();
        c.gridx = 1;
        painel.add(comboItem, c);

        // Lista visual dos itens selecionados
        row++;
        c.gridx = 0; c.gridy = row;
        painel.add(new JLabel("Itens Selecionados:"), c);

        listaItensSelecionados = new JList<>(itensSelecionadosModel);
        listaItensSelecionados.setVisibleRowCount(5);
        JScrollPane scroll = new JScrollPane(listaItensSelecionados);

        c.gridx = 1;
        painel.add(scroll, c);

        // Botões de ação
        row++;
        JPanel botoes = new JPanel(new GridLayout(1, 4, 8, 8));
        JButton btnCriar = new JButton("Criar Fatura");
        JButton btnAddItem = new JButton("Adicionar Item");
        JButton btnBuscar = new JButton("Buscar Fatura");
        JButton btnListar = new JButton("Listar Faturas");

        botoes.add(btnCriar);
        botoes.add(btnAddItem);
        botoes.add(btnBuscar);
        botoes.add(btnListar);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        painel.add(botoes, c);
        c.gridwidth = 1;

        add(painel, BorderLayout.NORTH);

        txtSaida = new JTextArea();
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);

        // Ações
        btnCriar.addActionListener(e -> criarFatura());
        btnAddItem.addActionListener(e -> adicionarItemAction());
        btnBuscar.addActionListener(e -> buscarFaturaAction());
        btnListar.addActionListener(e -> listarFaturasNaSaida());
    }

    private void carregarClientes() {
        comboCliente.removeAllItems();
        List<Cliente> clientes = clienteController.listar();
        for (Cliente c : clientes) {
            comboCliente.addItem(c.getId() + " - " + c.getNome());
        }
    }

    private void carregarItems() {
        comboItem.removeAllItems();
        List<Item> itens = itemController.listar();
        for (Item it : itens) {
            comboItem.addItem(it.getId() + " - " + it.getNome());
        }
    }

    private void adicionarItemAction() {
        try {
            if (comboItem.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um item.");
                return;
            }

            String itemStr = comboItem.getSelectedItem().toString();
            int idItem = Integer.parseInt(itemStr.split(" - ")[0]);
            Item item = itemController.buscar(idItem);

            itensSelecionados.add(item);
            itensSelecionadosModel.addElement(
                    item.getId() + " - " + item.getNome() + " (Qtd: " + item.getQuantidade() + ")"
            );

            JOptionPane.showMessageDialog(this, "Item adicionado!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + ex.getMessage());
        }
    }

    private void criarFatura() {
        try {
            int numero = Integer.parseInt(txtNumero.getText());

            String cliStr = comboCliente.getSelectedItem().toString();
            int idCliente = Integer.parseInt(cliStr.split(" - ")[0]);
            Cliente cliente = clienteController.buscar(idCliente);

            String mes = comboMes.getSelectedItem().toString();
            int ano = Integer.parseInt(comboAno.getSelectedItem().toString());

            faturaController.criarFatura(
                    numero,
                    cliente,
                    ano,
                    mes,
                    new ArrayList<>(itensSelecionados)
            );

            JOptionPane.showMessageDialog(this, "Fatura criada!");

            itensSelecionados.clear();
            itensSelecionadosModel.clear();

            listarFaturasNaSaida();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void buscarFaturaAction() {
        try {
            int numero = Integer.parseInt(txtNumero.getText());
            Fatura f = faturaController.buscar(numero);

            if (f == null) {
                txtSaida.setText("Fatura não encontrada.");
                return;
            }

            mostrarFatura(f);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar fatura.");
        }
    }

    private void listarFaturasNaSaida() {
        List<Fatura> faturas = faturaController.listar();
        StringBuilder sb = new StringBuilder();

        for (Fatura f : faturas) {
            sb.append("Fatura Nº ").append(f.getNumero())
              .append(" | Cliente: ").append(f.getCliente() != null ? f.getCliente().getId() + " - " + f.getCliente().getNome() : "N/A")
              .append(" | Mês/Ano: ").append(f.getMes()).append("/").append(f.getAno())
              .append(" | Total: ").append(f.calcularTotal())
              .append("\n");
        }

        txtSaida.setText(sb.toString());
    }

    private void mostrarFatura(Fatura f) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DETALHES DA FATURA ===\n\n");

        sb.append("Número: ").append(f.getNumero()).append("\n");
        sb.append("Cliente: ").append(f.getCliente().getId())
          .append(" - ").append(f.getCliente().getNome()).append("\n");
        sb.append("Referência: ").append(f.getMes()).append("/").append(f.getAno()).append("\n");
        sb.append("Total da Fatura: ").append(f.calcularTotal()).append("\n\n");

        sb.append("Itens da Fatura:\n");

        for (var calc : f.getItens()) {
            Item it = (Item) calc;

            sb.append("ID: ").append(it.getId())
              .append(" | Nome: ").append(it.getNome())
              .append(" | Quantidade: ").append(it.getQuantidade())
              .append(" | Valor Unit.: ").append(it.getPreco())
              .append(" | Total Item: ").append(it.calcularTotal())
              .append("\n");
        }

        txtSaida.setText(sb.toString());
    }

}
