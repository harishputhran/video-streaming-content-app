package com.harish.streaming.content.validators;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
public @interface ValidateEnum {

    Class<? extends Enum> enumClass();

    String message() default "Invalid Value";

    String valueExpected() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
