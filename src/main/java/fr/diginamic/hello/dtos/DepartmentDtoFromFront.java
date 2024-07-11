package fr.diginamic.hello.dtos;

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
public class DepartmentDtoFromFront {
    @Size(min = 2, max = 3)
    private String code;

    @NotNull
    @Size(min = 3)
    private String name;

    private int[] citiesIds;
}
