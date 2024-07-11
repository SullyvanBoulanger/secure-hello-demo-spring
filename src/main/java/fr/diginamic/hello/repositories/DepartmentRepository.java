package fr.diginamic.hello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, String>{
    
}
