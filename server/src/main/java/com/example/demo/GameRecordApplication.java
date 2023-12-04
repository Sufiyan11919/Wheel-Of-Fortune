package com.example.demo;

import com.google.api.client.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

/**
 * The GameRecordApplication class is the main entry point for the Spring Shell application.
 * It provides shell commands for saving, loading, and removing game records.
 */
@ShellComponent
public class GameRecordApplication {

    @Autowired
    GameRecordRepository gameRecordRepository;

    /**
     * The main method that starts the Spring Shell application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(GameRecordApplication.class, args);
    }

    /**
     * Saves a game record to Cloud Datastore.
     *
     * @param userId The user identifier associated with the game record.
     * @param score  The score achieved in the game.
     * @param date   The date when the game record was created.
     * @return A string representation of the saved game record.
     */
    @ShellMethod("Saves a gameRecord to Cloud Datastore: save-gameRecord <userid> <score> <date>")
    public String saveGameRecord(String userId, int score, String date) {
        GameRecord savedGameRecord = this.gameRecordRepository.save(new GameRecord(userId, score, date));
        return savedGameRecord.toString();
    }

    /**
     * Loads all game records from Cloud Datastore.
     *
     * @return A string representation of all game records.
     */
    @ShellMethod("Loads all GameRecord")
    public String findAllGameRecord() {
        Iterable<GameRecord> gameRecords = this.gameRecordRepository.findAll();
        return Lists.newArrayList(gameRecords).toString();
    }

    /**
     * Loads game records by user identifier.
     *
     * @param userId The user identifier to search for.
     * @return A string representation of game records matching the specified user identifier.
     */
    @ShellMethod("Loads record by user: find-by-userID <userId>")
    public String findByUserId(String userId) {
        List<GameRecord> gameRecords = this.gameRecordRepository.findByUserId(userId);
        return gameRecords.toString();
    }

    /**
     * Removes all game records from Cloud Datastore.
     */
    @ShellMethod("Remove all records")
    public void removeAllGameRecords() {
        this.gameRecordRepository.deleteAll();
    }
}