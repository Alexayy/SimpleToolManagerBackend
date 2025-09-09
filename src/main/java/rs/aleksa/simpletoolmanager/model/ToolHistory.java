package rs.aleksa.simpletoolmanager.model;

import com.google.cloud.firestore.annotation.DocumentId;
import java.time.Instant;
import java.util.UUID;

public class ToolHistory {
    @DocumentId
    private UUID id;
    private UUID toolId;
    private Instant when;
    private String who;
    private String action;
    private ToolStatus fromStatus;
    private ToolStatus toStatus;
    private String note;

    public ToolHistory() {
        this.id = UUID.randomUUID();
        this.when = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getToolId() {
        return toolId;
    }

    public void setToolId(UUID toolId) {
        this.toolId = toolId;
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ToolStatus getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(ToolStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public ToolStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(ToolStatus toStatus) {
        this.toStatus = toStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
