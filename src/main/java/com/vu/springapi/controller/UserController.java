package com.vu.springapi.controller;

import com.vu.springapi.dto.request.UserCreateRequest;
import com.vu.springapi.dto.request.UserUpdateRequest;
import com.vu.springapi.dto.response.ApiRespone;
import com.vu.springapi.model.User;
import com.vu.springapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiRespone<User> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest){
        ApiRespone<User> apiRespone = new ApiRespone<>();
        apiRespone.setResult(userService.createUser(userCreateRequest));
        return apiRespone;
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    User updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest){
        return userService.updateUser(id, userUpdateRequest);
    }
    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "user has been deleted!";
    }
}
