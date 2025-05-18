package com.audiomania.service;

import com.audiomania.entities.ClienteEntity;
import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.entities.ProdutoEntity;
import com.audiomania.entities.VendaEntity;
import com.audiomania.repository.ClienteRepository;
import com.audiomania.repository.FuncionarioRepository;
import com.audiomania.repository.ProdutoRepository;
import com.audiomania.repository.VendaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VendaService {

    private static VendaRepository vendaRepository = new VendaRepository();
    private static ClienteRepository clienteRepository = new ClienteRepository();
    private static FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    private static ProdutoRepository produtoRepository = new ProdutoRepository();

    public static VendaEntity buscarPorId(Integer id) {
        return vendaRepository.buscarPorId(id);
    }

    public static List<VendaEntity> listarTodas() {
        return vendaRepository.listarTodos();
    }

    public static boolean registrarVenda(Integer idCliente, Integer idFuncionario, Integer idProduto,
                                         Integer quantidade, BigDecimal valorTotal, String formaPagamento,
                                         BigDecimal desconto) {

        ClienteEntity cliente = clienteRepository.buscarPorId(idCliente);
        if (cliente == null) {
            return false;
        }

        FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(idFuncionario);
        if (funcionario == null) {
            return false;
        }

        ProdutoEntity produto = produtoRepository.buscarPorId(idProduto);
        if (produto == null || produto.getQuantidadeEstoque() < quantidade) {
            return false;
        }

        // Atualizar estoque do produto
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        boolean atualizouEstoque = produtoRepository.atualizar(produto);

        if (!atualizouEstoque) {
            return false;
        }

        VendaEntity venda = new VendaEntity();
        venda.setData(LocalDate.now());
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);
        venda.setProduto(produto);
        venda.setQuantidade(quantidade);
        venda.setValorTotal(valorTotal);
        venda.setFormaPagamento(formaPagamento);
        venda.setDesconto(desconto);

        return vendaRepository.salvar(venda);
    }

    public static boolean atualizarVenda(Integer id, String formaPagamento, BigDecimal desconto) {
        VendaEntity venda = vendaRepository.buscarPorId(id);
        if (venda == null) {
            return false;
        }

        venda.setFormaPagamento(formaPagamento);
        venda.setDesconto(desconto);

        // Recalcular valor total se necessário
        if (desconto != null) {
            BigDecimal valorSemDesconto = venda.getProduto().getPreco().multiply(new BigDecimal(venda.getQuantidade()));
            BigDecimal valorTotal = valorSemDesconto.subtract(desconto);
            if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                valorTotal = BigDecimal.ZERO;
            }
            venda.setValorTotal(valorTotal);
        }

        return vendaRepository.atualizar(venda);
    }

    public static boolean cancelarVenda(Integer id) {
        VendaEntity venda = vendaRepository.buscarPorId(id);
        if (venda == null) {
            return false;
        }

        // Devolver produto ao estoque
        ProdutoEntity produto = venda.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + venda.getQuantidade());
        boolean atualizouEstoque = produtoRepository.atualizar(produto);

        if (!atualizouEstoque) {
            return false;
        }

        return vendaRepository.excluir(id);
    }

    public static void fecharRecursos() {
        vendaRepository.fechar();
    }
}