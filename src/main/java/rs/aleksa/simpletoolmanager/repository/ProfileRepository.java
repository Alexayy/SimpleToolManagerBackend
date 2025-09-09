package rs.aleksa.simpletoolmanager.repository;

import rs.aleksa.simpletoolmanager.model.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {
    Optional<Profile> findById(UUID userId);
    Profile save(Profile profile);
}
