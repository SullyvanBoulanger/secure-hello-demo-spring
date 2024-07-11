package fr.diginamic.hello.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDtoFromFront {
    @NotNull
    @Size(min = 2)
    private String name;

    @Min(10)
    private long numberInhabitants;

    @Size(min = 2, max = 2)
    private String departmentId;
}
