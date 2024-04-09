package itschool.crmfinalproject.users.mapper;

import itschool.crmfinalproject.users.entity.User;
import itschool.crmfinalproject.users.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDto(User user);

    User toUser(UserDTO userDTO);
}