package com.github.andrewdnv.service.user.validation;

import com.github.andrewdnv.service.user.validation.validator.HasContactsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasContactsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasContacts {

    String message() default "User has no contacts";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}