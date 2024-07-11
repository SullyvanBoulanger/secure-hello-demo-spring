package fr.diginamic.hello.dtos;

import fr.diginamic.hello.entities.City;
import lombok.Getter;

@Getter
public class CityPdfDto {
    private String name;
    private long numberOfInhabitants;

    public CityPdfDto(City city) {
        this.name = city.getName();
        this.numberOfInhabitants = city.getNumberInhabitants();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Nom : ").append(name).append(", Population : ").append(numberOfInhabitants)
                .toString();
    }
}
