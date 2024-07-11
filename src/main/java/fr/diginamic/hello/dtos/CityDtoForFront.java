package fr.diginamic.hello.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDtoForFront {
    private String code;
    private long numberOfInhabitants;
    private String departmentCode;
}
