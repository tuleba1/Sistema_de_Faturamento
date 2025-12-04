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

public class TelaFatura extends JFrame {

    private final FaturaController faturaController;
    private final ClienteController clienteController;
    private final ItemController itemController;

    private JTextField txtNumeroFatura;
    private JTextField txtIdCliente;
    private JTextField txtIdItem;

    private JTextArea txtSaida;

    public TelaFatura(FaturaController faturaController,
                      ClienteController clienteController,
                      ItemController itemController) {

        this.faturaController = faturaController;
        this.clienteController = clienteController;
        this.itemController = itemController;

        setTitle("Gerenciamento de Faturas");
        setSize(650, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(7, 2, 10, 10));

        painel.add(new JLabel("Número da Fatura:"));
        txtNumeroFatura = new JTextField();
        painel.add(txtNumeroFatura);

        painel.add(new JLabel("ID do Cliente:"));
        txtIdCliente = new JTextField();
        painel.add(txtIdCliente);

        painel.add(new JLabel("ID do Item:"));
        txtIdItem = new JTextField();
        painel.add(txtIdItem);

        JButton btnCriar = new JButton("Criar Fatura");
        JButton btnAddItem = new JButton("Adicionar Item");
        JButton btnBuscar = new JButton("Buscar Fatura");
        JButton btnListar = new JButton("Listar Faturas");
        JButton btnExcluir = new JButton("Excluir Fatura");

        painel.add(btnCriar);
        painel.add(btnAddItem);
        painel.add(btnBuscar);
        painel.add(btnListar);
        painel.add(btnExcluir);

        add(painel, BorderLayout.NORTH);

        txtSaida = new JTextArea();
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);



        btnAddItem.addActionListener(e -> adicionarItem());
        btnBuscar.addActionListener(e -> buscarFatura());
        btnListar.addActionListener(e -> listarFaturas());
        btnExcluir.addActionListener(e -> excluirFatura());
    }



    private void adicionarItem() {
        try {
            int numero = Integer.parseInt(txtNumeroFatura.getText());
            int idItem = Integer.parseInt(txtIdItem.getText());

            Item item = itemController.buscar(idItem);

            if (item == null) {
                JOptionPane.showMessageDialog(this, "Item não encontrado!");
                return;
            }

            boolean ok = faturaController.adicionarItemNaFatura(numero, item);

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Fatura não encontrada!");
                return;
            }

            JOptionPane.showMessageDialog(this, "Item adicionado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void buscarFatura() {
        try {
            int numero = Integer.parseInt(txtNumeroFatura.getText());

            Fatura f = faturaController.buscar(numero);

            if (f == null) {
                txtSaida.setText("Fatura não encontrada.");
                return;
            }

            mostrarFatura(f);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void listarFaturas() {
        List<Fatura> faturas = faturaController.listar();

        StringBuilder sb = new StringBuilder();

        for (Fatura f : faturas) {
            sb.append("Fatura Nº ").append(f.getNumero())
              .append(" | Cliente: ").append(f.getCliente() != null ? f.getCliente().getNome() : "N/A")
              .append(" | Total: ").append(f.calcularTotal())
              .append("\n");
        }

        txtSaida.setText(sb.toString());
    }

    private void excluirFatura() {
        try {
            int numero = Integer.parseInt(txtNumeroFatura.getText());

            boolean ok = faturaController.removerFatura(numero);

            if (ok)
                JOptionPane.showMessageDialog(this, "Fatura removida!");
            else
                JOptionPane.showMessageDialog(this, "Fatura não encontrada!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void mostrarFatura(Fatura f) {
        StringBuilder sb = new StringBuilder();

        sb.append("Fatura Nº ").append(f.getNumero()).append("\n")
          .append("Cliente: ").append(f.getCliente() != null ? f.getCliente().getNome() : "N/A").append("\n")
          .append("Total: ").append(f.calcularTotal()).append("\n\n")
          .append("Itens:\n");

        for (var calc : f.getItens()) {
            Item item = (Item) calc;
            sb.append("ID: ").append(item.getId())
              .append(" | Nome: ").append(item.getNome())
              .append(" | Qtd: ").append(item.getQuantidade())
              .append(" | Unit.: ").append(item.getPreco())
              .append(" | Total: ").append(item.calcularTotal())
              .append("\n");
        }

        txtSaida.setText(sb.toString());
    }

    private void carregarFaturasCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
