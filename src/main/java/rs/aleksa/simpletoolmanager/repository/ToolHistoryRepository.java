package rs.aleksa.simpletoolmanager.repository;

import rs.aleksa.simpletoolmanager.model.ToolHistory;

import java.util.List;
import java.util.UUID;

public interface ToolHistoryRepository {
    List<ToolHistory> findByToolId(UUID toolId);
    ToolHistory save(ToolHistory history);
}
