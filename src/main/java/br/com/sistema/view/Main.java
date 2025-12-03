import br.com.sistema.controller.FaturaController;
import br.com.sistema.dao.FaturaDAOArrayList;
import br.com.sistema.dao.ItemDAOArrayList;
import br.com.sistema.model.Item;
import br.com.sistema.model.Fatura;
import br.com.sistema.service.FaturaService;
import br.com.sistema.service.ItemService;

public class Main {
    public static void main(String[] args) {

        // Instanciar os DAOs
        ItemDAOArrayList itemDAO = new ItemDAOArrayList();
        FaturaDAOArrayList faturaDAO = new FaturaDAOArrayList();

        // Instanciar os Services
        ItemService itemService = new ItemService(itemDAO);
        FaturaService faturaService = new FaturaService(faturaDAO);

        // Instanciar o Controller
        FaturaController faturaController = new FaturaController(faturaService);

        try {
            // Criando itens
            Item item1 = new Item("Mouse Gamer", 150.0, 2);
            Item item2 = new Item("Teclado Mecânico", 300.0, 1);
            Item item3 = new Item("Headset", 250.0, 1);

            itemService.criar(item1);
            itemService.criar(item2);
            itemService.criar(item3);

            System.out.println("Itens cadastrados:");
            itemService.listar().forEach(System.out::println);
            System.out.println("-----------------------------------");

            // Criar Fatura nº 1001
            faturaController.criarFatura(1001);

            // Adicionar itens à fatura
            faturaController.adicionarItemNaFatura(1001, item1); // Mouse (2x)
            faturaController.adicionarItemNaFatura(1001, item2); // Teclado
            faturaController.adicionarItemNaFatura(1001, item3); // Headset

            // Exibir total da fatura
            double total = faturaController.calcularTotal(1001);
            System.out.println("Total Fatura 1001: R$ " + total);
            System.out.println("-----------------------------------");

            // Criar outra fatura 1002
            faturaController.criarFatura(1002);
            faturaController.adicionarItemNaFatura(1002, item3); // Headset

            // Exibir todas as faturas
            System.out.println("Lista de faturas:");
            for (Fatura f : faturaController.listarFaturas()) {
                System.out.println("Fatura Nº " + f.getNumero() + 
                                   " | Total: R$ " + f.calcularTotal());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
