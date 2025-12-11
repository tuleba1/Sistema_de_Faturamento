package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.controller.FaturaController;
import br.com.sistema.controller.ItemController;
import br.com.sistema.model.Cliente;
import br.com.sistema.model.Fatura;
import br.com.sistema.model.Item;
import br.com.sistema.model.ItemFatura;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaFatura extends javax.swing.JFrame {

    private FaturaController faturaController;
    private ClienteController clienteController;
    private ItemController itemController;

    private Fatura faturaAtual;

    public TelaFatura(ClienteController cc, ItemController ic, FaturaController fc) {
        initComponents();
        this.clienteController = cc;
        this.itemController = ic;
        this.faturaController = fc;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JTextField();
        txtMes = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        btnCriar = new javax.swing.JButton();
        lblCliente = new javax.swing.JLabel();
        lblPeriodo = new javax.swing.JLabel();

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();

        jLabel2 = new javax.swing.JLabel();
        txtCodigoItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();

        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerar Fatura");

        jLabel1.setText("CPF Cliente:");

        jLabel2.setText("Código do Item:");
        jLabel3.setText("Quantidade:");

        btnCriar.setText("Criar Fatura");
        btnCriar.addActionListener(evt -> criarFatura());

        btnAdicionar.setText("Adicionar Item");
        btnAdicionar.addActionListener(evt -> adicionarItem());

        tabelaItens.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Item", "Qtd", "Preço", "Subtotal"
                }
        ));
        jScrollPane1.setViewportView(tabelaItens);

        lblCliente.setText("Cliente: -");
        lblPeriodo.setText("Período: - ");
        lblTotal.setText("Total: R$ 0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCliente)
                    .addComponent(lblPeriodo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10)
                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(btnCriar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(10)
                        .addComponent(txtCodigoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(jLabel3)
                        .addGap(10)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(btnAdicionar))
                    .addComponent(lblTotal))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup()
                    .addComponent(jLabel1)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCriar))
                .addGap(10)
                .addComponent(lblCliente)
                .addComponent(lblPeriodo)
                .addGap(10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addGroup(layout.createParallelGroup()
                    .addComponent(jLabel2)
                    .addComponent(txtCodigoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar))
                .addGap(20)
                .addComponent(lblTotal)
                .addGap(20))
        );

        pack();
        setLocationRelativeTo(null);
    }



    private void criarFatura() {
        try {
            String cpf = txtCPF.getText();
            String mes = txtMes.getText();
            String ano = txtAno.getText();

            faturaAtual = faturaController.criarFatura(cpf, mes, ano);

            lblCliente.setText("Cliente: " + faturaAtual.getCliente().getNome());
            lblPeriodo.setText("Período: " + mes + "/" + ano);
            atualizarTabela();

            JOptionPane.showMessageDialog(this, "Fatura criada com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void adicionarItem() {
        try {
            int codigo = Integer.parseInt(txtCodigoItem.getText());
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            faturaController.adicionarItem(faturaAtual, codigo, quantidade);

            atualizarTabela();
            lblTotal.setText("Total: R$ " + faturaAtual.calcularTotal());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void atualizarTabela() {
        DefaultTableModel model = (DefaultTableModel) tabelaItens.getModel();
        model.setRowCount(0);

        if (faturaAtual == null) return;

        for (ItemFatura it : faturaAtual.getItens()) {
            model.addRow(new Object[]{
                it.getItem().getNome(),
                it.getQuantidade(),
                it.getItem().getPreco(),
                it.calcularSubtotal()
            });
        }
    }


    private javax.swing.JButton btnCriar;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblTotal;

    private javax.swing.JTable tabelaItens;

    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtMes;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtCodigoItem;
    private javax.swing.JTextField txtQuantidade;
}
