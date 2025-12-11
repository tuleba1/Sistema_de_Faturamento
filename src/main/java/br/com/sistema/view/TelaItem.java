package br.com.sistema.view;

import br.com.sistema.controller.ItemController;
import br.com.sistema.model.Item;

import javax.swing.*;
import java.awt.*;

public class TelaItem extends JFrame {

    private ItemController controller;

    private JTextField txtNome;
    private JTextField txtValor;

    public TelaItem(ItemController controller) {
        this.controller = controller;

        setTitle("Cadastro de Itens");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 5, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Nome do Item:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Valor (R$):"));
        txtValor = new JTextField();
        add(txtValor);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarItem());
        add(btnSalvar);

        setVisible(true);
    }

    private void salvarItem() {
        try {
            String nome = txtNome.getText();
            double valor = Double.parseDouble(txtValor.getText());

            controller.cadastrar(nome, valor);

            JOptionPane.showMessageDialog(this, "Item cadastrado com sucesso!");
            txtNome.setText("");
            txtValor.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
