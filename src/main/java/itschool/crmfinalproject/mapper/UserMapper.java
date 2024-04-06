package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.model.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO userDTO);
}