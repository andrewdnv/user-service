package com.github.andrewdnv.service.user.validation.validator;

import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.validation.HasContacts;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HasContactsValidator implements ConstraintValidator<HasContacts, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext ctx) {
        return user.getMobilePhone() != null || user.getEmail() != null;
    }

}