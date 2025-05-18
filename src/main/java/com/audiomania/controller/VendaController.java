package com.audiomania.controller;

import com.audiomania.entities.VendaEntity;
import com.audiomania.service.VendaService;

import java.math.BigDecimal;
import java.util.List;

public class VendaController {

    /**
     * Busca uma venda pelo ID
     * @param id ID da venda
     * @return O objeto VendaEntity ou null se não encontrado
     */
    public VendaEntity buscarPorId(Integer id) {
        return VendaService.buscarPorId(id);
    }

    /**
     * Lista todas as vendas
     * @return Lista de vendas
     */
    public List<VendaEntity> listarVendas() {
        return VendaService.listarTodas();
    }

    /**
     * Registra uma nova venda
     * @param idCliente ID do cliente
     * @param idFuncionario ID do funcionário
     * @param idProduto ID do produto
     * @param quantidade Quantidade vendida
     * @param valorTotal Valor total da venda
     * @param formaPagamento Forma de pagamento
     * @param desconto Valor do desconto (opcional)
     * @return true se registrado com sucesso, false caso contrário
     */
    public boolean registrarVenda(Integer idCliente, Integer idFuncionario, Integer idProduto,
                                  Integer quantidade, BigDecimal valorTotal, String formaPagamento,
                                  BigDecimal desconto) {
        return VendaService.registrarVenda(idCliente, idFuncionario, idProduto, quantidade,
                valorTotal, formaPagamento, desconto);
    }

    /**
     * Atualiza uma venda existente
     * @param id ID da venda
     * @param formaPagamento Nova forma de pagamento
     * @param desconto Novo valor de desconto
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarVenda(Integer id, String formaPagamento, BigDecimal desconto) {
        return VendaService.atualizarVenda(id, formaPagamento, desconto);
    }

    /**
     * Cancela uma venda
     * @param id ID da venda
     * @return true se cancelado com sucesso, false caso contrário
     */
    public boolean cancelarVenda(Integer id) {
        return VendaService.cancelarVenda(id);
    }
}