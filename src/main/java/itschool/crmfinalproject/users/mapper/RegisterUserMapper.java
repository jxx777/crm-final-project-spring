package itschool.crmfinalproject.users.mapper;

import itschool.crmfinalproject.users.entity.User;
import itschool.crmfinalproject.authentification.model.RequestRegistrationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {
    User signUpToUser(RequestRegistrationDTO requestRegistrationDTO);
}