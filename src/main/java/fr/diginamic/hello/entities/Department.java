package fr.diginamic.hello.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    private String code;

    private String name;

    @OneToMany(mappedBy = "department")
    @JsonIgnoreProperties("department")
    private Set<City> cities;

    public void addCity(City city) {
        cities.add(city);
    }
}
