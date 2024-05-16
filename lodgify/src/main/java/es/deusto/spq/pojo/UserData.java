package es.deusto.spq.pojo;

/**
 * @class UserData
 * @brief Represents the data of a user.
 */
public class UserData {

    /** The login of the user. */
    private String login;

    /** The password of the user. */
    private String password;

    /**
     * Constructs a new UserData.
     * Required by serialization.
     */
    public UserData() {
    }

    /**
     * Gets the login of the user.
     * @return The login of the user.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Sets the login of the user.
     * @param login The login to set.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password of the user.
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the user.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the UserData object.
     * @return A string representation of the UserData object.
     */
    @Override
    public String toString() {
        return "[login=" + login + ", password=" + password + "]";
    }
}