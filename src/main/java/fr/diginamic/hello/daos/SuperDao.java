package fr.diginamic.hello.daos;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class SuperDao<S, T, U> {
    @PersistenceContext
    protected EntityManager entityManager;

    public abstract List<T> findAll();

    public abstract T findById(S id);

    @Transactional
    public abstract void insert(U dto);

    @Transactional
    public abstract boolean update(S id, U dto);

    @Transactional
    public abstract boolean delete(S id);
}
