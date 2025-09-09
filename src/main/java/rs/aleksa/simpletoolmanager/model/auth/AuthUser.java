package rs.aleksa.simpletoolmanager.model.auth;

import rs.aleksa.simpletoolmanager.model.UserRole;

public class AuthUser {
    private String id;
    private String email;
    private UserRole role = UserRole.WORKER;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
