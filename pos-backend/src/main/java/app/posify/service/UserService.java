package app.posify.service;

import app.posify.exceptions.UserException;
import app.posify.modal.User;

import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserById(long id) throws UserException, Exception;
    List<User> getAllUsers();
}
