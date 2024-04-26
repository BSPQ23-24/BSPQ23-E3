package es.deusto.spq.server.jdo;

import javax.jdo.annotations.*;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Residence {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	private String residence_address;
    private String residence_type;
    private int n_rooms;
    private float price;
    private String image;
    private String user_username;

	public Residence(String residence_address, String residence_type, int n_rooms, float price, String image, String user_username) {
		this.residence_address = residence_address;
        this.residence_type = residence_type;
        this.n_rooms = n_rooms;
        this.price = price;
        this.image = image;
        this.user_username = user_username;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResidence_address() {
        return residence_address;
    }

    public void setResidence_address(String residence_address) {
        this.residence_address = residence_address;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
    }

    public int getN_rooms() {
        return n_rooms;
    }

    public void setN_rooms(int n_rooms) {
        this.n_rooms = n_rooms;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_id(String user_username) {
        this.user_username = user_username;
    }

    @Override
    public String toString() {
        return "Residence [residence_address=" + residence_address + ", n_rooms=" + n_rooms + ", price=" + price
                + ", user_id=" + user_username + "]";
    }

}
