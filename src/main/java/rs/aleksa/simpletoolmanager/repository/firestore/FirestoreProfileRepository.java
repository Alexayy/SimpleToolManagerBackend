package rs.aleksa.simpletoolmanager.repository.firestore;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import rs.aleksa.simpletoolmanager.model.Profile;
import rs.aleksa.simpletoolmanager.repository.ProfileRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
@ConditionalOnBean(Firestore.class)
public class FirestoreProfileRepository implements ProfileRepository {

    private final CollectionReference collection;

    public FirestoreProfileRepository(Firestore firestore) {
        this.collection = firestore.collection("profiles");
    }

    @Override
    public Optional<Profile> findById(UUID userId) {
        try {
            DocumentSnapshot snapshot = collection.document(userId.toString()).get().get();
            if (snapshot.exists()) {
                return Optional.ofNullable(snapshot.toObject(Profile.class));
            }
            return Optional.empty();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error fetching profile", e);
        }
    }

    @Override
    public Profile save(Profile profile) {
        try {
            collection.document(profile.getUserId().toString()).set(profile).get();
            return profile;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error saving profile", e);
        }
    }
}
