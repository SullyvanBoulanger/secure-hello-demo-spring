package fr.diginamic.hello.controllers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.CityDtoForFront;
import fr.diginamic.hello.dtos.CityDtoFromFront;
import fr.diginamic.hello.dtos.mappers.CityDtoMapper;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;
import fr.diginamic.hello.exceptions.BadRequestException;
import fr.diginamic.hello.repositories.CityRepository;
import fr.diginamic.hello.repositories.DepartmentRepository;
import fr.diginamic.hello.services.CityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/villes")
public class CityController extends SuperController<Integer, City, CityDtoFromFront> {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityDtoMapper cityDtoMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private DepartmentRepository departmentRepository;

    public CityController() {
        super("La ville n'existe pas");
    }

    @GetMapping("/test/{id}")
    public CityDtoForFront geCityDto(@PathVariable int id) {
        return cityDtoMapper.toDto(cityRepository.findById(id).orElse(null));
    }

    @GetMapping("/csv")
    public ResponseEntity<byte[]> generateCsvFile(@RequestParam int min) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "cities_over_" + min + "_inhabitants.csv");

        List<String> lines = cityService.exportToCSV(min);
        String csvContent = String.join("\n", lines);
        byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @Override
    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody CityDtoFromFront dto, BindingResult result)
            throws BadRequestException {
        checkErrors(result);

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);
        if (department == null) {
            throw new BadRequestException("Le département associé n'existe pas");
        }
        boolean isDepartmentHasACityWithName = department.getCities().stream()
                .anyMatch(city -> city.getName().equals(dto.getName()));
        if (isDepartmentHasACityWithName) {
            throw new BadRequestException("Le département associé possède déjà une ville nommée " + dto.getName());
        }

        return ResponseEntity.ok(cityService.insertFromDto(dto));
    }

    @GetMapping("/nom")
    public ResponseEntity<?> getCitiesStartingWith(@RequestParam String name) {
        return ResponseEntity.ok(cityRepository.findByNameStartingWith(name));
    }

    @GetMapping("/superieur")
    public ResponseEntity<?> getCitiesWithInhabitantsGreaterThan(@RequestParam int min) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsGreaterThan(min));
    }

    @GetMapping("/entre")
    public ResponseEntity<?> getCitiesWithInhabitantsBetween(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsBetween(min, max));
    }

    @GetMapping("/departement/{code}/superieur")
    public ResponseEntity<?> getCitiesByDepartmentWithInhabitantsGreaterThan(@PathVariable String code,
            @RequestParam int min) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsGreaterThanAndDepartmentCode(min, code));
    }

    @GetMapping("/departement/{code}/entre")
    public ResponseEntity<?> getCitiesByDepartmentWithInhabitantsBetween(@PathVariable String code,
            @RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(cityRepository.findByNumberInhabitantsBetweenAndDepartmentCode(min, max, code));
    }

    @GetMapping("/departement/{code}/top")
    public ResponseEntity<?> getTopNCitiesFromDepartment(@PathVariable String code, @RequestParam int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return ResponseEntity.ok(cityRepository.findAllByDepartmentCodeOrderByNumberInhabitantsDesc(code, pageable));
    }
}
