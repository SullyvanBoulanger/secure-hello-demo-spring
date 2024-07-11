package fr.diginamic.hello.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentApiGouvDto {
    private String code;
    private String nom;
    private String codeRegion;
}
