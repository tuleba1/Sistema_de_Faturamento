package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.controller.FaturaController;
import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaCliente extends JFrame {

    private final ClienteController clienteController;
    private final FaturaController faturaController;


    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JTextField txtEstado;
    private JTextField txtPais;
    private JTextField txtNascimento;

    private JTextField txtIdBuscar;


    private JTextArea txtSaida;

    public TelaCliente(ClienteController clienteController, FaturaController faturaController) {
        this.clienteController = clienteController;
        this.faturaController = faturaController;

        setTitle("Cadastro de Clientes");
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(10, 2, 10, 10));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        painel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        painel.add(txtEndereco);

        painel.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        painel.add(txtEstado);

        painel.add(new JLabel("País:"));
        txtPais = new JTextField();
        painel.add(txtPais);

        painel.add(new JLabel("Nascimento (AAAA-MM-DD):"));
        txtNascimento = new JTextField();
        painel.add(txtNascimento);

        painel.add(new JLabel("ID para buscar/alterar/excluir:"));
        txtIdBuscar = new JTextField();
        painel.add(txtIdBuscar);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnListar = new JButton("Listar Clientes");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnFaturas = new JButton("Ver Faturas do Cliente");

        painel.add(btnCadastrar);
        painel.add(btnListar);
        painel.add(btnBuscar);
        painel.add(btnAtualizar);
        painel.add(btnExcluir);
        painel.add(btnFaturas);

        add(painel, BorderLayout.NORTH);

        txtSaida = new JTextArea();
        txtSaida.setEditable(false);
        add(new JScrollPane(txtSaida), BorderLayout.CENTER);



        btnCadastrar.addActionListener(e -> cadastrarCliente());
        btnListar.addActionListener(e -> listarClientes());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnFaturas.addActionListener(e -> listarFaturasDoCliente());
    }

   

    private void cadastrarCliente() {
        try {
            Cliente c = new Cliente(
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtEndereco.getText(),
                    txtEstado.getText(),
                    txtPais.getText(),
                    LocalDate.parse(txtNascimento.getText())
            );

            clienteController.criar(c);
            
            int mes = Integer.parseInt(JOptionPane.showInputDialog("Mês da primeira fatura (1 a 12):"));
            int ano = Integer.parseInt(JOptionPane.showInputDialog("Ano da primeira fatura:"));

            int numeroFatura = (int) (Math.random() * 100000);

            faturaController.criarFatura(numeroFatura, c, mes, ano);


            JOptionPane.showMessageDialog(this, "Cliente cadastrado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        List<Cliente> lista = clienteController.listar();

        txtSaida.setText("");
        for (Cliente c : lista) {
            txtSaida.append("ID: " + c.getId() +
                    " | Nome: " + c.getNome() + "\n");
        }
    }

    private void buscarCliente() {
        try {
            int id = Integer.parseInt(txtIdBuscar.getText());
            Cliente c = clienteController.buscar(id);

            if (c == null) {
                txtSaida.setText("Cliente não encontrado!");
                return;
            }

            txtSaida.setText("Cliente encontrado:\n");
            txtSaida.append("Nome: " + c.getNome() + "\n");
            txtSaida.append("Email: " + c.getEmail() + "\n");
            txtSaida.append("Endereço: " + c.getEndereco() + "\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void atualizarCliente() {
        try {
            int id = Integer.parseInt(txtIdBuscar.getText());

            Cliente c = clienteController.buscar(id);

            if (c == null) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
                return;
            }

            c.setNome(txtNome.getText());
            c.setEmail(txtEmail.getText());
            c.setEndereco(txtEndereco.getText());
            c.setEstado(txtEstado.getText());
            c.setPaisNascimento(txtPais.getText());
            c.setDataNascimento(LocalDate.parse(txtNascimento.getText()));

            clienteController.atualizar(c);

            JOptionPane.showMessageDialog(this, "Cliente atualizado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        try {
            int id = Integer.parseInt(txtIdBuscar.getText());
            clienteController.deletar(id);
            JOptionPane.showMessageDialog(this, "Cliente excluído!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void listarFaturasDoCliente() {
        try {
            int id = Integer.parseInt(txtIdBuscar.getText());
            Cliente cliente = clienteController.buscar(id);

            if (cliente == null) {
                txtSaida.setText("Cliente não encontrado!");
                return;
            }

            List<Fatura> faturas = faturaController.listarFaturasDoCliente(cliente);

            if (faturas.isEmpty()) {
                txtSaida.setText("Nenhuma fatura encontrada para este cliente.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Faturas do cliente: ").append(cliente.getNome()).append("\n\n");

            double totalGasto = 0;

            for (Fatura f : faturas) {
                sb.append("Fatura Nº ").append(f.getNumero())
                        .append(" | Total: R$ ").append(f.calcularTotal())
                        .append("\n");

                totalGasto += f.calcularTotal();
            }

            sb.append("\nTotal Gasto pelo Cliente: R$ ").append(totalGasto);

            txtSaida.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
