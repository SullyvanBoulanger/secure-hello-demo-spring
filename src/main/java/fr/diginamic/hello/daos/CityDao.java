package fr.diginamic.hello.daos;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.dtos.CityDtoFromFront;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;
import jakarta.persistence.TypedQuery;

@Repository
public class CityDao extends SuperDao<Integer, City, CityDtoFromFront> {

    public List<City> findAll() {
        TypedQuery<City> query = entityManager.createQuery("SELECT c FROM City c", City.class);
        return query.getResultList();
    }

    @Override
    public City findById(Integer id) {
        return entityManager.find(City.class, id);
    }

    public City findByName(String name) {
        TypedQuery<City> query = entityManager.createQuery("SELECT c FROM City c WHERE c.name LIKE :name", City.class);
        query.setParameter("name", name);

        try {
            return query.getResultList().getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void insert(CityDtoFromFront dto) {
        City entityCity = new City();
        entityCity.setName(dto.getName());
        entityCity.setNumberInhabitants(dto.getNumberInhabitants());
        entityCity.setDepartment(entityManager.find(Department.class, dto.getDepartmentId()));

        entityManager.persist(entityCity);
    }

    @Override
    @Transactional
    public boolean update(Integer id, CityDtoFromFront dto) {
        City city = findById(id);

        if (city == null)
            return false;

        city.setName(dto.getName());
        city.setNumberInhabitants(dto.getNumberInhabitants());
        city.setDepartment(entityManager.find(Department.class, dto.getDepartmentId()));

        return true;
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        City city = findById(id);

        if (city == null)
            return false;

        entityManager.remove(city);

        return true;
    }

    @Transactional
    public boolean updateDepartment(int cityId, Department department) {
        City city = findById(cityId);

        if (city == null)
            return false;

        city.setDepartment(department);

        return true;
    }
}
