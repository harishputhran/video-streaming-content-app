package com.harish.streaming.content.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidatorImpl implements ConstraintValidator<ValidateEnum, Object>{
    private ValidateEnum annotation;

    @Override
    public void initialize(ValidateEnum validEnum) {
      this.annotation = validEnum;
    }

    @Override
    public boolean isValid(Object valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        if(!(valueForValidation instanceof String)){
            return false;
        }
        boolean isEnumValueValid = false;
        String value = valueForValidation.toString();
        Object[] enumValues = this.annotation.getClass().getEnumConstants();

        if(enumValues != null){
            isEnumValueValid = Arrays.stream(enumValues)
                    .filter(enumValue -> StringUtils.equalsIgnoreCase(value, enumValue.toString()))
                    .findAny().isPresent();
        }

        return isEnumValueValid && StringUtils.equalsIgnoreCase(value, this.annotation.valueExpected());
    }
}
