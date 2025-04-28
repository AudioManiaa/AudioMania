package com.audiomania.repository;

import com.audiomania.entities.ClienteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClienteRepository {

    private EntityManagerFactory emf;

    public ClienteRepository() {
        this.emf = Persistence.createEntityManagerFactory("meuPU");
    }

    public ClienteEntity buscarPorCpf(String cpf) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ClienteEntity> query = em.createQuery(
                    "SELECT c FROM ClienteEntity c WHERE c.cpf = :cpf",
                    ClienteEntity.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public ClienteEntity buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ClienteEntity.class, id);
        } finally {
            em.close();
        }
    }

    public List<ClienteEntity> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ClienteEntity> query = em.createQuery(
                    "SELECT c FROM ClienteEntity c ORDER BY c.nome",
                    ClienteEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean salvar(ClienteEntity cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
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

    public boolean atualizar(ClienteEntity cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
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
            ClienteEntity cliente = em.find(ClienteEntity.class, id);
            if (cliente != null) {
                em.remove(cliente);
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