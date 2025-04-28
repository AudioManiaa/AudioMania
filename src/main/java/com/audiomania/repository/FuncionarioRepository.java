package com.audiomania.repository;

import com.audiomania.entities.FuncionarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class FuncionarioRepository {

    private EntityManagerFactory emf;

    public FuncionarioRepository() {
        this.emf = Persistence.createEntityManagerFactory("meuPU");
    }

    public FuncionarioEntity autenticar(String cpf, String senha) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FuncionarioEntity> query = em.createQuery(
                    "SELECT f FROM FuncionarioEntity f WHERE f.cpf = :cpf AND f.senha = :senha",
                    FuncionarioEntity.class);
            query.setParameter("cpf", cpf);
            query.setParameter("senha", senha);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public FuncionarioEntity buscarPorCpf(String cpf) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FuncionarioEntity> query = em.createQuery(
                    "SELECT f FROM FuncionarioEntity f WHERE f.cpf = :cpf",
                    FuncionarioEntity.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public FuncionarioEntity buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(FuncionarioEntity.class, id);
        } finally {
            em.close();
        }
    }

    public List<FuncionarioEntity> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FuncionarioEntity> query = em.createQuery(
                    "SELECT f FROM FuncionarioEntity f ORDER BY f.nome",
                    FuncionarioEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean salvar(FuncionarioEntity funcionario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(funcionario);
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

    public boolean atualizar(FuncionarioEntity funcionario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(funcionario);
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
            FuncionarioEntity funcionario = em.find(FuncionarioEntity.class, id);
            if (funcionario != null) {
                em.remove(funcionario);
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