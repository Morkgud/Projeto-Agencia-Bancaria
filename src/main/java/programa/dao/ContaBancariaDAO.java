package programa.dao;

import programa.models.ContaBancaria;

import jakarta.persistence.*;
import java.util.List;

public class ContaBancariaDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projeto");

    public static void salvar(ContaBancaria conta) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(conta);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static ContaBancaria buscarPorNumeroConta(int numeroConta) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ContaBancaria.class, numeroConta);
        } finally {
            em.close();
        }
    }

    public static List<ContaBancaria> listar() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM ContaBancaria c", ContaBancaria.class).getResultList();
        } finally {
            em.close();
        }
    }
    public static void deletar(ContaBancaria conta) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        conta = em.merge(conta);
        em.remove(conta);

        transaction.commit();
        em.close();
    }
    public static List<ContaBancaria> listar(int limite) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ContaBancaria> query = em.createQuery("SELECT c FROM ContaBancaria c", ContaBancaria.class);
            query.setMaxResults(limite);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


}
