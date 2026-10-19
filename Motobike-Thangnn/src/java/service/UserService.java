package service;

import dao.UserDAO;
import model.User;

/**
 * Service layer for User management:
 * Handles business validation rules before delegating to UserDAO.
 */
public class UserService {

    private final UserDAO dao;

    public UserService() {
        this.dao = new UserDAO();
    }

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for inserting a user:
     * - Username cannot be null or empty, cannot exceed 50 characters.
     * - Password cannot be null or empty.
     * - FullName cannot be null or empty, cannot exceed 100 characters.
     * - RoleID must be 1, 2, or 3.
     * - Username must not already exist.
     * Returns generated UserID (> 0) on success, or -1 on validation/DB error.
     */
    public int insertUser(User u) {
        if (u == null) {
            return -1;
        }
        if (u.getUsername() == null || u.getUsername().trim().isEmpty() || u.getUsername().length() > 50) {
            return -1;
        }
        if (u.getPassword() == null || u.getPassword().isEmpty()) {
            return -1;
        }
        if (u.getFullName() == null || u.getFullName().trim().isEmpty() || u.getFullName().length() > 100) {
            return -1;
        }
        if (u.getRoleID() < 1 || u.getRoleID() > 3) {
            return -1;
        }
        if (dao.usernameExists(u.getUsername().trim())) {
            return -1;
        }
        return dao.insertUser(u);
    }

    /**
     * Business validation for updating user profile:
     * - UserID must be > 0.
     * - FullName cannot be null or empty, cannot exceed 100 characters.
     * Returns true on success, false on validation/DB error.
     */
    public boolean updateUser(User u) {
        if (u == null || u.getUserID() <= 0) {
            return false;
        }
        if (u.getFullName() == null || u.getFullName().trim().isEmpty() || u.getFullName().length() > 100) {
            return false;
        }
        return dao.updateUser(u);
    }

    /**
     * Business validation for updating user role:
     * - UserID must be > 0.
     * - NewRoleID must be 1, 2, or 3.
     * Returns true on success, false on validation/DB error.
     */
    public boolean updateRole(int userID, int newRoleID) {
        if (userID <= 0) {
            return false;
        }
        if (newRoleID < 1 || newRoleID > 3) {
            return false;
        }
        return dao.updateRole(userID, newRoleID);
    }

    /**
     * Business validation for updating active status:
     * - UserID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean updateActiveStatus(int userID, boolean active) {
        if (userID <= 0) {
            return false;
        }
        return dao.updateActiveStatus(userID, active);
    }

    /**
     * Business validation for updating password:
     * - UserID must be > 0.
     * - NewPassword cannot be null or empty.
     * Returns true on success, false on validation/DB error.
     */
    public boolean updatePassword(int userID, String newPassword) {
        if (userID <= 0) {
            return false;
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        return dao.updatePassword(userID, newPassword);
    }

    /**
     * Business validation for deleting a user:
     * - UserID must be > 0.
     * Returns true on success, false on validation/DB error (e.g. FK constraint).
     */
    public boolean deleteUser(int userID) {
        if (userID <= 0) {
            return false;
        }
        return dao.deleteUser(userID);
    }
}
