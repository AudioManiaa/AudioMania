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
        dadosExemplo();

        // Criar controlador e view
        EstoqueView estoqueView = new EstoqueView();
        EstoqueController controller = new EstoqueController();

        // Iniciar o loop principal do programa
        controller.iniciar();

        // Fechar recursos
        System.out.println("Sistema encerrado.");
    }

    private static void dadosExemplo() {
        // Criar categorias
        Categoria autofalantes = new Categoria(1L, "AUT", "Autofalantes", "Woofers, tweeters, mid-range, etc");
        Categoria acessorios = new Categoria(2L, "ACS", "Acessórios", "Cabos, conectores, interfaces, etc.");
        Categoria equipamentos = new Categoria(3L, "EQP", "Equipamentos de Audio", "Amplificadores, processadores, etc.");

        // Adicionar categorias ao repositório
        CategoriaRepository.adicionar(autofalantes);
        CategoriaRepository.adicionar(acessorios);
        CategoriaRepository.adicionar(equipamentos);

        // Criar produtos
        Produto subwoofer = new Produto(1L, "SUB-001", "Subwoofer 12 polegadas",
                "Subwoofer de 12 polegadas com bobina dupla",
                BigDecimal.valueOf(450.00), BigDecimal.valueOf(750.00),
                "JBL", autofalantes);

        Produto woofer = new Produto(2L, "WF-002", "Woofer 6x9",
                "Par de alto-falantes 6x9 com 400W de potência",
                BigDecimal.valueOf(280.00), BigDecimal.valueOf(450.00),
                "Pioneer", autofalantes);

        Produto tweeter = new Produto(3L, "TW-003", "Kit Tweeter 300W",
                "Kit de tweeters com 300W de potência máxima",
                BigDecimal.valueOf(150.00), BigDecimal.valueOf(250.00),
                "Bravox", autofalantes);

        Produto cabos = new Produto(4L, "CB-004", "Kit de Cabos RCA",
                "Kit completo de cabos RCA para instalação",
                BigDecimal.valueOf(45.00), BigDecimal.valueOf(85.00),
                "Technoise", acessorios);

        Produto capacitor = new Produto(5L, "CAP-005", "Capacitor 1.0 Farad",
                "Capacitor para estabilização de energia",
                BigDecimal.valueOf(120.00), BigDecimal.valueOf(199.00),
                "Taramps", acessorios);

        Produto amplificador = new Produto(6L, "AMP-006", "Amplificador 1200W",
                "Amplificador de 4 canais com 1200W RMS",
                BigDecimal.valueOf(550.00), BigDecimal.valueOf(890.00),
                "Soundigital", equipamentos);


        // Adicionar produtos ao repositório
        ProdutoRepository.adicionar(subwoofer);
        ProdutoRepository.adicionar(woofer);
        ProdutoRepository.adicionar(tweeter);
        ProdutoRepository.adicionar(cabos);
        ProdutoRepository.adicionar(capacitor);
        ProdutoRepository.adicionar(amplificador);

        // Criar itens de estoque
        ItemEstoque itemSubwoofer = new ItemEstoque(1L, subwoofer, 8, 5, "Prateleira A1");
        ItemEstoque itemWoofer = new ItemEstoque(2L, woofer, 12, 6, "Prateleira A2");
        ItemEstoque itemTweeter = new ItemEstoque(3L, tweeter, 15, 10, "Prateleira B1");
        ItemEstoque itemCabos = new ItemEstoque(4L, cabos, 30, 15, "Gaveta C1");
        ItemEstoque itemCapacitor = new ItemEstoque(5L, capacitor, 20, 10, "Gaveta C2");
        ItemEstoque itemAmplificador = new ItemEstoque(6L, amplificador, 6, 4, "Prateleira D1");

        // Adicionar itens ao repositório de estoque
        EstoqueRepository.adicionar(itemSubwoofer);
        EstoqueRepository.adicionar(itemWoofer);
        EstoqueRepository.adicionar(itemTweeter);
        EstoqueRepository.adicionar(itemCabos);
        EstoqueRepository.adicionar(itemCapacitor);
        EstoqueRepository.adicionar(itemAmplificador);

        // Criar produto para o item com estoque baixo
        Produto processador = new Produto(7L, "PROC-007", "Processador de Áudio Digital",
                "Processador DSP com 8 canais para equalização",
                BigDecimal.valueOf(900.00), BigDecimal.valueOf(1500.00),
                "AudioControl", equipamentos);

        // Adicionar o produto ao repositório
        ProdutoRepository.adicionar(processador);

        // Criar item com estoque baixo para teste
        ItemEstoque itemBaixoEstoque = new ItemEstoque(7L, processador, 1, 2, "Prateleira A3");

        // Configurar as datas manualmente
        itemBaixoEstoque.setDataUltimaEntrada(LocalDate.now());
        itemBaixoEstoque.setDataUltimaSaida(LocalDate.now().minusDays(15));


        // Adicionar ao repositório
        EstoqueRepository.adicionar(itemBaixoEstoque);
    }
}