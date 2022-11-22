package com.rize.test.controller.validator;

import com.rize.test.model.ArtistCategory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ArtistCategory result = null;

        for (ArtistCategory category : ArtistCategory.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                result = category;
                break;
            }
        }
        return result != null;
    }

}