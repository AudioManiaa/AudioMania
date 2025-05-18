package com.audiomania.repository;

import com.audiomania.entities.ProdutoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProdutoRepository {

    private EntityManagerFactory emf;

    public ProdutoRepository() {
        this.emf = Persistence.createEntityManagerFactory("meuPU");
    }

    public ProdutoEntity buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ProdutoEntity.class, id);
        } finally {
            em.close();
        }
    }

    public List<ProdutoEntity> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ProdutoEntity> query = em.createQuery(
                    "SELECT p FROM ProdutoEntity p ORDER BY p.nome",
                    ProdutoEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean salvar(ProdutoEntity produto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean atualizar(ProdutoEntity produto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean excluir(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ProdutoEntity produto = em.find(ProdutoEntity.class, id);
            if (produto != null) {
                em.remove(produto);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public void fechar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}