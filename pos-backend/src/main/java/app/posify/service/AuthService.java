package app.posify.service;

import app.posify.exceptions.UserException;
import app.posify.payload.dto.UserDto;
import app.posify.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
