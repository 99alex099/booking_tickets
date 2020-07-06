package dto.converters.impl;

import dto.UserDTO;
import dto.converters.interfaces.UserConverter;
import entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverterImpl extends ConverterImpl<User, UserDTO> implements UserConverter {

}
