package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.dto.RegisterUserRequest;
import com.siduska.ehealthwallet.dto.UpdateUserRequest;
import com.siduska.ehealthwallet.dto.UserDto;
import com.siduska.ehealthwallet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Users API")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserDto> getUserById(@Parameter(name = "User Id")
                                               @PathVariable(name = "id") Long id) {
        var user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "email already exists")
            );
        }
        var userDto = userService.createUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<?> updateUser(
            @Parameter(name = "User Id")
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
    ){
        var updatedUser = userService.updateUser(id, request);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> deleteUser(@Parameter(name = "User id")
                                           @PathVariable(name = "id") Long id) {
        var user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
