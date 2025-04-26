package com.audiomania.repository;

import com.audiomania.entities.FuncionarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

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

    public void fechar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}