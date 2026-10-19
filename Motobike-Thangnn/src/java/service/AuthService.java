package service;

import dao.UserDAO;
import model.User;

/**
 * Service layer for Authentication:
 * Handles login validation before delegating to UserDAO.
 */
public class AuthService {

    private final UserDAO dao;

    public AuthService() {
        this.dao = new UserDAO();
    }

    public AuthService(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for login:
     * - Username cannot be null or empty.
     * - Password cannot be null or empty.
     * Returns the authenticated User on success, or null on validation/auth failure.
     */
    public User checkLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        return dao.checkLogin(username.trim(), password);
    }
}
