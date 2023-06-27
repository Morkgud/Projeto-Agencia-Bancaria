package programa.dao;

import programa.models.Cliente;
import jakarta.persistence.*;
import java.util.List;

public class ClienteDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projeto");

    public static void salvar(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(cliente);

        transaction.commit();
        em.close();
    }

    public static List<Cliente> listar() {
        EntityManager em = emf.createEntityManager();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        em.close();
        return clientes;
    }

    public static Cliente buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = em.find(Cliente.class, id);
        em.close();
        return cliente;
    }
}
