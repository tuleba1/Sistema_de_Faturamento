package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaCliente extends JFrame {

    private ClienteController controller;

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JTextField txtCpf;
    private JTextField txtDataNascimento; // formato yyyy-MM-dd

    public TelaCliente(ClienteController controller) {
        this.controller = controller;

        setTitle("Cadastro de Clientes");
        setSize(400, 330);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 5, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ---- Campos ----
        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        add(txtEndereco);

        add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        add(txtCpf);

        add(new JLabel("Data Nascimento (yyyy-MM-dd):"));
        txtDataNascimento = new JTextField();
        add(txtDataNascimento);

   
        JButton btnSalvar = new JButton("Salvar Cliente");
        btnSalvar.addActionListener(e -> salvarCliente());
        add(btnSalvar);

    
        add(new JLabel(""));

        setVisible(true);
    }

    private void salvarCliente() {
        try {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String endereco = txtEndereco.getText();
            String cpf = txtCpf.getText();

            LocalDate nascimento = LocalDate.parse(txtDataNascimento.getText());

            controller.cadastrar(nome, email, endereco, cpf, nascimento);

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtCpf.setText("");
        txtDataNascimento.setText("");
    }
}
