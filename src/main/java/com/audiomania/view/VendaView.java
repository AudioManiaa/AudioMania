package com.audiomania.view;

import com.audiomania.controller.VendaController;
import com.audiomania.entities.ClienteEntity;
import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.entities.ProdutoEntity;
import com.audiomania.entities.VendaEntity;
import com.audiomania.service.ClienteService;
import com.audiomania.service.ProdutoService;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class VendaView {
    private final Scanner scanner;
    private final VendaController controller;
    private final FuncionarioEntity funcionarioLogado;

    public VendaView(FuncionarioEntity funcionarioLogado) {
        this.scanner = new Scanner(System.in);
        this.controller = new VendaController();
        this.funcionarioLogado = funcionarioLogado;
    }

    public void iniciarGerenciamento() {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n===== GERENCIAMENTO DE VENDAS =====\n");
            System.out.println("1. Listar Vendas");
            System.out.println("2. Registrar Nova Venda");
            System.out.println("3. Atualizar Venda");
            System.out.println("4. Cancelar Venda");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("\nEscolha uma opção: ");

            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1:
                    listarVendas();
                    break;
                case 2:
                    registrarVenda();
                    break;
                case 3:
                    atualizarVenda();
                    break;
                case 4:
                    cancelarVenda();
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            if (!sair) {
                System.out.print("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void listarVendas() {
        List<VendaEntity> vendas = controller.listarVendas();

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        System.out.println("\n=== LISTA DE VENDAS ===\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (VendaEntity venda : vendas) {
            System.out.println("ID: " + venda.getId());
            System.out.println("Data: " + venda.getData().format(formatter));
            System.out.println("Cliente: " + venda.getCliente().getNome() + " (ID: " + venda.getCliente().getId() + ")");
            System.out.println("Funcionário: " + venda.getFuncionario().getNome() + " (ID: " + venda.getFuncionario().getId() + ")");
            System.out.println("Produto: " + venda.getProduto().getNome() + " (ID: " + venda.getProduto().getId() + ")");
            System.out.println("Quantidade: " + venda.getQuantidade());
            System.out.println("Valor Total: R$ " + venda.getValorTotal());
            System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());
            System.out.println("Desconto: R$ " + (venda.getDesconto() != null ? venda.getDesconto() : "0.00"));
            System.out.println("------------------------------");
        }
    }

    private void registrarVenda() {
        System.out.println("\n=== REGISTRAR NOVA VENDA ===\n");

        // Listar clientes para seleção
        List<ClienteEntity> clientes = ClienteService.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados. Cadastre um cliente primeiro.");
            return;
        }

        System.out.println("Clientes disponíveis:");
        for (ClienteEntity cliente : clientes) {
            System.out.println("ID: " + cliente.getId() + " | Nome: " + cliente.getNome() + " | CPF: " + cliente.getCpf());
        }

        // Selecionar cliente
        System.out.print("\nID do cliente: ");
        int idCliente;
        try {
            idCliente = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        // Listar produtos para seleção
        List<ProdutoEntity> produtos = ProdutoService.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados. Cadastre um produto primeiro.");
            return;
        }

        System.out.println("\nProdutos disponíveis:");
        for (ProdutoEntity produto : produtos) {
            if (produto.getQuantidadeEstoque() > 0) {
                System.out.println("ID: " + produto.getId() + " | Nome: " + produto.getNome() +
                        " | Preço: R$ " + produto.getPreco() +
                        " | Estoque: " + produto.getQuantidadeEstoque());
            }
        }

        // Selecionar produto
        System.out.print("\nID do produto: ");
        int idProduto;
        try {
            idProduto = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        ProdutoEntity produto = ProdutoService.buscarPorId(idProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (produto.getQuantidadeEstoque() <= 0) {
            System.out.println("Produto sem estoque disponível.");
            return;
        }

        // Quantidade
        System.out.print("Quantidade: ");
        int quantidade;
        try {
            quantidade = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Quantidade inválida.");
            return;
        }

        if (quantidade <= 0) {
            System.out.println("Quantidade deve ser maior que zero.");
            return;
        }

        if (quantidade > produto.getQuantidadeEstoque()) {
            System.out.println("Estoque insuficiente. Disponível: " + produto.getQuantidadeEstoque());
            return;
        }

        // Forma de pagamento
        System.out.print("Forma de pagamento: ");
        String formaPagamento = scanner.nextLine();

        // Desconto
        System.out.print("Desconto (0 para nenhum): ");
        BigDecimal desconto;
        try {
            desconto = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor de desconto inválido.");
            return;
        }

        // Calcular valor total
        BigDecimal valorTotal = produto.getPreco().multiply(new BigDecimal(quantidade));
        if (desconto != null && desconto.compareTo(BigDecimal.ZERO) > 0) {
            valorTotal = valorTotal.subtract(desconto);
            if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                valorTotal = BigDecimal.ZERO;
            }
        }

        // Registrar venda
        boolean sucesso = controller.registrarVenda(
                idCliente,
                funcionarioLogado.getId(),
                idProduto,
                quantidade,
                valorTotal,
                formaPagamento,
                desconto);

        if (sucesso) {
            System.out.println("Venda registrada com sucesso!");
            System.out.println("Valor total: R$ " + valorTotal);
        } else {
            System.out.println("Erro ao registrar venda.");
        }
    }

    private void atualizarVenda() {
        System.out.println("\n=== ATUALIZAR VENDA ===\n");

        System.out.print("ID da venda a ser atualizada: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        VendaEntity venda = controller.buscarPorId(id);
        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\nDetalhes da venda:");
        System.out.println("ID: " + venda.getId());
        System.out.println("Data: " + venda.getData().format(formatter));
        System.out.println("Cliente: " + venda.getCliente().getNome());
        System.out.println("Funcionário: " + venda.getFuncionario().getNome());
        System.out.println("Produto: " + venda.getProduto().getNome());
        System.out.println("Quantidade: " + venda.getQuantidade());
        System.out.println("Valor Total: R$ " + venda.getValorTotal());
        System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());
        System.out.println("Desconto: R$ " + (venda.getDesconto() != null ? venda.getDesconto() : "0.00"));

        System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");

        System.out.print("Nova forma de pagamento [" + venda.getFormaPagamento() + "]: ");
        String formaPagamento = scanner.nextLine();
        if (formaPagamento.isBlank()) {
            formaPagamento = venda.getFormaPagamento();
        }

        System.out.print("Novo desconto [" + (venda.getDesconto() != null ? venda.getDesconto() : "0.00") + "]: ");
        String descontoStr = scanner.nextLine();
        BigDecimal desconto;
        if (descontoStr.isBlank()) {
            desconto = venda.getDesconto();
        } else {
            try {
                desconto = new BigDecimal(descontoStr);
            } catch (NumberFormatException e) {
                System.out.println("Valor de desconto inválido.");
                return;
            }
        }

        boolean sucesso = controller.atualizarVenda(id, formaPagamento, desconto);

        if (sucesso) {
            System.out.println("Venda atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar venda.");
        }
    }

    private void cancelarVenda() {
        System.out.println("\n=== CANCELAR VENDA ===\n");

        System.out.print("ID da venda a ser cancelada: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        VendaEntity venda = controller.buscarPorId(id);
        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\nDetalhes da venda a ser cancelada:");
        System.out.println("ID: " + venda.getId());
        System.out.println("Data: " + venda.getData().format(formatter));
        System.out.println("Cliente: " + venda.getCliente().getNome());
        System.out.println("Funcionário: " + venda.getFuncionario().getNome());
        System.out.println("Produto: " + venda.getProduto().getNome());
        System.out.println("Quantidade: " + venda.getQuantidade());
        System.out.println("Valor Total: R$ " + venda.getValorTotal());

        System.out.print("\nConfirma o cancelamento desta venda? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            boolean sucesso = controller.cancelarVenda(id);

            if (sucesso) {
                System.out.println("Venda cancelada com sucesso!");
                System.out.println("Os produtos foram devolvidos ao estoque.");
            } else {
                System.out.println("Erro ao cancelar venda.");
            }
        } else {
            System.out.println("Operação cancelada pelo usuário.");
        }
    }
}