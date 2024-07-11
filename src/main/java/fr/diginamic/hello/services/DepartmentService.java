package fr.diginamic.hello.services;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import fr.diginamic.hello.daos.DepartmentDao;
import fr.diginamic.hello.dtos.CityPdfDto;
import fr.diginamic.hello.dtos.DepartmentDtoFromFront;
import fr.diginamic.hello.dtos.DepartmentPdfDto;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.entities.Department;
import fr.diginamic.hello.exceptions.BadRequestException;
import fr.diginamic.hello.repositories.DepartmentRepository;

@Service
public class DepartmentService extends SuperService<String, Department, DepartmentDtoFromFront> {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department getByName(String name) {
        return departmentDao.findByName(name);
    }

    public List<City> getBigCities(int id, int limit) {
        return departmentDao.getBigCities(id, limit);
    }

    public ByteArrayOutputStream generatePDFDocument(String code) throws BadRequestException {
        Department department = departmentRepository.findById(code).orElse(null);

        if (department == null)
            throw new BadRequestException("Le département n'existe pas");

        DepartmentPdfDto departmentPdfDto = new DepartmentPdfDto(department);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.addTitle(departmentPdfDto.getName());
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph departmentCodeParagraph = new Paragraph("Code : " + departmentPdfDto.getCode(), normalFont);
            document.add(departmentCodeParagraph);

            String citiesParagraphString = "Villes : \n"
                    + departmentPdfDto.getCities().stream()
                            .sorted(Comparator.comparing(CityPdfDto::getName))
                            .map(CityPdfDto::toString)
                            .collect(Collectors.joining("\n"));
            Paragraph departmentCitiesParagraph = new Paragraph(citiesParagraphString, normalFont);
            document.add(departmentCitiesParagraph);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream;
    }
}
