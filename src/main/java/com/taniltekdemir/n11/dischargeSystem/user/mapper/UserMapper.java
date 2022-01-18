package com.taniltekdemir.n11.dischargeSystem.user.mapper;

import com.taniltekdemir.n11.dischargeSystem.user.dto.UserDto;
import com.taniltekdemir.n11.dischargeSystem.user.dto.UserSaveEntityDto;
import com.taniltekdemir.n11.dischargeSystem.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto convertUserToUserDto(User user);

    List<UserDto> convertUserListToUserDtoList(List<User> userList);

    User convertUserSaveEntityDtoToUser(UserSaveEntityDto userSaveEntityDto);
}
