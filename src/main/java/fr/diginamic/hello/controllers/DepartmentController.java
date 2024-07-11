package fr.diginamic.hello.controllers;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.dtos.DepartmentDtoForFront;
import fr.diginamic.hello.dtos.DepartmentDtoFromFront;
import fr.diginamic.hello.dtos.mappers.DepartmentDtoMapper;
import fr.diginamic.hello.entities.Department;
import fr.diginamic.hello.exceptions.BadRequestException;
import fr.diginamic.hello.services.DepartmentService;

@RestController
@RequestMapping("/departements")
public class DepartmentController extends SuperController<String, Department, DepartmentDtoFromFront> {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDtoMapper departmentDtoMapper;

    public DepartmentController() {
        super("Le département n'existe pas");
    }

    @GetMapping("/{id}/grandes-villes")
    public ResponseEntity<?> getBigCities(@PathVariable int id, @RequestParam int limit) {

        return ResponseEntity.ok(departmentService.getBigCities(id, limit));
    }

    @GetMapping("/test/{code}")
    public DepartmentDtoForFront getDepartmentDto(@PathVariable String code) {
        return departmentDtoMapper.toDto(departmentService.getById(code));
    }

    @GetMapping("/{code}/pdf")
    public ResponseEntity<byte[]> exportPdf(@PathVariable String code) throws BadRequestException {
        ByteArrayOutputStream pdfStream = departmentService.generatePDFDocument(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=departement" + code + ".pdf");
        headers.setContentLength(pdfStream.size());

        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }
}
