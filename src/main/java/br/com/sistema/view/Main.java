/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.view;

import br.com.sistema.dao.ClienteDAO;
import br.com.sistema.dao.ClienteDAOArrayList;
import br.com.sistema.model.Cliente;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        try {
            // Instanciando o DAO
            ClienteDAO clienteDAO = new ClienteDAOArrayList();

            // Criando 3 clientes
            Cliente c1 = new Cliente(
                    1,
                    "João Silva",
                    "joao@gmail.com",
                    "Rua A, 123",
                    "SP",
                    "Brasil",
                    LocalDate.of(1990, 5, 11)
            );

            Cliente c2 = new Cliente(
                    2,
                    "Maria Souza",
                    "maria@gmail.com",
                    "Av. Central, 500",
                    "RJ",
                    "Brasil",
                    LocalDate.of(1985, 10, 3)
            );

            Cliente c3 = new Cliente(
                    3,
                    "Carlos Lima",
                    "carlos@hotmail.com",
                    "Rua das Flores, 88",
                    "MG",
                    "Brasil",
                    LocalDate.of(2000, 2, 20)
            );

            // Validando
            c1.validar();
            c2.validar();
            c3.validar();

            // Salvando no DAO
            clienteDAO.salvar(c1);
            clienteDAO.salvar(c2);
            clienteDAO.salvar(c3);

            // Listando todos
            System.out.println("=== LISTA INICIAL ===");
            clienteDAO.listarTodos().forEach(System.out::println);

            // Buscando cliente por ID
            System.out.println("\n=== BUSCANDO ID 2 ===");
            Cliente buscado = clienteDAO.buscarPorId(2);
            System.out.println(buscado);

            // Atualizando cliente 3
            System.out.println("\n=== ATUALIZANDO CLIENTE 3 ===");
            c3.setNome("Carlos Eduardo Lima");
            clienteDAO.atualizar(c3);

            // Exibindo lista após atualização
            clienteDAO.listarTodos().forEach(System.out::println);

            // Deletando cliente 1
            System.out.println("\n=== DELETANDO CLIENTE 1 ===");
            clienteDAO.deletar(1);

            // Exibindo final
            System.out.println("\n=== LISTA FINAL ===");
            clienteDAO.listarTodos().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}