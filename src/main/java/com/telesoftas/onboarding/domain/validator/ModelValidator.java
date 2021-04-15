package com.telesoftas.onboarding.domain.validator;

import com.telesoftas.onboarding.domain.exception.ViolationException;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@AllArgsConstructor
public abstract class ModelValidator<E> {

    protected final Validator validator;


    public void validate(E requestModel, Class<?>... groups) throws ViolationException {
        Map<String, List<String>> errorMap = new HashMap<>();
        Set<ConstraintViolation<E>> errors = validator.validate(requestModel, groups);

        if (!errors.isEmpty()) {

            errors.forEach(e -> addToMap(e, errorMap));

            throw new ViolationException("There are some violations", errorMap);
        }

    }


    protected void validateValue(Class<E> type, String propertyName, Object value) throws ViolationException {
        Map<String, List<String>> errorMap = new HashMap<>();
        Set<ConstraintViolation<E>> errors = validator.validateValue(type, propertyName, value);

        if (!errors.isEmpty()) {

            errors.forEach(e -> addToMap(e, errorMap));

            throw new ViolationException("There are some violations", errorMap);
        }

    }


    private void addToMap(ConstraintViolation e, Map<String, List<String>> errorMap) {
        String parameterName = e.getPropertyPath().toString();
        String message = e.getMessage();

        List<String> errorList = (errorMap.containsKey(parameterName)) ? errorMap.get(parameterName) : new ArrayList<>();

        errorList.add(message);
        errorMap.put(parameterName, errorList);
    }
}
