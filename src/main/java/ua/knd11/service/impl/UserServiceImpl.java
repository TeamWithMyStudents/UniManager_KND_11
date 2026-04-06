package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.UserService;
import ua.knd11.util.UserFileHandler;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static ua.knd11.util.FieldValidators.isNullOrBlank;

/**
 * Implementation of the UserService interface.
 * Provides user management functionality with authentication integration.
 */
public class UserServiceImpl implements UserService {
    private static final AuthService authService = new AuthServiceImpl();
    /**
     * In-memory repository for storing all user objects.
     * Shared across all service implementations.
     */
    protected static List<User> repository = new ArrayList<>();

    /**
     * Creates a new UserServiceImpl and initializes the repository.
     * Loads existing users from persistent storage if the repository is empty.
     */
    public UserServiceImpl() {
        if (repository.isEmpty()) repository.addAll(UserFileHandler.getSavedList());
    }

    /**
     * Adds a user to the repository after validation.
     * Registers the user through the authentication service and assigns an ID.
     *
     * @param user the user to be added
     * @return true if the user was added successfully, false otherwise
     * @throws IllegalArgumentException if the user is null or required fields are missing
     * @throws RuntimeException         if the user could not be added due to system errors
     */
    public boolean add(User user) {
        if (user == null || isNullOrBlank(user.getName(), "Name") || isNullOrBlank(user.getSurname(), "Surname")) {
            return false;
        }
        try {
            authService.registration(user);
            user.assignId();
            repository.add(user);
            return true;
        } catch (RuntimeException e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes a user from the repository by their unique ID.
     *
     * @param id the unique identifier of the user to be removed
     * @return true if the user was removed successfully, false if user not found
     */
    public boolean delete(int id) {
        return repository.removeIf(user -> user.getId() == id);
    }

    /**
     * Retrieves all users stored in the repository.
     *
     * @return a new list containing all users from the repository
     */
    public List<User> getAll() {
        return new ArrayList<>(repository);
    }

    /**
     * Searches for users by name and displays the results.
     * Uses case-insensitive exact matching.
     *
     * @param query the name string to search for
     * @throws IllegalArgumentException if the query is null
     */
    public void findByName(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getName())).forEach(System.out::println);
    }

    /**
     * Searches for users by surname and displays the results.
     * Uses case-insensitive exact matching.
     *
     * @param query the surname string to search for
     * @throws IllegalArgumentException if the query is null
     */
    public void findBySurname(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getSurname())).forEach(System.out::println);
    }

    /**
     * Sorts all users by surname using Ukrainian locale rules.
     * Uses Collator for proper Ukrainian text comparison.
     */
    public void sortBySurname() {
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        repository.sort(Comparator.comparing(User::getSurname, Comparator.nullsLast(uaCollator)));
    }

    @Override
    public String toString() {
        return "Users: \n" + repository.toString()
                .replace("[", "")
                .replace("]", "");
    }
}
