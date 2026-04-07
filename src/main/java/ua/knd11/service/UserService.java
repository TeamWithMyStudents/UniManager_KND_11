package ua.knd11.service;

import ua.knd11.model.User;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Adds a user to the system.
     *
     * @param u the user to add
     * @return `true` if the user was added successfully, `false` otherwise
     */
    boolean add(User u);

    /**
     * Deletes the user with the specified id.
     *
     * @param id the user's identifier
     * @return `true` if a user with the id was deleted, `false` otherwise
     */
    boolean delete(int id);

    /**
     * Retrieve all users.
     *
     * @return a list of all User objects; empty list if no users exist
     */
    List<User> getAll();

    /**
     * Search for users whose given name matches the provided query.
     *
     * @param query the search string used to match a user's given name
     */
    @SuppressWarnings("unused")
    void findByName(String query);

    /**
     * Search for users whose surname matches the supplied query.
     *
     * @param query the surname or substring to match against user surnames
     */
    @SuppressWarnings("unused")
    void findBySurname(String query);

    /**
     * Sort by surname.
     */
    @SuppressWarnings("unused")
    void sortBySurname();
}
