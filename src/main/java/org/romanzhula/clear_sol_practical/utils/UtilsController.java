package org.romanzhula.clear_sol_practical.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UtilsController {
    public static Map<String, String> getBindingErrors(BindingResult bindingResult) {

        Collector<FieldError, ?, Map<String, String>> collector =
                Collectors.toMap(
                    fieldError -> fieldError.getField() + "ERROR",
                    FieldError::getDefaultMessage
                )
        ;

        return bindingResult
                .getFieldErrors()
                .stream()
                .collect(collector)
        ;
    }
}
