package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @class Residence
 * @brief Represents a residence entity in the system.
 */
@PersistenceCapable
public class Residence {

    /** The unique identifier for the residence. */
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    /** The address of the residence. */
    private String residence_address;

    /** The type of the residence. */
    private String residence_type;

    /** The number of rooms in the residence. */
    private int n_rooms;

    /** The price of the residence. */
    private float price;

    /** The image of the residence. */
    private String image;

    /** The username of the user who owns the residence. */
    private String user_username;

    /**
     * Constructs a new Residence with the specified details.
     * @param residence_address The address of the residence.
     * @param residence_type The type of the residence.
     * @param n_rooms The number of rooms in the residence.
     * @param price The price of the residence.
     * @param image The image of the residence.
     * @param user_username The username of the user who owns the residence.
     */
    public Residence(String residence_address, String residence_type, int n_rooms, float price, String image,
            String user_username) {
        this.residence_address = residence_address;
        this.residence_type = residence_type;
        this.n_rooms = n_rooms;
        this.price = price;
        this.image = image;
        this.user_username = user_username;
    }

    /**
     * Gets the unique identifier of the residence.
     * @return The unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the residence.
     * @param id The unique identifier.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the address of the residence.
     * @return The residence address.
     */
    public String getResidence_address() {
        return residence_address;
    }

    /**
     * Sets the address of the residence.
     * @param residence_address The residence address.
     */
    public void setResidence_address(String residence_address) {
        this.residence_address = residence_address;
    }

    /**
     * Gets the type of the residence.
     * @return The residence type.
     */
    public String getResidence_type() {
        return residence_type;
    }

    /**
     * Sets the type of the residence.
     * @param residence_type The residence type.
     */
    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
    }

    /**
     * Gets the number of rooms in the residence.
     * @return The number of rooms.
     */
    public int getN_rooms() {
        return n_rooms;
    }

    /**
     * Sets the number of rooms in the residence.
     * @param n_rooms The number of rooms.
     */
    public void setN_rooms(int n_rooms) {
        this.n_rooms = n_rooms;
    }

    /**
     * Gets the price of the residence.
     * @return The price.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the price of the residence.
     * @param price The price.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gets the image of the residence.
     * @return The image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image of the residence.
     * @param image The image.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the username of the user associated with the residence.
     * @return The user's username.
     */
    public String getUser_username() {
        return user_username;
    }

    /**
     * Sets the username of the user associated with the residence.
     * @param user_username The user's username.
     */
    public void setUser_id(String user_username) {
        this.user_username = user_username;
    }

    /**
     * Returns a string representation of the residence.
     * @return A string containing the residence details.
     */
    @Override
    public String toString() {
        return "Residence [residence_address=" + residence_address + ", n_rooms=" + n_rooms + ", price=" + price
                + ", user_username=" + user_username + "]";
    }

}
