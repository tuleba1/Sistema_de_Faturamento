package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.controller.ItemController;
import br.com.sistema.controller.FaturaController;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private ClienteController clienteController;
    private ItemController itemController;
    private FaturaController faturaController;

    public TelaPrincipal() {

        clienteController = new ClienteController();
        itemController = new ItemController();
        faturaController = new FaturaController(clienteController, itemController);

        clienteController.carregarDoArquivo();
        itemController.carregarDoArquivo();
        faturaController.carregarDoArquivo();

   
        setTitle("Sistema de Faturamento");
        setSize(400, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnCliente = new JButton("Clientes");
        btnCliente.setBounds(120, 20, 150, 30);

        JButton btnItem = new JButton("Itens");
        btnItem.setBounds(120, 70, 150, 30);

        JButton btnFatura = new JButton("Faturas");
        btnFatura.setBounds(120, 120, 150, 30);

        JButton btnListagens = new JButton("Listagens");
        btnListagens.setBounds(120, 170, 150, 30);


        btnCliente.addActionListener(e ->
                new TelaCliente(clienteController).setVisible(true)
        );

        btnItem.addActionListener(e ->
                new TelaItem(itemController).setVisible(true)
        );

        btnFatura.addActionListener(e ->
                new TelaFatura(clienteController, itemController, faturaController).setVisible(true)
        );

        btnListagens.addActionListener(e ->
                new TelaListagens().setVisible(true)
        );

    
        add(btnCliente);
        add(btnItem);
        add(btnFatura);
        add(btnListagens);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
