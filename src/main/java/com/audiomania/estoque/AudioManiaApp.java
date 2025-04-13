package com.audiomania.estoque;

import com.audiomania.estoque.controller.EstoqueController;
import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.repository.CategoriaRepository;
import com.audiomania.estoque.repository.EstoqueRepository;
import com.audiomania.estoque.repository.ProdutoRepository;
import com.audiomania.estoque.view.EstoqueView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class AudioManiaApp {

    public static void main(String[] args) {
        // Inicializar o sistema com alguns dados de exemplo
        inicializarDadosExemplo();

        // Criar controlador e view
        EstoqueView estoqueView = new EstoqueView();
        EstoqueController controller = new EstoqueController();

        // Iniciar o loop principal do programa
        controller.iniciar();

        // Fechar recursos
        System.out.println("Sistema encerrado.");
    }

    private static void inicializarDadosExemplo() {
        // Criar categorias
        Categoria instrumentos = new Categoria(1L, "INS", "Instrumentos Musicais", "Guitarras, baixos, teclados, etc.");
        Categoria acessorios = new Categoria(2L, "ACS", "Acessórios", "Cabos, palhetas, cordas, etc.");
        Categoria equipamentos = new Categoria(3L, "EQP", "Equipamentos de Som", "Amplificadores, mesas de som, etc.");

        // Adicionar categorias ao repositório
        CategoriaRepository.adicionar(instrumentos);
        CategoriaRepository.adicionar(acessorios);
        CategoriaRepository.adicionar(equipamentos);

        // Criar produtos
        Produto guitarra = new Produto(1L, "GTR-001", "Guitarra Stratocaster",
                "Guitarra elétrica modelo Stratocaster",
                BigDecimal.valueOf(1200.00), BigDecimal.valueOf(1800.00),
                "FenderBR", instrumentos);

        Produto baixo = new Produto(2L, "BX-002", "Baixo Jazz Bass",
                "Baixo elétrico modelo Jazz Bass",
                BigDecimal.valueOf(1500.00), BigDecimal.valueOf(2200.00),
                "FenderBR", instrumentos);

        Produto teclado = new Produto(3L, "TEC-003", "Teclado Profissional",
                "Teclado profissional com 88 teclas",
                BigDecimal.valueOf(2500.00), BigDecimal.valueOf(3600.00),
                "Yamaha", instrumentos);

        Produto cabo = new Produto(4L, "CB-004", "Cabo P10 3 metros",
                "Cabo P10 para instrumentos com 3 metros",
                BigDecimal.valueOf(25.00), BigDecimal.valueOf(45.00),
                "Santo Angelo", acessorios);

        Produto palhetas = new Produto(5L, "PLH-005", "Pacote de Palhetas",
                "Pacote com 10 palhetas médias",
                BigDecimal.valueOf(15.00), BigDecimal.valueOf(30.00),
                "Dunlop", acessorios);

        Produto amplificador = new Produto(6L, "AMP-006", "Amplificador 50W",
                "Amplificador para guitarra 50W",
                BigDecimal.valueOf(600.00), BigDecimal.valueOf(950.00),
                "Marshall", equipamentos);

        // Adicionar produtos ao repositório
        ProdutoRepository.adicionar(guitarra);
        ProdutoRepository.adicionar(baixo);
        ProdutoRepository.adicionar(teclado);
        ProdutoRepository.adicionar(cabo);
        ProdutoRepository.adicionar(palhetas);
        ProdutoRepository.adicionar(amplificador);

        // Criar itens de estoque
        ItemEstoque itemGuitarra = new ItemEstoque(1L, guitarra, 5, 3, "Prateleira A1");
        ItemEstoque itemBaixo = new ItemEstoque(2L, baixo, 3, 2, "Prateleira A2");
        ItemEstoque itemTeclado = new ItemEstoque(3L, teclado, 2, 2, "Prateleira B1");
        ItemEstoque itemCabo = new ItemEstoque(4L, cabo, 30, 15, "Gaveta C1");
        ItemEstoque itemPalhetas = new ItemEstoque(5L, palhetas, 50, 25, "Gaveta C2");
        ItemEstoque itemAmplificador = new ItemEstoque(6L, amplificador, 4, 3, "Prateleira D1");

        // Adicionar itens ao repositório de estoque
        EstoqueRepository.adicionar(itemGuitarra);
        EstoqueRepository.adicionar(itemBaixo);
        EstoqueRepository.adicionar(itemTeclado);
        EstoqueRepository.adicionar(itemCabo);
        EstoqueRepository.adicionar(itemPalhetas);
        EstoqueRepository.adicionar(itemAmplificador);

        // Criar produto para o item com estoque baixo
        Produto violino = new Produto(7L, "VLN-007", "Violino 4/4",
                "Violino tamanho 4/4 completo com estojo",
                BigDecimal.valueOf(800.00), BigDecimal.valueOf(1200.00),
                "Eagle", instrumentos);

        // Adicionar o produto ao repositório
        ProdutoRepository.adicionar(violino);

        // Criar item com estoque baixo para teste
        ItemEstoque itemBaixoEstoque = new ItemEstoque(7L, violino, 1, 2, "Prateleira A3");

        // Configurar as datas manualmente
        itemBaixoEstoque.setDataUltimaEntrada(LocalDate.now());
        itemBaixoEstoque.setDataUltimaSaida(LocalDate.now().minusDays(15));

        // Adicionar ao repositório
        EstoqueRepository.adicionar(itemBaixoEstoque);
    }
}