package com.example.demo;

import com.google.api.client.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Shell component class for managing User entities through RESTful endpoints.
 */
@ShellComponent
public class UserApplication {

    @Autowired
    UserRepository userRepository;

    /**
     * Main method to run the UserApplication.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    /**
     * Saves a user to Cloud Datastore.
     *
     * @param userId The unique identifier for the user.
     * @param handle The user's handle or username.
     * @param email  The user's email address.
     * @param date   The date when the user entity was created.
     * @return A message indicating the success or failure of the operation.
     */
    @ShellMethod("Saves a user to Cloud Datastore: save-user <userid> <handle> <email> <date>")
    public String saveUser(String userId, String handle, String email, String date) {
        User savedUser = this.userRepository.save(new User(userId, handle, email, date));
        return savedUser.toString();
    }

    /**
     * Loads all User entities.
     *
     * @return A list of all User entities stored in the repository.
     */
    @ShellMethod("Loads all User entities")
    public String findAllUser() {
        Iterable<User> user = this.userRepository.findAll();
        return Lists.newArrayList(user).toString();
    }

    /**
     * Removes all users from the repository.
     */
    @ShellMethod("Remove all users")
    public void removeAllUser() {
        this.userRepository.deleteAll();
    }
}