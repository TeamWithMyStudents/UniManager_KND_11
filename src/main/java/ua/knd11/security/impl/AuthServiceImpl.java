package ua.knd11.security.impl;

import com.password4j.Hash;
import com.password4j.Password;
import ua.knd11.model.User;
import ua.knd11.security.AuthService;
import ua.knd11.security.UserSession;
import ua.knd11.util.FieldValidator;
import ua.knd11.util.UserFileHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementation of AuthService for user authentication and registration.
 */
public class AuthServiceImpl implements AuthService {
    private final static ArrayList<User> registeredUsers = new ArrayList<>();

    /**
     * Populates the in-memory registered users list from persistent storage when it is empty.
     * <p>
     * If the shared {@code registeredUsers} list contains no entries, loads saved users and adds them to it.
     */
    public AuthServiceImpl() {
        if (registeredUsers.isEmpty()) registeredUsers.addAll(UserFileHandler.getSavedList());
    }

    /**
     * Registers a new user: validates input, replaces the user's password with its hash,
     * persists the user to storage, and adds the user to the in-memory registry.
     *
     * @param user the user to register; must be non-null, must have an email not already present (case-insensitive), and will have its password replaced with the password's hash
     * @throws IllegalArgumentException if {@code user} is null or a user with the same email already exists
     * @throws IOException              if persisting the user to storage fails
     */
    public void registration(User user) throws IllegalArgumentException, IOException {
        if (user == null) throw new IllegalArgumentException("User must not be null");

        for (User existingUser : registeredUsers) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        if (!FieldValidator.isPasswordProtected(user.getPassword())) ProtectUserPassword(user);
        UserFileHandler.saveUsers(user);
        registeredUsers.add(user);
    }

    /**
     * Replaces the user's plain-text password with its hashed and protected counterpart.
     * This process ensures that the user's password is securely stored.
     *
     * @param user the user whose password needs to be protected; must be non-null and have a valid,
     *             non-empty plain-text password in its {@code password} field
     * @throws IllegalArgumentException if {@code user} is null or does not have a valid plain-text password
     */
    private void ProtectUserPassword(User user) throws IllegalArgumentException {
        if (FieldValidator.isPasswordProtected(user.getPassword()))
            throw new IllegalArgumentException("Password is already protected");
        String protectedPassword = makeProtectedPassword(user.getPassword());
        user.setProtectedPassword(protectedPassword);
    }

    /**
     * Transforms a plain-text password into a hashed and protected password string.
     * This method applies additional security measures such as salting, peppering,
     * and PBKDF2 hashing to the input password value.
     *
     * @param value the plain-text password to be transformed; must not be null
     * @return the resulting hashed and protected password string
     */
    private String makeProtectedPassword(String value) {
        //noinspection SpellCheckingInspection
        Hash password = Password.hash(value)
                .addPepper("Uni-hddjtf") // random characters
                .addSalt("Uni-fktjgyu") // random characters
                .withPBKDF2();
        return password.getResult();
    }

    /**
     * Authenticates a user by verifying the provided email and password.
     * If the email and password are valid and match an existing registered user,
     * the user is logged into the session.
     *
     * @param email the email address of the user attempting to log in; must not be null or empty and must be a valid email format
     * @param value the plain-text password of the user; must not be null or empty
     * @return the authenticated {@code User} object associated with the provided email if authentication is successful
     * @throws IllegalArgumentException if the email or password is invalid, no user with the email exists,
     *                                  the provided password is incorrect, or a user is already logged in
     */
    public User login(String email, String value) throws IllegalArgumentException {
        FieldValidator.validateEmail(email);
        FieldValidator.validatePassword(value);

        if (UserSession.isAuthenticated()) {
            throw new IllegalArgumentException("User is already logged in");
        }

        User foundUser = null;
        for (User user : registeredUsers) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                foundUser = user;
                break;
            }
        }

        String userPasswordGuess = makeProtectedPassword(value);
        if (foundUser == null || !foundUser.getPassword().equals(userPasswordGuess)) {
            throw new IllegalArgumentException("User with this email not found or incorrect password");
        }
        UserSession.login(foundUser);
        System.out.println("Login successful");
        return foundUser;
    }

    /**
     * Returns a defensive copy of the list of registered users.
     *
     * @return a new ArrayList containing all currently registered users
     */
    @SuppressWarnings("unused")
    public ArrayList<User> getRegisteredUsers() {
        return new ArrayList<>(registeredUsers);
    }

    /**
     * Removes a user with the specified unique identifier from the registered users list.
     *
     * @param id the unique identifier of the user to be removed
     * @throws IllegalArgumentException if no user with the specified id is found
     */
    public void removeUser(int id) throws IllegalArgumentException {
        if (registeredUsers.removeIf(user -> user.getId() == id))
            throw new IllegalArgumentException("User with this id not found");
    }
}
