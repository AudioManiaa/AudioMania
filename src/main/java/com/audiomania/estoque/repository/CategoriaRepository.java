package com.audiomania.estoque.repository;

import com.audiomania.estoque.model.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repositório responsável pelo armazenamento e manipulação de categorias.
 */
public class CategoriaRepository {

    private static final List<Categoria> categorias = new ArrayList<>();
    private static Long proximoId = 1L;

    /**
     * Adiciona uma nova categoria ao repositório.
     *
     * @param categoria A categoria a ser adicionada
     * @return A categoria adicionada
     */
    public static Categoria adicionar(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(proximoId++);
        } else {
            proximoId = Math.max(proximoId, categoria.getId() + 1);
        }
        categorias.add(categoria);
        return categoria;
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id O ID da categoria
     * @return Um Optional contendo a categoria, se encontrada
     */
    public static Optional<Categoria> buscarPorId(Long id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    /**
     * Busca categorias pelo nome (ou parte dele).
     *
     * @param nome O nome ou parte do nome da categoria
     * @return Lista de categorias que correspondem ao critério de busca
     */
    public static List<Categoria> buscarPorNome(String nome) {
        return categorias.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as categorias do repositório.
     *
     * @return Lista de todas as categorias
     */
    public static List<Categoria> listarTodos() {
        return new ArrayList<>(categorias);
    }

    /**
     * Atualiza uma categoria existente.
     *
     * @param categoria A categoria com as informações atualizadas
     * @return A categoria atualizada, ou null se não for encontrada
     */
    public static Categoria atualizar(Categoria categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoria.getId())) {
                categorias.set(i, categoria);
                return categoria;
            }
        }
        return null;
    }

    /**
     * Remove uma categoria pelo ID.
     *
     * @param id O ID da categoria a ser removida
     * @return true se a categoria foi removida, false caso contrário
     */
    public static boolean remover(Long id) {
        return categorias.removeIf(c -> c.getId().equals(id));
    }

    /**
     * Limpa todas as categorias do repositório (útil para testes).
     */
    public static void limpar() {
        categorias.clear();
        proximoId = 1L;
    }
}