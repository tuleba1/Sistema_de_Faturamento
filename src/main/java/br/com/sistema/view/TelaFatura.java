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
import java.util.Vector;
import java.util.stream.IntStream;

public class TelaFatura extends JFrame {

    private final FaturaController faturaController;
    private final ClienteController clienteController;
    private final ItemController itemController;

    private JTextField txtNumero;
    private JComboBox<String> comboMes;
    private JComboBox<Integer> comboAno;
    private DefaultListModel<String> itensModel;
    private JComboBox<String> comboCliente; 
    private JComboBox<String> comboItem;    

    private JTextArea txtSaida;

    public TelaFatura(FaturaController faturaController,
                      ClienteController clienteController,
                      ItemController itemController) {
        this.faturaController = faturaController;
        this.clienteController = clienteController;
        this.itemController = itemController;

        setTitle("Gerenciamento de Faturas");
        setSize(700, 600);
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
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        c.gridx = 0; c.gridy = row; painel.add(new JLabel("Número da Fatura:"), c);
        txtNumero = new JTextField(); c.gridx = 1; painel.add(txtNumero, c);

        row++;
        c.gridx = 0; c.gridy = row; painel.add(new JLabel("Cliente:"), c);
        comboCliente = new JComboBox<>(); c.gridx = 1; painel.add(comboCliente, c);

        String[] meses = new DateFormatSymbols(new Locale("pt","BR")).getMonths();

       String[] meses12 = new String[12];
       System.arraycopy(meses, 0, meses12, 0, 12);

        comboMes = new JComboBox<>();
        String[] mes = {
            "Janeiro","Fevereiro","Março","Abril","Maio","Junho",
            "Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"
        };
        for (String m : meses) comboMes.addItem(m);
        painel.add(new JLabel("Mês:"), c);
        c.gridx = 1;
        painel.add(comboMes, c);

 
       int anoAtual = LocalDate.now().getYear();
       int anoInicial = 2000;
       Integer[] anos = IntStream.rangeClosed(anoInicial, anoAtual)
                                 .boxed()
                                 .toArray(Integer[]::new);

       comboAno = new JComboBox<>(anos);
       comboAno.setSelectedItem(anoAtual); // por padrão seleciona o ano atual
       row++;
       c.gridx = 0; c.gridy = row; painel.add(new JLabel("Ano:"), c);
       c.gridx = 1; painel.add(comboAno, c);


        row++;
        c.gridx = 0; c.gridy = row; painel.add(new JLabel("Item (selecionar e adicionar):"), c);
        comboItem = new JComboBox<>(); c.gridx = 1; painel.add(comboItem, c);

        row++;
        JPanel botoes = new JPanel(new GridLayout(1,4,8,8));
        JButton btnCriar = new JButton("Criar Fatura");
        JButton btnAddItem = new JButton("Adicionar Item");
        JButton btnBuscar = new JButton("Buscar Fatura");
        JButton btnListar = new JButton("Listar Faturas");

        botoes.add(btnCriar);
        botoes.add(btnAddItem);
        botoes.add(btnBuscar);
        botoes.add(btnListar);

        c.gridx = 0; c.gridy = ++row; c.gridwidth = 2; painel.add(botoes, c);
        c.gridwidth = 1;

        add(painel, BorderLayout.NORTH);

        txtSaida = new JTextArea();
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);

 
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


    private void criarFatura() {
        try {

            int numero = Integer.parseInt(txtNumero.getText());


            String cliStr = comboCliente.getSelectedItem().toString();
            int idCliente = Integer.parseInt(cliStr.split(" - ")[0]);
            Cliente cliente = clienteController.buscar(idCliente);


            String mes = comboMes.getSelectedItem().toString();
            int ano = Integer.parseInt(comboAno.getSelectedItem().toString());


            List<Item> itens = new ArrayList<>();

            if (comboItem.getSelectedItem() != null) {
                String itemStr = comboItem.getSelectedItem().toString();
                int idItem = Integer.parseInt(itemStr.split(" - ")[0]);
                Item item = itemController.buscar(idItem);
                itens.add(item);
            }
   
            
            faturaController.criarFatura(numero, cliente, ano, mes, itens);

            JOptionPane.showMessageDialog(this, "Fatura criada com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }


    private void adicionarItemAction() {
        try {
            int numero = Integer.parseInt(txtNumero.getText());
            if (comboItem.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um item.");
                return;
            }
            String itemStr = comboItem.getSelectedItem().toString();
            int idItem = Integer.parseInt(itemStr.split(" - ")[0]);
            Item item = itemController.buscar(idItem);
            if (item == null) { JOptionPane.showMessageDialog(this, "Item inválido."); return; }

            boolean ok = faturaController.adicionarItemNaFatura(numero, item);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Fatura não encontrada (verifique o número).");
            } else {
                JOptionPane.showMessageDialog(this, "Item adicionado à fatura!");
                buscarFaturaAction();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número inválido: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + ex.getMessage());
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
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número inválido.");
        }
    }

    private void listarFaturasNaSaida() {
        List<Fatura> faturas = faturaController.listar();
        StringBuilder sb = new StringBuilder();
        for (Fatura f : faturas) {
            sb.append("Fatura Nº ").append(f.getNumero())
              .append(" | Cliente: ").append(f.getCliente() != null ? f.getCliente().getNome() : "N/A")
              .append(" | Mês/Ano: ").append(f.getMes()).append("/").append(f.getAno())
              .append(" | Total: ").append(f.calcularTotal())
              .append("\n");
        }
        txtSaida.setText(sb.toString());
    }

    private void mostrarFatura(Fatura f) {
        StringBuilder sb = new StringBuilder();
        sb.append("Fatura Nº ").append(f.getNumero()).append("\n");
        sb.append("Cliente: ").append(f.getCliente() != null ? f.getCliente().getNome() : "N/A").append("\n");
        sb.append("Referencia: ").append(f.getMes()).append("/").append(f.getAno()).append("\n");
        sb.append("Total: ").append(f.calcularTotal()).append("\n\n");
        sb.append("Itens:\n");
        for (var c : f.getItens()) {
          
            Item it = (Item) c;
            sb.append("ID: ").append(it.getId())
              .append(" | ").append(it.getNome())
              .append(" | Qtd: ").append(it.getQuantidade())
              .append(" | Unit: ").append(it.getPreco())
              .append(" | Total: ").append(it.calcularTotal())
              .append("\n");
        }
        txtSaida.setText(sb.toString());
    }
}
