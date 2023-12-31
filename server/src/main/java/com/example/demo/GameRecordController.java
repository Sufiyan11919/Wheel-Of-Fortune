package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Controller class for managing GameRecord entities through RESTful endpoints.
 */
@RestController
public class GameRecordController {

    private final GameRecordRepository gameRecordRepository;

    /**
     * Constructs a new GameRecordController with the specified GameRecordRepository.
     *
     * @param gameRecordRepository The repository for GameRecord entities.
     */
    public GameRecordController(GameRecordRepository gameRecordRepository) {
        this.gameRecordRepository = gameRecordRepository;
    }

    /**
     * Endpoint for saving a GameRecord entity.
     *
     * @param gameRecord The GameRecord entity to be saved.
     * @return A message indicating the success or failure of the operation.
     */
    @PostMapping("/saveGameRecord")
    @CrossOrigin(origins = "*")
    public String saveGameRecord(@RequestBody GameRecord gameRecord) {
        if (gameRecord == null) {
            return "The game record is invalid";
        }
        this.gameRecordRepository.save(gameRecord);
        return "success";
    }

    /**
     * Endpoint for retrieving all GameRecord entities.
     *
     * @return A list of all GameRecord entities stored in the repository.
     */
    @GetMapping("/findAllGameRecord")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<GameRecord> findAllGameRecord() {
        Iterable<GameRecord> gameRecords = this.gameRecordRepository.findAll();
        List<GameRecord> gameRecordList = new ArrayList<>();
        gameRecords.forEach(gameRecordList::add);
        return gameRecordList;
    }

    /**
     * Endpoint for retrieving GameRecord entities by user ID.
     *
     * @param userId The user ID to filter GameRecord entities.
     * @return A list of GameRecord entities with the given user ID.
     */
    @GetMapping("/findByUserId")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<GameRecord> findByUserId(@RequestParam String userId) {
        Iterable<GameRecord> gameRecords = this.gameRecordRepository.findByUserId(userId);
        List<GameRecord> gameRecordList = new ArrayList<>();
        gameRecords.forEach(gameRecordList::add);
        return gameRecordList;
    }

    /**
     * Endpoint for removing all GameRecord entities.
     *
     * @return A message indicating the success or failure of the operation.
     */
    @GetMapping("/removeAllGameRecords")
    @CrossOrigin(origins = "*")
    public String removeAllGameRecords() {
        this.gameRecordRepository.deleteAll();
        return "success";
    }

    /**
     * Endpoint for removing a GameRecord entity by ID.
     *
     * @param id The ID of the GameRecord entity to be removed.
     * @return A message indicating the success or failure of the operation.
     */
    @GetMapping("/removeGameRecordById")
    @CrossOrigin(origins = "*")
    public String removeGameRecordById(@RequestParam Long id) {
        this.gameRecordRepository.existsById(id);
        return "success";
    }

    /**
     * Endpoint for retrieving a GameRecord entity by Google ID.
     *
     * @param googleId The Google ID to search for a GameRecord entity.
     * @return The GameRecord entity with the specified Google ID, or null if not found.
     */
    @GetMapping("/findGameRecordByGoogleId")
    @CrossOrigin(origins = "*")
    public GameRecord findGameRecordByGoogleId(@RequestParam String googleId) {
        Optional<GameRecord> gameRecord = this.gameRecordRepository.findByGoogleId(googleId);
        if (gameRecord.isPresent()) {
            return gameRecord.get();
        }
        return null;
    }
}