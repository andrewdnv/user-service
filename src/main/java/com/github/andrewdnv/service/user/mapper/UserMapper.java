package com.github.andrewdnv.service.user.mapper;

import com.github.andrewdnv.service.user.entity.UserDo;
import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.model.UserStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CommonMapper.class})
public interface UserMapper {

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "patronymicName", source = "patronymic")
    @Mapping(target = "dateOfBirth", source = "birthDate")
    @Mapping(target = "dateOfRegistration", source = "registrationDate")
    UserDo toUserDo(User user);

    @InheritInverseConfiguration
    User toUser(UserDo userDo);

    default String toString(UserStatus userStatus) {
        return userStatus.getValue();
    }

    default UserStatus toUserStatus(String value) {
        return UserStatus.fromValue(value);
    }

}