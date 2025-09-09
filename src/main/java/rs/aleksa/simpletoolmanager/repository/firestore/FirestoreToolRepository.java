package rs.aleksa.simpletoolmanager.repository.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import rs.aleksa.simpletoolmanager.model.Tool;
import rs.aleksa.simpletoolmanager.repository.ToolRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
@ConditionalOnBean(Firestore.class)
public class FirestoreToolRepository implements ToolRepository {

    private final CollectionReference collection;

    public FirestoreToolRepository(Firestore firestore) {
        this.collection = firestore.collection("tools");
    }

    @Override
    public List<Tool> findAll() {
        try {
            ApiFuture<QuerySnapshot> query = collection.get();
            return query.get().getDocuments().stream()
                    .map(doc -> doc.toObject(Tool.class))
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error fetching tools", e);
        }
    }

    @Override
    public Optional<Tool> findById(UUID id) {
        try {
            DocumentSnapshot snapshot = collection.document(id.toString()).get().get();
            if (snapshot.exists()) {
                return Optional.ofNullable(snapshot.toObject(Tool.class));
            }
            return Optional.empty();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error fetching tool", e);
        }
    }

    @Override
    public Tool save(Tool tool) {
        try {
            collection.document(tool.getId().toString()).set(tool).get();
            return tool;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error saving tool", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            collection.document(id.toString()).delete().get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error deleting tool", e);
        }
    }
}
