package com.nghia.todolist.controller.auth;

import java.util.List;

import com.nghia.todolist.secure.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nghia.todolist.dto.BaseResponseDto;
import com.nghia.todolist.dto.request.user.UserRequestDto;
import com.nghia.todolist.dto.request.user.UserRequestRemove;
import com.nghia.todolist.dto.response.user.UserDtoNoPassword;
import com.nghia.todolist.exceptional.BadRequestException;
import com.nghia.todolist.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/api/v1/users/profile")
    public BaseResponseDto<UserDtoNoPassword> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDtoNoPassword userDto = userService.getUser(userDetails.getUsername());
        if(userDto == null) {
            throw new BadRequestException("User not found");
        }
        return BaseResponseDto.success(
                200, "User retrieved successfully", userDto, System.currentTimeMillis()
        );
    }

    @GetMapping("/api/v1/admin/allUsers")
    public BaseResponseDto<List<UserDtoNoPassword>> getAllUser() {
        return BaseResponseDto.success(
                200, "Successfully", userService.getAllUser(), System.currentTimeMillis()
        );
    }

    @DeleteMapping("/api/v1/users")
    public BaseResponseDto<String> removeUser(
        @RequestBody UserRequestRemove userRequestRemove) {
        
        boolean isRemoved = userService.removeUser(userRequestRemove);
        if(!isRemoved) {
            throw new BadRequestException("Cannot remove user");
        }
        return BaseResponseDto.success(
                200, "User removed successfully", null, System.currentTimeMillis()
        );
    }
}
