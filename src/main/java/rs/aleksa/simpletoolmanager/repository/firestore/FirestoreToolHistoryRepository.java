package rs.aleksa.simpletoolmanager.repository.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import rs.aleksa.simpletoolmanager.model.ToolHistory;
import rs.aleksa.simpletoolmanager.repository.ToolHistoryRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
@ConditionalOnBean(Firestore.class)
public class FirestoreToolHistoryRepository implements ToolHistoryRepository {

    private final CollectionReference collection;

    public FirestoreToolHistoryRepository(Firestore firestore) {
        this.collection = firestore.collection("tool_history");
    }

    @Override
    public List<ToolHistory> findByToolId(UUID toolId) {
        try {
            ApiFuture<QuerySnapshot> query = collection.whereEqualTo("toolId", toolId.toString()).get();
            return query.get().getDocuments().stream()
                    .map(doc -> doc.toObject(ToolHistory.class))
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error fetching history", e);
        }
    }

    @Override
    public ToolHistory save(ToolHistory history) {
        try {
            collection.document(history.getId().toString()).set(history).get();
            return history;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error saving history", e);
        }
    }
}
