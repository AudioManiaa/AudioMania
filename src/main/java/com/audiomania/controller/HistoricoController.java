package com.audiomania.controller;

import com.audiomania.model.entities.VendaEntity;
import com.audiomania.model.service.VendaService;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;

public class HistoricoController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Lista todas as vendas do sistema
     * @return Lista de vendas
     */
    public List<VendaEntity> listarTodasVendas() {
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

            String termoLower = termo.toLowerCase();

            return todasVendas.stream()
                    .filter(venda -> {
                        String cliente = venda.getCliente() != null ? venda.getCliente().getNome().toLowerCase() : "";
                        String produto = venda.getProduto() != null ? venda.getProduto().getNome().toLowerCase() : "";

                        return cliente.contains(termoLower) || produto.contains(termoLower);
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erro ao buscar vendas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Filtra vendas por período de datas
     * @param dataInicioStr Data de início no formato dd/MM/yyyy
     * @param dataFimStr Data de fim no formato dd/MM/yyyy
     * @return Lista de vendas no período
     */
    public List<VendaEntity> filtrarPorPeriodo(String dataInicioStr, String dataFimStr) {
        try {
            LocalDate dataInicio = LocalDate.parse(dataInicioStr, DATE_FORMATTER);
            LocalDate dataFim = LocalDate.parse(dataFimStr, DATE_FORMATTER);

            List<VendaEntity> todasVendas = VendaService.listarTodas();

            return todasVendas.stream()
                    .filter(venda -> {
                        if (venda.getData() == null) return false;

                        // getData() já retorna LocalDate
                        LocalDate dataVenda = venda.getData();
                        return !dataVenda.isBefore(dataInicio) && !dataVenda.isAfter(dataFim);
                    })
                    .collect(Collectors.toList());

        } catch (DateTimeParseException e) {
            System.err.println("Erro ao parsear datas: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro ao filtrar por período: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Valida as datas de filtro
     * @param dataInicioStr Data de início
     * @param dataFimStr Data de fim
     * @return Status da validação
     */
    public String validarDatas(String dataInicioStr, String dataFimStr) {
        try {
            // Validar data de início
            LocalDate dataInicio;
            try {
                dataInicio = LocalDate.parse(dataInicioStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                return "DATA_INICIO_INVALIDA";
            }

            // Validar data de fim
            LocalDate dataFim;
            try {
                dataFim = LocalDate.parse(dataFimStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                return "DATA_FIM_INVALIDA";
            }

            // Verificar se data início é menor ou igual à data fim
            if (dataInicio.isAfter(dataFim)) {
                return "DATA_INICIO_MAIOR";
            }

            return "VALIDO";

        } catch (Exception e) {
            System.err.println("Erro ao validar datas: " + e.getMessage());
            return "ERRO_VALIDACAO";
        }
    }

    /**
     * Formata venda para exibição na tabela do histórico
     * @param venda Venda a ser formatada
     * @return Array de objetos para a tabela
     */
    public Object[] formatarVendaParaTabela(VendaEntity venda) {
        try {
            double valorUnitario = 0.0;
            if (venda.getQuantidade() > 0) {
                // Converter BigDecimal para double
                valorUnitario = venda.getValorTotal().doubleValue() / venda.getQuantidade();
            }

            double desconto = calcularDesconto(venda);

            return new Object[]{
                    venda.getId(),
                    venda.getData() != null ? venda.getData().format(DATE_FORMATTER) : "",
                    venda.getCliente() != null ? venda.getCliente().getNome() : "",
                    venda.getProduto() != null ? venda.getProduto().getNome() : "",
                    venda.getQuantidade(),
                    String.format("R$ %.2f", valorUnitario),
                    String.format("R$ %.2f", desconto),
                    String.format("R$ %.2f", venda.getValorTotal().doubleValue()),
                    venda.getFormaPagamento()
            };
        } catch (Exception e) {
            System.err.println("Erro ao formatar venda para tabela: " + e.getMessage());
            return new Object[]{
                    venda.getId(), "Erro", "Erro", "Erro", 0, "R$ 0,00", "R$ 0,00", "R$ 0,00", "Erro"
            };
        }
    }

    /**
     * Calcula o valor total de uma lista de vendas
     * @param vendas Lista de vendas
     * @return Valor total
     */
    public double calcularValorTotal(List<VendaEntity> vendas) {
        return vendas.stream()
                .mapToDouble(venda -> venda.getValorTotal().doubleValue())
                .sum();
    }

    /**
     * Calcula a quantidade total de produtos vendidos
     * @param vendas Lista de vendas
     * @return Quantidade total
     */
    public int calcularQuantidadeTotal(List<VendaEntity> vendas) {
        return vendas.stream()
                .mapToInt(VendaEntity::getQuantidade)
                .sum();
    }

    /**
     * Exporta dados para CSV
     * @param vendas Lista de vendas para exportar
     * @return Status da operação
     */
    public String exportarParaExcel(List<VendaEntity> vendas) {
        try {
            String nomeArquivo = "historico_vendas_" +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".csv";

            FileWriter writer = new FileWriter(nomeArquivo);

            // Cabeçalho
            writer.write("ID,Data,Cliente,Produto,Quantidade,Valor Unitário,Desconto,Valor Total,Forma Pagamento\n");

            // Dados
            for (VendaEntity venda : vendas) {
                Object[] linha = formatarVendaParaTabela(venda);

                writer.write(String.valueOf(linha[0]) + ",");
                writer.write(String.valueOf(linha[1]) + ",");
                writer.write("\"" + String.valueOf(linha[2]) + "\",");
                writer.write("\"" + String.valueOf(linha[3]) + "\",");
                writer.write(String.valueOf(linha[4]) + ",");
                writer.write(String.valueOf(linha[5]) + ",");
                writer.write(String.valueOf(linha[6]) + ",");
                writer.write(String.valueOf(linha[7]) + ",");
                writer.write("\"" + String.valueOf(linha[8]) + "\"");
                writer.write("\n");
            }

            // Totais
            writer.write("\n");
            writer.write("TOTAIS,,,,");
            writer.write(String.valueOf(calcularQuantidadeTotal(vendas)) + ",");
            writer.write(","); // Valor unitário não faz sentido no total
            writer.write(","); // Desconto total seria complexo de calcular
            writer.write(String.format("R$ %.2f", calcularValorTotal(vendas)) + ",");
            writer.write("\n");

            writer.close();

            return "SUCESSO";

        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo: " + e.getMessage());
            return "ERRO_ESCRITA";
        } catch (Exception e) {
            System.err.println("Erro ao exportar dados: " + e.getMessage());
            return "ERRO_GERAL";
        }
    }

    /**
     * Obtém as vendas atualmente exibidas na tabela
     * @param tableModel Modelo da tabela
     * @return Lista de vendas da tabela
     */
    public List<VendaEntity> obterVendasTabela(DefaultTableModel tableModel) {
        List<VendaEntity> vendas = new ArrayList<>();

        try {
            List<VendaEntity> todasVendas = VendaService.listarTodas();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Integer id = (Integer) tableModel.getValueAt(i, 0);

                // Buscar a venda completa pelo ID
                for (VendaEntity venda : todasVendas) {
                    if (venda.getId().equals(id)) {
                        vendas.add(venda);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao obter vendas da tabela: " + e.getMessage());
        }

        return vendas;
    }

    /**
     * Gera relatório de vendas por período
     * @param vendas Lista de vendas
     * @return String com relatório formatado
     */
    public String gerarRelatorio(List<VendaEntity> vendas) {
        if (vendas.isEmpty()) {
            return "Nenhuma venda encontrada no período selecionado.";
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE VENDAS ===\n\n");

        relatorio.append("Total de Vendas: ").append(vendas.size()).append("\n");
        relatorio.append("Quantidade Total de Produtos: ").append(calcularQuantidadeTotal(vendas)).append("\n");
        relatorio.append("Valor Total: ").append(String.format("R$ %.2f", calcularValorTotal(vendas))).append("\n");

        if (vendas.size() > 0) {
            relatorio.append("Ticket Médio: ").append(String.format("R$ %.2f", calcularValorTotal(vendas) / vendas.size())).append("\n\n");
        }

        return relatorio.toString();
    }

    // Métodos auxiliares privados

    /**
     * Calcula o desconto aplicado na venda
     * @param venda Venda
     * @return Valor do desconto
     */
    private double calcularDesconto(VendaEntity venda) {
        try {
            if (venda.getProduto() == null) return 0.0;

            // Converter BigDecimal para double e calcular desconto
            double precoOriginal = venda.getProduto().getPreco().doubleValue() * venda.getQuantidade();
            double valorFinal = venda.getValorTotal().doubleValue();

            return Math.max(0, precoOriginal - valorFinal);

        } catch (Exception e) {
            return 0.0;
        }
    }
}