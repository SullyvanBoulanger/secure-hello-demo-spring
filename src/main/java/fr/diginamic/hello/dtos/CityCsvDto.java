package fr.diginamic.hello.dtos;

import fr.diginamic.hello.annotations.CSV;
import fr.diginamic.hello.entities.City;

public class CityCsvDto {
    @CSV(columnName = "Nom de la ville", order = 0)
    private String name;

    @CSV(columnName = "Nombre d'habitants", order = 1)
    private long numberInhabitants;

    @CSV(columnName = "Code département", order = 2)
    private String departmentCode;

    @CSV(columnName = "Nom du département", order = 3)
    private String departmentName;

    public CityCsvDto(City city) {
        this.name = city.getName();
        this.numberInhabitants = city.getNumberInhabitants();
        this.departmentCode = city.getDepartment().getCode();
        this.departmentName = city.getDepartment().getName();
    }
}
