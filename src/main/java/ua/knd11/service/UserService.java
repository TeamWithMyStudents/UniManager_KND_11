package ua.knd11.service;

import ua.knd11.model.User;

import java.util.List;

/**
 * Service interface for managing user operations.
 * Provides basic CRUD operations and search functionality for all user types.
 */
public interface UserService {
    /**
     * Adds a new user to the repository.
     * Validates the user, assigns an ID, and stores it in the system.
     *
     * @param u the user to be added
     * @return true if the user was added successfully, false otherwise
     * @throws IllegalArgumentException if the user is null or required fields are missing
     * @throws RuntimeException         if the user could not be added due to system errors
     */
    boolean add(User u);

    /**
     * Removes a user from the repository by their unique ID.
     *
     * @param id the unique identifier of the user to be removed
     * @return true if the user was removed successfully, false if user not found
     */
    boolean delete(int id);

    /**
     * Retrieves all users stored in the repository.
     *
     * @return a list containing all users in the system
     */
    List<User> getAll();

    /**
     * Searches for users by name and displays the results.
     * Uses partial matching and is case-insensitive.
     *
     * @param query the name string to search for
     * @throws IllegalArgumentException if the query is null or empty
     */
    @SuppressWarnings("unused")
    void findByName(String query);

    /**
     * Searches for users by surname and displays the results.
     * Uses partial matching and is case-insensitive.
     *
     * @param query the surname string to search for
     * @throws IllegalArgumentException if the query is null or empty
     */
    @SuppressWarnings("unused")
    void findBySurname(String query);

    /**
     * Sorts all users by surname using Ukrainian locale rules.
     * Displays the sorted list to the console.
     */
    @SuppressWarnings("unused")
    void sortBySurname();
}
