package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

/**
 * The GameRecord class represents a game record entity to be stored in the datastore.
 * It is annotated with @Entity to specify the entity name as "GameRecords".
 */
@Entity(name = "GameRecords")
public class GameRecord {

    @Id
    private Long id;
    private int score;
    private String date;
    private String userId;

    /**
     * Constructs a new GameRecord with the specified userId, score, and date.
     *
     * @param userId The user identifier associated with the game record.
     * @param score  The score achieved in the game.
     * @param date   The date when the game record was created.
     */
    public GameRecord(String userId, int score, String date) {
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    /**
     * Gets the unique identifier of the game record.
     *
     * @return The id of the game record.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the game record.
     *
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user identifier associated with the game record.
     *
     * @return The userId of the game record.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user identifier associated with the game record.
     *
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the score achieved in the game.
     *
     * @return The score of the game record.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score achieved in the game.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the date when the game record was created.
     *
     * @return The date of the game record.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets the date when the game record was created.
     *
     * @param date The date to set.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns a string representation of the game record.
     *
     * @return A string containing the id, score, date, and userId of the game record.
     */
    @Override
    public String toString() {
        return "GameRecord{" +
                "id=" + id +
                ", score=" + score +
                ", date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}