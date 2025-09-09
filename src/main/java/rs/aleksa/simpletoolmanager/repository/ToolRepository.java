package rs.aleksa.simpletoolmanager.repository;

import rs.aleksa.simpletoolmanager.model.Tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToolRepository {
    List<Tool> findAll();
    Optional<Tool> findById(UUID id);
    Tool save(Tool tool);
    void delete(UUID id);
}
