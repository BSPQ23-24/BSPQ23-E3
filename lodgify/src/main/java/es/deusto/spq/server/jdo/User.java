package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @class User
 * @brief Represents a user entity in the system.
 */
@PersistenceCapable
public class User {

	/** The unique identifier for the user. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	/** The username of the user. */
	private String username;

	/** The password of the user. */
	private String password;

	/** The name of the user. */
	private String name;

	/** The surname of the user. */
	private String surname;

	/** The phone number of the user. */
	private String phone_number;

	/** The email address of the user. */
	private String email;

	/** The type of user */
	private String user_type;

	/** The identification card number of the user. */
	private String id_card;

	/** The bank account number of the user. */
	private int bank_account;

	/** The social security number of the user. */
	private int social_SN;

	/** The address of the user. */
	private String address;

	/**
     * Constructs a new User with the specified details.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param phone_number The phone number of the user.
     * @param email The email address of the user.
     * @param user_type The type of the user.
     * @param id_card The identification card number of the user.
     * @param bank_account The bank account number of the user.
     * @param social_SN The social security number of the user.
     * @param address The address of the user.
     */
	public User(String username, String password, String name, String surname, String phone_number, String email,
			String user_type, String id_card, int bank_account, int social_SN, String address) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.phone_number = phone_number;
		this.email = email;
		this.user_type = user_type;
		this.id_card = id_card;
		this.bank_account = bank_account;
		this.social_SN = social_SN;
		this.address = address;
	}

	/**
     * Constructs a new User with the specified username, password, and user type.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param user_type The type of the user.
     */
	public User(String username, String password, String user_type) {
		this.username = username;
		this.password = password;
		this.user_type = user_type;
	}

	/**
     * Gets the unique identifier of the user.
     * @return The unique identifier.
     */
	public Long getId() {
		return id;
	}

	/**
     * Sets the unique identifier of the user.
     * @param id The unique identifier.
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Gets the username of the user.
     * @return The username.
     */
	public String getUsername() {
		return this.username;
	}

	/**
     * Sets the username of the user.
     * @param username The username.
     */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Gets the password of the user.
     * @return The password.
     */
	public String getPassword() {
		return this.password;
	}

	/**
     * Sets the password of the user.
     * @param password The password.
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * Gets the name of the user.
     * @return The name.
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the user.
     * @param name The name.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the surname of the user.
     * @return The surname.
     */
	public String getSurname() {
		return surname;
	}

	/**
     * Sets the surname of the user.
     * @param surname The surname.
     */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
     * Gets the phone number of the user.
     * @return The phone number.
     */
	public String getPhone_number() {
		return phone_number;
	}

	/**
     * Sets the phone number of the user.
     * @param phone_number The phone number.
     */
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	/**
     * Gets the email address of the user.
     * @return The email address.
     */
	public String getEmail() {
		return email;
	}

	/**
     * Sets the email address of the user.
     * @param email The email address.
     */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
     * Gets the type of the user.
     * @return The user type.
     */
	public String getUser_type() {
		return user_type;
	}

	/**
     * Sets the type of the user.
     * @param user_type The user type.
     */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	/**
     * Gets the identification card number of the user.
     * @return The identification card number.
     */
	public String getId_card() {
		return id_card;
	}

	/**
     * Sets the identification card number of the user.
     * @param id_card The identification card number.
     */
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	/**
     * Gets the bank account number of the user.
     * @return The bank account number.
     */
	public int getBank_account() {
		return bank_account;
	}

	/**
     * Sets the bank account number of the user.
     * @param bank_account The bank account number.
     */
	public void setBank_account(int bank_account) {
		this.bank_account = bank_account;
	}

	/**
     * Gets the social security number of the user.
     * @return The social security number.
     */
	public int getSocial_SN() {
		return social_SN;
	}

	/**
     * Sets the social security number of the user.
     * @param social_SN The social security number.
     */
	public void setSocial_SN(int social_SN) {
		this.social_SN = social_SN;
	}

	/**
     * Gets the address of the user.
     * @return The address.
     */
	public String getAddress() {
		return address;
	}

	/**
     * Sets the address of the user.
     * @param address The address.
     */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
     * Returns a string representation of the user.
     * @return A string containing the user's username and password.
     */
	@Override
	public String toString() {
		return "Username --> " + this.username + ", password -->  " + this.password;
	}
}
