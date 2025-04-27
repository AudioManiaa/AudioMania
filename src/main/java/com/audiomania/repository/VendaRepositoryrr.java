package com.audiomania.repository;

import com.audiomania.entities.VendaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class VendaRepositoryrr {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");

    public void salvar(VendaEntity venda) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(venda);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<VendaEntity> listarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM VendaEntity", VendaEntity.class).getResultList();
        } finally {
            em.close();
        }
    }
}
