package fr.diginamic.hello.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.City;

public interface CityRepository extends JpaRepository<City, Integer> {
    
    public List<City> findByNameStartingWith(String name);

    public List<City> findByNumberInhabitantsGreaterThan(int min);

    public List<City> findByNumberInhabitantsBetween(int min, int max);

    public List<City> findByNumberInhabitantsGreaterThanAndDepartmentCode(int min, String departmentCode);

    public List<City> findByNumberInhabitantsBetweenAndDepartmentCode(int min, int max, String departmentCode);

    public List<City> findAllByDepartmentCodeOrderByNumberInhabitantsDesc(String departmentCode, Pageable pageable);
}
