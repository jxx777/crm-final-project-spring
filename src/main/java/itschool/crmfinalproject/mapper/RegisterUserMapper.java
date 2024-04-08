package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.model.auth.RequestRegistrationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {
    User signUpToUser(RequestRegistrationDTO requestRegistrationDTO);
}