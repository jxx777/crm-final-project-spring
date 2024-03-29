package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.model.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDto(User user);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User userDtoToUser(UserDTO userDTO);
}