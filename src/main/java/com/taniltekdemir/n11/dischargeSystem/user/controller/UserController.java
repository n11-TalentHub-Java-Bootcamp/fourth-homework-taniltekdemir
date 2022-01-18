package com.taniltekdemir.n11.dischargeSystem.user.controller;

import com.taniltekdemir.n11.dischargeSystem.user.dto.UserDto;
import com.taniltekdemir.n11.dischargeSystem.user.dto.UserSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){

        List<UserDto> userDtos = userService.findAll();

        return ResponseEntity.ok(userDtos);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserSaveEntityDto userSaveEntityDto){

        UserDto userDto = userService.save(userSaveEntityDto);

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

}
