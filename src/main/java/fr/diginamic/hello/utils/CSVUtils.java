package fr.diginamic.hello.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.diginamic.hello.annotations.CSV;

public class CSVUtils {
    public static List<String> toCSV(List<?> list, Class<?> clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        List<Field> filteredAndSortedFields = fields.stream()
               .filter(field -> field.isAnnotationPresent(CSV.class))
               .sorted(Comparator.comparing(field -> field.getAnnotation(CSV.class).order()))
               .collect(Collectors.toList());
    
        StringBuilder stringBuilder = new StringBuilder();
        filteredAndSortedFields.forEach(field -> stringBuilder.append(field.getAnnotation(CSV.class).columnName() + ";"));
    
        List<String> linesToWrite = new ArrayList<>();
        linesToWrite.add(stringBuilder.toString());
    
        list.forEach(object -> {
            StringBuilder lineBuilder = new StringBuilder();
            filteredAndSortedFields.forEach(field -> {
                field.setAccessible(true);
                try {
                    lineBuilder.append(field.get(object) + ";");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            linesToWrite.add(lineBuilder.toString());
        });

        return linesToWrite;
    }
}
