package fr.diginamic.hello.dtos;

import java.util.List;

import fr.diginamic.hello.entities.Department;
import lombok.Getter;

@Getter
public class DepartmentPdfDto {
    private String name;
    private String code;
    private List<CityPdfDto> cities;
    
    public DepartmentPdfDto(Department department) {
        this.name = department.getName();
        this.code = department.getCode();
        this.cities = department.getCities().stream().map(CityPdfDto::new).toList();
    }
}
