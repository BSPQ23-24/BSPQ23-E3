package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ResidenceTest {

    private Residence residence;

    @Before
    public void setUp() {
        residence = new Residence("Av Universidades 9", "House", 3, 200000.0f, "house.jpg", 1);
    }

    @Test
    public void testGetResidence_address() {
        assertEquals("Av Universidades 9", residence.getResidence_address());
    }

    @Test
    public void testSetResidence_address() {
        residence.setResidence_address("Av Universidades 23");
        assertEquals("Av Universidades 23", residence.getResidence_address());
    }

    @Test
    public void testGetResidence_type() {
        assertEquals("House", residence.getResidence_type());
    }

    @Test
    public void testSetResidence_type() {
        residence.setResidence_type("Apartment");
        assertEquals("Apartment", residence.getResidence_type());
    }

    @Test
    public void testGetN_rooms() {
        assertEquals(3, residence.getN_rooms());
    }

    @Test
    public void testSetN_rooms() {
        residence.setN_rooms(2);
        assertEquals(2, residence.getN_rooms());
    }

    @Test
    public void testGetPrice() {
        assertEquals(200000.0f, residence.getPrice(), 0.001);
    }

    @Test
    public void testSetPrice() {
        residence.setPrice(150000.0f);
        assertEquals(150000.0f, residence.getPrice(), 0.001);
    }

    @Test
    public void testGetImage() {
        assertEquals("house.jpg", residence.getImage());
    }

    @Test
    public void testSetImage() {
        residence.setImage("apartment.jpg");
        assertEquals("apartment.jpg", residence.getImage());
    }

    @Test
    public void testGetUser_id() {
        assertEquals(1, residence.getUser_id());
    }

    @Test
    public void testSetUser_id() {
        residence.setUser_id(2);
        assertEquals(2, residence.getUser_id());
    }

    @Test
    public void testToString() {
        String expected = "Residence [residence_address=Av Universidades 9, n_rooms=3, price=200000.0, user_id=1]";
        assertEquals(expected, residence.toString());
    }
}
