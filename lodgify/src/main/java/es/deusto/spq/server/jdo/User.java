package es.deusto.spq.server.jdo;

import javax.jdo.annotations.*;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String name;
	private String surname;
<<<<<<< Updated upstream
	private int phone_number;
	private String email;	

	@Persistent(mappedBy="user", dependentElement="true")
	@Join
	Set<Message> messages = new HashSet<>();
	
	public User(String username, String password, String name, String surname, int phone_number, String email) {
=======
	private String phone_number;
	private String email;
	private String user_type;
    private String id_card;
    private int bank_account;
    private int social_SN;
    private String address;	

	public User(String username, String password, String name, String surname, String phone_number, String email, String user_type, String id_card, int bank_account, int social_SN, String address) {
>>>>>>> Stashed changes
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public int getBank_account() {
		return bank_account;
	}

	public void setBank_account(int bank_account) {
		this.bank_account = bank_account;
	}

	public int getSocial_SN() {
		return social_SN;
	}

	public void setSocial_SN(int social_SN) {
		this.social_SN = social_SN;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	 
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public int getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(int phone_number) {
		this.phone_number = phone_number;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Message> getMessages() {
		return this.messages;
	}


	public String toString() {
        return "Username --> " + this.username + ", password -->  " + this.password;
    }
}
