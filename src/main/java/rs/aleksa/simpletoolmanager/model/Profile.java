package rs.aleksa.simpletoolmanager.model;

import com.google.cloud.firestore.annotation.DocumentId;
import java.util.UUID;

public class Profile {
    @DocumentId
    private UUID userId;
    private String email;
    private UserRole role = UserRole.WORKER;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
