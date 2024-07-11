package fr.diginamic.hello.dtos.mappers;

import org.springframework.stereotype.Component;

import fr.diginamic.hello.dtos.CityDtoForFront;
import fr.diginamic.hello.entities.City;

@Component
public class CityDtoMapper {

    public CityDtoForFront toDto(City city) {
        CityDtoForFront cityDto = new CityDtoForFront();

        cityDto.setCode(city.getCode());
        cityDto.setDepartmentCode(city.getDepartment().getCode());
        cityDto.setNumberOfInhabitants(city.getNumberInhabitants());

        return cityDto;
    }
}
