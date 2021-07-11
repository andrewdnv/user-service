package com.github.andrewdnv.service.user.model;

import com.github.andrewdnv.service.user.validation.HasContacts;
import com.github.andrewdnv.service.user.validation.group.PutValidation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@HasContacts
@Accessors(chain = true)
public class User {

    @NotNull(groups = PutValidation.class)
    @Min(value = 1, groups = PutValidation.class)
    private Long userId;

    @NotNull
    @Length(min = 1, max = 255)
    private String firstName;

    @Length(min = 1, max = 255)
    private String patronymic;

    @NotNull
    @Length(min = 1, max = 255)
    private String lastName;

    private LocalDate birthDate;

    @NotNull
    private LocalDate registrationDate;

    @Length(min = 1, max = 255)
    private String city;

    @Pattern(regexp = "\\+?[0-9]{1,11}")
    private String mobilePhone;

    @Pattern(regexp = "[a-z0-9\\-]+@[a-z0-9\\-]+\\.[a-z0-9\\-]+")
    @Length(max = 127)
    private String email;

    @NotNull
    private UserStatus status;

}