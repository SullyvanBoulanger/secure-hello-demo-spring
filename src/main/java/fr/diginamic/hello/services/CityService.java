package fr.diginamic.hello.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.daos.CityDao;
import fr.diginamic.hello.dtos.CityCsvDto;
import fr.diginamic.hello.dtos.CityDtoFromFront;
import fr.diginamic.hello.entities.City;
import fr.diginamic.hello.repositories.CityRepository;
import fr.diginamic.hello.utils.CSVUtils;

@Service
public class CityService extends SuperService<Integer, City, CityDtoFromFront> {
    @Autowired
    private CityDao cityDao;

    @Autowired
    private CityRepository cityRepository;

    public City getByName(String name) {
        return cityDao.findByName(name);
    }

    public List<String> exportToCSV(int min) {
        List<CityCsvDto> cityCsvDtos = cityRepository.findByNumberInhabitantsGreaterThan(min).stream()
                .map(CityCsvDto::new)
                .toList();
        
        return CSVUtils.toCSV(cityCsvDtos, CityCsvDto.class);
    }
}
