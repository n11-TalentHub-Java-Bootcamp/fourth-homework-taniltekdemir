package com.taniltekdemir.n11.dischargeSystem.user.service;

import com.taniltekdemir.n11.dischargeSystem.user.dto.UserDto;
import com.taniltekdemir.n11.dischargeSystem.user.dto.UserSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import com.taniltekdemir.n11.dischargeSystem.user.mapper.UserMapper;
import com.taniltekdemir.n11.dischargeSystem.user.service.entityService.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserEntityService userEntityService;


    public List<UserDto> findAll() {
        List<User> userList = userEntityService.findAll();

        return UserMapper.INSTANCE.convertUserListToUserDtoList(userList);
    }

    public UserDto save(UserSaveEntityDto userSaveEntityDto) {

        User user = UserMapper.INSTANCE.convertUserSaveEntityDtoToUser(userSaveEntityDto);

        user = userEntityService.save(user);

        return UserMapper.INSTANCE.convertUserToUserDto(user);
    }

    public void delete(Long id){
        Optional<User> optionalUser = userEntityService.findById(id);

        User user;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new RuntimeException("User noot found!");
        }
        userEntityService.delete(user);
    }
}
