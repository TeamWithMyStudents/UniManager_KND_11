package ua.knd11.service.impl;

import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.impl.AuthServiceImpl;
import ua.knd11.service.UserService;
import ua.knd11.util.UserFileHandler;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * The type User service.
 */
public class UserServiceImpl implements UserService {
    private static final AuthService authService = new AuthServiceImpl();
    /**
     * Repository where saves all users
     */
    protected static List<User> repository = new ArrayList<>();

    /**
     * Initializes a new UserServiceImpl and populates the shared in-memory repository from storage if it is empty.
     *
     * <p>If the static repository contains no users, entries returned by {@code UserFileHandler.getSavedList()}
     * are added to it.</p>
     */
    public UserServiceImpl() {
        if (repository.isEmpty()) repository.addAll(UserFileHandler.getSavedList());
    }

    /**
     * Adds the specified user to the in-memory repository after registering the user and assigning an ID.
     *
     * @param user the user to register and persist; must not be {@code null}
     * @return {@code true} if the user was added to the repository, {@code false} otherwise
     * @throws NullPointerException if {@code user} is {@code null}
     */
    public boolean add(User user) throws NullPointerException {
        if (user == null) throw new NullPointerException("User must not be null");
        try {
            authService.registration(user);
            user.assignId();
            repository.add(user);
            return true;
        } catch (IllegalArgumentException | IOException | IllegalStateException e) {
            System.err.println("Failed to add user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove the user with the given id from the in-memory repository.
     * <p>
     * If a user is removed, the method also invokes the shared AuthService to remove the user from authentication state.
     *
     * @param id the identifier of the user to remove
     * @return true if a user with the given id was removed, false otherwise
     */
    public boolean delete(int id) {
        boolean removedFromRepository = repository.removeIf(user -> user.getId() == id);
        if (removedFromRepository) {
            authService.removeUser(id);
        }
        return removedFromRepository;
    }

    /**
     * Retrieve all users currently stored in the repository.
     *
     * @return a new List containing the repository's users (modifications to the returned list do not affect the internal repository)
     */
    public List<User> getAll() {
        return new ArrayList<>(repository);
    }

    /**
     * Prints users whose name equals the given query, using case-insensitive comparison.
     *
     * @param query the name to match (case-insensitive)
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
    public void findByName(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getName())).forEach(System.out::println);
    }

    /**
     * Prints users whose surname matches the given query, using a case-insensitive comparison.
     *
     * @param query surname to match (case-insensitive)
     * @throws IllegalArgumentException if {@code query} is null
     */
    public void findBySurname(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must not be null");
        }
        repository.stream().filter(user -> query.equalsIgnoreCase(user.getSurname())).forEach(System.out::println);
    }

    /**
     * Sorts the internal user repository in-place by users' surname using Ukrainian locale collation.
     * <p>
     * Comparison treats null surnames as greater than any non-null surname so users with null surnames are placed last.
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
