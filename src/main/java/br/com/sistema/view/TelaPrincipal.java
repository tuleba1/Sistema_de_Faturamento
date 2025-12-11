package br.com.sistema.view;

import br.com.sistema.controller.ClienteController;
import br.com.sistema.controller.ItemController;
import br.com.sistema.controller.FaturaController;


import br.com.sistema.dao.ClienteDAO;
import br.com.sistema.dao.ClienteDAOArquivo;
import br.com.sistema.dao.FaturaDAO;
import br.com.sistema.dao.FaturaDAOArquivo;
import br.com.sistema.dao.ItemDAO;
import br.com.sistema.dao.ItemDAOArquivo;
import br.com.sistema.dao.UsuarioDAOArquivo;

import br.com.sistema.service.ClienteService;
import br.com.sistema.service.ItemService;
import br.com.sistema.service.FaturaService;


import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private ClienteController clienteController;
    private ItemController itemController;
    private FaturaController faturaController;

    public TelaPrincipal() {

        ClienteDAO clienteDAO = new ClienteDAOArquivo("clientes.txt");
        ItemDAO itemDAO = new ItemDAOArquivo("itens.txt");
        FaturaDAO faturaDAO = new FaturaDAOArquivo("faturas.txt");


        ClienteService clienteService = new ClienteService(clienteDAO);
        ItemService itemService = new ItemService(itemDAO);
        FaturaService faturaService = new FaturaService(faturaDAO);


        clienteController = new ClienteController(clienteService);
        itemController = new ItemController(itemService);


        faturaController = new FaturaController(
                faturaService,
                clienteService,
                itemService
        );


        setTitle("Sistema de Faturamento");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnCliente = new JButton("Clientes");
        btnCliente.setBounds(120, 20, 150, 30);

        JButton btnItem = new JButton("Itens");
        btnItem.setBounds(120, 70, 150, 30);

        JButton btnFatura = new JButton("Faturas");
        btnFatura.setBounds(120, 120, 150, 30);

        btnCliente.addActionListener(e -> new TelaCliente(clienteController, faturaController).setVisible(true));
        btnItem.addActionListener(e -> new TelaItem(itemController, clienteController, faturaController).setVisible(true));

        btnFatura.addActionListener(e -> new TelaFatura(faturaController, clienteController, itemController).setVisible(true));

        add(btnCliente);
        add(btnItem);
        add(btnFatura);
    }
    public static void main(String[] args) {
        new TelaPrincipal().setVisible(true);
    }
}