package dev.abhisek.productservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryValidator.class)
public @interface CategoryField {

    String message() default "Categories length must in between 10 to 100 characters and can not contain spaces";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
