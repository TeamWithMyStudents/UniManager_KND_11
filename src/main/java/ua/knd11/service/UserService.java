package ua.knd11.service;
import ua.knd11.model.User;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Add boolean.
     *
     * @param u the u
     * @return the boolean
     */
    boolean add(User u);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(int id);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<User> getAll();

    /**
     * Find by name.
     *
     * @param query the query
     */
    @SuppressWarnings("unused")
    void findByName(String query);

    /**
     * Find by surname.
     *
     * @param query the query
     */
    @SuppressWarnings("unused")
    void findBySurname(String query);

    /**
     * Sort by surname.
     */
    @SuppressWarnings("unused")
    void sortBySurname();
}
