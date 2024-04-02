package es.deusto.spq.server.jdo;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Set;
import java.util.HashSet;

@PersistenceCapable
public class User {
	@PrimaryKey
	private int user_id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private int phone_number;
	private String email;	

	@Persistent(mappedBy="user", dependentElement="true")
	@Join
	Set<Message> messages = new HashSet<>();
	
	public User(int user_id, String username, String password, String name, String surname, int phone_number, String email) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.phone_number = phone_number;
		this.email = email;
	}


	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	
	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
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

	public Set<Message> getMessages() {return this.messages;}


	public String toString() {
        return "User: id --> " + this.user_id + "username --> " + this.username + ", password -->  " + this.password;
    }
}
