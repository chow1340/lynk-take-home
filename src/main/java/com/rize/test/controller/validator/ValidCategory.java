package com.rize.test.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategory {

    String message() default "Category must be one of ACTOR | PAINTER | SCULPTOR";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}