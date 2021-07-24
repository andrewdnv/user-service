package com.github.andrewdnv.service.user.controller;

import com.github.andrewdnv.service.user.exception.NotFoundException;
import com.github.andrewdnv.service.user.model.User;
import com.github.andrewdnv.service.user.service.api.UserService;
import com.github.andrewdnv.service.user.validation.group.PutValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@NotNull @Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@NotNull @Validated(PutValidation.class) @RequestBody User user) {
        User savedUser = userService.updateUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
        ConstraintViolationException.class,
        MethodArgumentNotValidException.class,
        MissingRequestValueException.class
    })
    public void handleBadRequestException(Exception ex) {
        log.error("Bad request: ", ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public void handleNotFoundException(NotFoundException ex) {
        log.error("Resource not found: ", ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Throwable.class)
    public void handleUnexpectedException(Throwable ex) {
        log.error("Unexpected exception: ", ex);
    }

}