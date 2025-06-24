package com.audiomania.controller;

import com.audiomania.model.entities.ClienteEntity;
import com.audiomania.model.entities.ProdutoEntity;
import com.audiomania.model.entities.VendaEntity;
import com.audiomania.model.service.ClienteService;
import com.audiomania.model.service.ProdutoService;
import com.audiomania.model.service.VendaService;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VendaController {

    /**
     * Busca uma venda pelo ID
     * @param id ID da venda
     * @return O objeto VendaEntity ou null se não encontrado
     */
    public VendaEntity buscarPorId(Integer id) {
        try {
            return VendaService.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar venda por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todas as vendas
     * @return Lista de vendas
     */
    public List<VendaEntity> listarVendas() {
        try {
            return VendaService.listarTodas();
        } catch (Exception e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca vendas por termo (cliente ou produto)
     * @param termo Termo de busca
     * @return Lista de vendas filtradas
     */
    public List<VendaEntity> buscarVendas(String termo) {
        try {
            List<VendaEntity> todasVendas = VendaService.listarTodas();

            if (termo == null || termo.trim().isEmpty()) {
                return todasVendas;
            }

            return todasVendas.stream()
                    .filter(venda -> filtrarPorTermo(venda, termo))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erro ao buscar vendas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Formata venda para exibição na tabela
     * @param venda Venda a ser formatada
     * @return Array de objetos para a tabela
     */
    public Object[] formatarVendaParaTabela(VendaEntity venda) {
        return new Object[]{
                venda.getId(),
                venda.getData() != null ? venda.getData().toString() : "",
                venda.getCliente() != null ? venda.getCliente().getNome() : "",
                venda.getProduto() != null ? venda.getProduto().getNome() : "",
                venda.getQuantidade(),
                String.format("R$ %.2f", venda.getValorTotal()),
                venda.getFormaPagamento()
        };
    }

    /**
     * Carrega dados para nova venda (combos)
     * @param clienteCombo Combo de clientes
     * @param produtoCombo Combo de produtos
     * @param pagamentoCombo Combo de formas de pagamento
     */
    public void carregarDadosNovaVenda(JComboBox<String> clienteCombo, JComboBox<String> produtoCombo,
                                       JComboBox<String> pagamentoCombo) {
        try {
            // Carregar clientes
            List<ClienteEntity> clientes = ClienteService.listarTodos();
            clienteCombo.removeAllItems();
            clienteCombo.addItem("Selecione um cliente...");
            for (ClienteEntity cliente : clientes) {
                clienteCombo.addItem(formatarClienteParaCombo(cliente));
            }

            // Carregar produtos
            List<ProdutoEntity> produtos = ProdutoService.listarTodos();
            produtoCombo.removeAllItems();
            produtoCombo.addItem("Selecione um produto...");
            for (ProdutoEntity produto : produtos) {
                if (produto.getQuantidadeEstoque() > 0) { // Só produtos com estoque
                    produtoCombo.addItem(formatarProdutoParaCombo(produto));
                }
            }

            // Carregar formas de pagamento
            pagamentoCombo.removeAllItems();
            pagamentoCombo.addItem("Selecione pagamento...");
            String[] formasPagamento = {"PIX", "Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Transferência"};
            for (String forma : formasPagamento) {
                pagamentoCombo.addItem(forma);
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar dados para nova venda: " + e.getMessage());
        }
    }

    /**
     * Calcula o valor total da venda
     * @param produtoSelecionado Produto selecionado
     * @param quantidadeStr Quantidade como string
     * @param descontoStr Desconto como string
     * @return Valor total formatado
     */
    public String calcularValorTotal(Object produtoSelecionado, String quantidadeStr, String descontoStr) {
        try {
            if (produtoSelecionado == null || produtoSelecionado.toString().startsWith("Selecione")) {
                return "R$ 0,00";
            }

            // Extrair ID do produto
            Integer produtoId = extrairIdDoProduto(produtoSelecionado.toString());
            if (produtoId == null) {
                return "R$ 0,00";
            }

            ProdutoEntity produto = ProdutoService.buscarPorId(produtoId);
            if (produto == null) {
                return "R$ 0,00";
            }

            // Validar quantidade
            Integer quantidade;
            try {
                quantidade = Integer.parseInt(quantidadeStr.trim());
                if (quantidade <= 0) {
                    return "R$ 0,00";
                }
            } catch (NumberFormatException e) {
                return "R$ 0,00";
            }

            // Validar desconto
            BigDecimal desconto;
            try {
                desconto = new BigDecimal(descontoStr.trim().replace(",", "."));
                if (desconto.compareTo(BigDecimal.ZERO) < 0) {
                    desconto = BigDecimal.ZERO;
                }
            } catch (NumberFormatException e) {
                desconto = BigDecimal.ZERO;
            }

            // Calcular valor total
            BigDecimal valorProduto = produto.getPreco().multiply(new BigDecimal(quantidade));
            BigDecimal valorFinal = valorProduto.subtract(desconto);

            if (valorFinal.compareTo(BigDecimal.ZERO) < 0) {
                valorFinal = BigDecimal.ZERO;
            }

            return String.format("R$ %.2f", valorFinal);

        } catch (Exception e) {
            System.err.println("Erro ao calcular valor total: " + e.getMessage());
            return "R$ 0,00";
        }
    }

    /**
     * Registra uma nova venda com validações e cálculo automático
     * @param clienteSelecionado Cliente selecionado
     * @param produtoSelecionado Produto selecionado
     * @param quantidadeStr Quantidade como string
     * @param descontoStr Desconto como string
     * @param pagamentoSelecionado Forma de pagamento selecionada
     * @param valorTotalStr Valor total como string (será recalculado)
     * @return Status da operação
     */
    public String registrarVenda(Object clienteSelecionado, Object produtoSelecionado, String quantidadeStr,
                                 String descontoStr, Object pagamentoSelecionado, String valorTotalStr) {

        // Validar cliente
        if (clienteSelecionado == null || clienteSelecionado.toString().startsWith("Selecione")) {
            return "CLIENTE_NAO_SELECIONADO";
        }

        // Validar produto
        if (produtoSelecionado == null || produtoSelecionado.toString().startsWith("Selecione")) {
            return "PRODUTO_NAO_SELECIONADO";
        }

        // Validar forma de pagamento
        if (pagamentoSelecionado == null || pagamentoSelecionado.toString().startsWith("Selecione")) {
            return "PAGAMENTO_NAO_SELECIONADO";
        }

        // Validar e converter quantidade
        Integer quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr.trim());
            if (quantidade <= 0) {
                return "QUANTIDADE_INVALIDA";
            }
        } catch (NumberFormatException e) {
            return "QUANTIDADE_INVALIDA";
        }

        // Validar e converter desconto
        BigDecimal desconto;
        try {
            desconto = new BigDecimal(descontoStr.trim().replace(",", "."));
            if (desconto.compareTo(BigDecimal.ZERO) < 0) {
                return "DESCONTO_INVALIDO";
            }
        } catch (NumberFormatException e) {
            return "DESCONTO_INVALIDO";
        }

        // Extrair IDs
        Integer clienteId = extrairIdDoCliente(clienteSelecionado.toString());
        Integer produtoId = extrairIdDoProduto(produtoSelecionado.toString());

        if (clienteId == null || produtoId == null) {
            return "ERRO_DADOS_INVALIDOS";
        }

        // Verificar estoque e calcular valor correto
        try {
            ProdutoEntity produto = ProdutoService.buscarPorId(produtoId);
            if (produto == null || produto.getQuantidadeEstoque() < quantidade) {
                return "ESTOQUE_INSUFICIENTE";
            }

            // RECALCULAR valor total corretamente (ignora o valorTotalStr da view)
            BigDecimal valorSemDesconto = produto.getPreco().multiply(new BigDecimal(quantidade));
            BigDecimal valorTotal = valorSemDesconto.subtract(desconto);

            if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                valorTotal = BigDecimal.ZERO;
            }

            // Registrar venda com valor recalculado
            boolean sucesso = VendaService.registrarVenda(
                    clienteId, 1, produtoId, quantidade, valorTotal,
                    pagamentoSelecionado.toString(), desconto
            );

            return sucesso ? "SUCESSO" : "ERRO_BANCO";

        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Carrega dados para atualização de venda
     * @param venda Venda a ser atualizada
     * @param vendaInfoLabel Label com informações da venda
     * @param pagamentoCombo Combo de formas de pagamento
     * @param descontoField Campo de desconto
     * @param valorTotalLabel Label do valor total
     */
    public void carregarDadosAtualizacao(VendaEntity venda, JLabel vendaInfoLabel, JComboBox<String> pagamentoCombo,
                                         JTextField descontoField, JLabel valorTotalLabel) {
        try {
            // Informações da venda
            String info = String.format(
                    "<html>Venda ID: %d<br>Cliente: %s<br>Produto: %s<br>Quantidade: %d</html>",
                    venda.getId(),
                    venda.getCliente() != null ? venda.getCliente().getNome() : "N/A",
                    venda.getProduto() != null ? venda.getProduto().getNome() : "N/A",
                    venda.getQuantidade()
            );
            vendaInfoLabel.setText(info);

            // Carregar formas de pagamento
            pagamentoCombo.removeAllItems();
            String[] formasPagamento = {"PIX", "Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Transferência"};
            for (String forma : formasPagamento) {
                pagamentoCombo.addItem(forma);
            }

            // Selecionar forma atual
            if (venda.getFormaPagamento() != null) {
                pagamentoCombo.setSelectedItem(venda.getFormaPagamento());
            }

            // Preencher desconto atual
            if (venda.getDesconto() != null) {
                descontoField.setText(venda.getDesconto().toString().replace(".", ","));
            } else {
                descontoField.setText("0,00");
            }

            // Mostrar valor total atual
            valorTotalLabel.setText(String.format("R$ %.2f", venda.getValorTotal()));

        } catch (Exception e) {
            System.err.println("Erro ao carregar dados para atualização: " + e.getMessage());
        }
    }

    /**
     * Recalcula o valor total da venda na atualização
     * @param venda Venda sendo atualizada
     * @param descontoStr Novo desconto
     * @return Novo valor total formatado
     */
    public String recalcularValorTotal(VendaEntity venda, String descontoStr) {
        try {
            // Validar desconto
            BigDecimal desconto;
            try {
                desconto = new BigDecimal(descontoStr.trim().replace(",", "."));
                if (desconto.compareTo(BigDecimal.ZERO) < 0) {
                    desconto = BigDecimal.ZERO;
                }
            } catch (NumberFormatException e) {
                desconto = BigDecimal.ZERO;
            }

            // Calcular valor sem desconto
            BigDecimal valorSemDesconto = venda.getProduto().getPreco().multiply(new BigDecimal(venda.getQuantidade()));
            BigDecimal valorFinal = valorSemDesconto.subtract(desconto);

            if (valorFinal.compareTo(BigDecimal.ZERO) < 0) {
                valorFinal = BigDecimal.ZERO;
            }

            return String.format("R$ %.2f", valorFinal);

        } catch (Exception e) {
            System.err.println("Erro ao recalcular valor total: " + e.getMessage());
            return String.format("R$ %.2f", venda.getValorTotal());
        }
    }

    /**
     * Atualiza uma venda existente
     * @param venda Venda a ser atualizada
     * @param pagamentoSelecionado Nova forma de pagamento
     * @param descontoStr Novo desconto
     * @return Status da operação
     */
    public String atualizarVenda(VendaEntity venda, Object pagamentoSelecionado, String descontoStr) {

        // Validar forma de pagamento
        if (pagamentoSelecionado == null) {
            return "PAGAMENTO_NAO_SELECIONADO";
        }

        // Validar e converter desconto
        BigDecimal desconto;
        try {
            desconto = new BigDecimal(descontoStr.trim().replace(",", "."));
            if (desconto.compareTo(BigDecimal.ZERO) < 0) {
                return "DESCONTO_INVALIDO";
            }
        } catch (NumberFormatException e) {
            return "DESCONTO_INVALIDO";
        }

        try {
            boolean sucesso = VendaService.atualizarVenda(
                    venda.getId(), pagamentoSelecionado.toString(), desconto
            );

            return sucesso ? "SUCESSO" : "ERRO_BANCO";

        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Cancela uma venda pelo ID
     * @param id ID da venda
     * @return true se cancelada com sucesso, false caso contrário
     */
    public boolean cancelarVenda(Integer id) {
        try {
            return VendaService.cancelarVenda(id);
        } catch (Exception e) {
            System.err.println("Erro ao cancelar venda: " + e.getMessage());
            return false;
        }
    }

    // Métodos auxiliares privados

    /**
     * Filtra venda por termo de busca
     * @param venda Venda a ser filtrada
     * @param termo Termo de busca
     * @return true se a venda corresponde ao termo
     */
    private boolean filtrarPorTermo(VendaEntity venda, String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return true;
        }

        String termoLower = termo.toLowerCase();
        String cliente = venda.getCliente() != null ? venda.getCliente().getNome().toLowerCase() : "";
        String produto = venda.getProduto() != null ? venda.getProduto().getNome().toLowerCase() : "";

        return cliente.contains(termoLower) || produto.contains(termoLower);
    }

    /**
     * Formata cliente para combo box
     * @param cliente Cliente a ser formatado
     * @return String formatada para combo
     */
    private String formatarClienteParaCombo(ClienteEntity cliente) {
        return String.format("ID: %d - %s", cliente.getId(), cliente.getNome());
    }

    /**
     * Formata produto para combo box
     * @param produto Produto a ser formatado
     * @return String formatada para combo
     */
    private String formatarProdutoParaCombo(ProdutoEntity produto) {
        return String.format("ID: %d - %s - R$ %.2f (Estoque: %d)",
                produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidadeEstoque());
    }

    /**
     * Extrai ID do cliente da string do combo
     * @param clienteStr String do combo
     * @return ID do cliente ou null
     */
    private Integer extrairIdDoCliente(String clienteStr) {
        try {
            if (clienteStr.startsWith("ID: ")) {
                String idStr = clienteStr.substring(4, clienteStr.indexOf(" - "));
                return Integer.parseInt(idStr);
            }
        } catch (Exception e) {
            System.err.println("Erro ao extrair ID do cliente: " + e.getMessage());
        }
        return null;
    }

    /**
     * Extrai ID do produto da string do combo
     * @param produtoStr String do combo
     * @return ID do produto ou null
     */
    private Integer extrairIdDoProduto(String produtoStr) {
        try {
            if (produtoStr.startsWith("ID: ")) {
                String idStr = produtoStr.substring(4, produtoStr.indexOf(" - "));
                return Integer.parseInt(idStr);
            }
        } catch (Exception e) {
            System.err.println("Erro ao extrair ID do produto: " + e.getMessage());
        }
        return null;
    }

    /**
     * Extrai valor total da string formatada
     * @param valorStr String do valor (R$ 99,99)
     * @return BigDecimal do valor
     */
    private BigDecimal extrairValorTotal(String valorStr) {
        try {
            String valorLimpo = valorStr.replace("R$", "").replace(" ", "").replace(",", ".");
            return new BigDecimal(valorLimpo);
        } catch (Exception e) {
            System.err.println("Erro ao extrair valor total: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}