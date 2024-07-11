package fr.diginamic.hello.dtos.mappers;

import org.springframework.stereotype.Component;

import fr.diginamic.hello.dtos.DepartmentDtoForFront;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;

@Component
public class DepartmentDtoMapper {
    public DepartmentDtoForFront toDto(Department department) {
        DepartmentDtoForFront departmentDto = new DepartmentDtoForFront();
        departmentDto.setCode(department.getCode());
        departmentDto
                .setNumberOfInhabitants(department.getCities().stream().mapToLong(City::getNumberInhabitants).sum());

        return departmentDto;
    }
}
