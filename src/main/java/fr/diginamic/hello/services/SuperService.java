package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.SuperDao;

@Service
public abstract class SuperService<S, T, U> {
    @Autowired
    private SuperDao<S, T, U> dao;

    @Autowired
    private JpaRepository<T, S> repository; 

    public List<T> getAll() {
        return dao.findAll();
    }

    public T getById(S id) {
        return dao.findById(id);
    }

    public List<T> insertFromDto(U dto) {
        dao.insert(dto);

        return dao.findAll();
    }

    public List<T> insertFromEntity(T entity){
        repository.save(entity);

        return repository.findAll();
    }

    public List<T> modify(S id, U dto) {
        if (!dao.update(id, dto)) {
            return null;
        }

        return dao.findAll();
    }

    public List<T> delete(S id) {
        if (!dao.delete(id)) {
            return null;
        }

        return dao.findAll();
    }
}
