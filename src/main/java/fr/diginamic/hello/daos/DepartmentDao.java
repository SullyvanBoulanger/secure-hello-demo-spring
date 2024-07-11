package fr.diginamic.hello.daos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello.dtos.DepartmentDtoFromFront;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;
import jakarta.persistence.TypedQuery;

@Repository
public class DepartmentDao extends SuperDao<String, Department, DepartmentDtoFromFront> {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<Department> findAll() {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d", Department.class);
        return query.getResultList();
    }

    @Override
    public Department findById(String code) {
        return entityManager.find(Department.class, code);
    }

    public Department findByName(String name) {
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d WHERE d.name LIKE :name",
                Department.class);
        query.setParameter("name", name);

        try {
            return query.getResultList().getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void insert(DepartmentDtoFromFront departmentDto) {
        Department department = new Department();
        department.setCode(departmentDto.getCode());
        department.setName(departmentDto.getName());
        bindCityToDepartment(department, departmentDto.getCitiesIds());

        entityManager.persist(department);
    }

    @Override
    @Transactional
    public boolean update(String code, DepartmentDtoFromFront departmentDto) {
        Department department = findById(code);

        if (department == null)
            return false;

        department.setName(departmentDto.getName());
        bindCityToDepartment(department, departmentDto.getCitiesIds());

        return true;
    }

    @Override
    @Transactional
    public boolean delete(String code) {
        Department department = findById(code);

        if (department == null)
            return false;

        entityManager.remove(department);

        return true;
    }

    public List<City> getBigCities(int id, int limit) {
        TypedQuery<City> query = entityManager.createQuery(
                "SELECT c FROM Department d JOIN d.cities c WHERE d.id = :id ORDER BY c.numberInhabitants DESC",
                City.class);
        query.setParameter("id", id);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public List<City> getCitiesBetweenNumberInhabitants(int id, int min, int max) {
        TypedQuery<City> query = entityManager.createQuery(
                "SELECT c FROM Department d JOIN d.cities c WHERE (d.id = :id) AND (c.numberInhabitants BETWEEN :min AND :max) ORDER BY c.numberInhabitants DESC",
                City.class);
        query.setParameter("id", id);
        query.setParameter("min", min);
        query.setParameter("max", max);

        return query.getResultList();
    }

    @Transactional
    private void bindCityToDepartment(Department department, int[] citiesIds) {
        Set<City> cities = new HashSet<>();

        for (int id : citiesIds) {
            City city = cityDao.findById(id);

            if (city != null) {
                cityDao.updateDepartment(id, department);
                cities.add(city);
            }
        }

        department.setCities(cities);
    }
}
