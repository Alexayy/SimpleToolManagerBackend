package rs.aleksa.simpletoolmanager.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import rs.aleksa.simpletoolmanager.model.auth.AuthUser;
import rs.aleksa.simpletoolmanager.model.UserRole;

@Service
@ConditionalOnBean(FirebaseAuth.class)
public class AuthService {

    private final FirebaseAuth firebaseAuth;

    public AuthService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public AuthUser verifyIdToken(String idToken) {
        try {
            FirebaseToken decoded = firebaseAuth.verifyIdToken(idToken);
            AuthUser user = new AuthUser();
            user.setId(decoded.getUid());
            user.setEmail(decoded.getEmail());

            // Default to the lowest privilege when the role claim is missing or invalid
            UserRole role = UserRole.WORKER;
            Object roleClaim = decoded.getClaims().get("role");
            if (roleClaim instanceof String roleStr) {
                try {
                    role = UserRole.valueOf(roleStr.toUpperCase());
                } catch (IllegalArgumentException ignore) {
                    // leave default role
                }
            }
            user.setRole(role);

            return user;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Invalid Firebase ID token", e);
        }
    }
}
