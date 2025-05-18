package com.audiomania.repository;

import com.audiomania.entities.VendaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class VendaRepository {

    private EntityManagerFactory emf;

    public VendaRepository() {
        this.emf = Persistence.createEntityManagerFactory("meuPU");
    }

    public VendaEntity buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(VendaEntity.class, id);
        } finally {
            em.close();
        }
    }

    public List<VendaEntity> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<VendaEntity> query = em.createQuery(
                    "SELECT v FROM VendaEntity v ORDER BY v.data DESC",
                    VendaEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean salvar(VendaEntity venda) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(venda);
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

    public boolean atualizar(VendaEntity venda) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(venda);
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
            VendaEntity venda = em.find(VendaEntity.class, id);
            if (venda != null) {
                em.remove(venda);
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