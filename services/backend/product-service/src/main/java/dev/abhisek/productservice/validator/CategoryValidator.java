package dev.abhisek.productservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CategoryValidator implements ConstraintValidator<CategoryField, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        for (String s : value) {
            if (s.length() > 100 || s.length() < 5)
                return false;


            if (s.contains(" ")) {
                context.buildConstraintViolationWithTemplate("Category cannot contain spaces, try separating by '_'.");
                return false;
            }
        }
        return true;
    }
}
