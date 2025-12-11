package br.com.sistema.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class TelaListagens extends JFrame {

    private JTable tabelaClientes;
    private JTable tabelaItens;
    private JTable tabelaFaturas;

    private DefaultTableModel modeloClientes;
    private DefaultTableModel modeloItens;
    private DefaultTableModel modeloFaturas;

    public TelaListagens() {

        setTitle("Listagens do Sistema - Dados Persistentes (.TXT)");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane abas = new JTabbedPane();

        abas.add("Clientes", painelClientes());
        abas.add("Itens", painelItens());
        abas.add("Faturas", painelFaturas());

        add(abas, BorderLayout.CENTER);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private JPanel painelClientes() {
        JPanel painel = new JPanel(new BorderLayout());

        modeloClientes = new DefaultTableModel(
                new String[]{"ID", "Nome", "CPF", "Email", "Endereço", "Nascimento"}, 0);

        tabelaClientes = new JTable(modeloClientes);
        painel.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

        carregarTXTClientes();
        return painel;
    }

    private void carregarTXTClientes() {
        modeloClientes.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";"); 
                modeloClientes.addRow(dados);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível ler clientes.txt\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel painelItens() {
        JPanel painel = new JPanel(new BorderLayout());

        modeloItens = new DefaultTableModel(
                new String[]{"ID", "Nome", "Preço"}, 0);

        tabelaItens = new JTable(modeloItens);
        painel.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);

        carregarTXTItens();
        return painel;
    }

    private void carregarTXTItens() {
        modeloItens.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("itens.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";"); 
                modeloItens.addRow(dados);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível ler itens.txt\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JPanel painelFaturas() {
        JPanel painel = new JPanel(new BorderLayout());

        modeloFaturas = new DefaultTableModel(
                new String[]{"ID", "CPF Cliente", "Mês", "Ano", "Qtd Itens", "Total"}, 0);

        tabelaFaturas = new JTable(modeloFaturas);
        painel.add(new JScrollPane(tabelaFaturas), BorderLayout.CENTER);

        carregarTXTFaturas();
        return painel;
    }

    private void carregarTXTFaturas() {
        modeloFaturas.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("faturas.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";"); 
                modeloFaturas.addRow(dados);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível ler faturas.txt\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
