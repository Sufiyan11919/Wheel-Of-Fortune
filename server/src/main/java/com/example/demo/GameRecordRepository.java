package com.example.demo;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link GameRecord} entities in Cloud Datastore.
 */
public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {

    /**
     * Finds a list of GameRecord entities by user ID.
     *
     * @param userId The user ID to filter GameRecord entities.
     * @return A list of GameRecord entities with the given user ID.
     */
    List<GameRecord> findByUserId(String userId);

    /**
     * Finds a GameRecord entity by Google ID.
     *
     * @param googleId The Google ID to search for a GameRecord entity.
     * @return An {@link Optional} containing the GameRecord entity with the specified Google ID,
     *         or empty if not found.
     */
    Optional<GameRecord> findByGoogleId(String googleId);
}