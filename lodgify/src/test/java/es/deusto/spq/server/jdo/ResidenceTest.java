package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import es.deusto.spq.server.jdo.Residence;


public class ResidenceTest {

    @Test
    public void testGettersAndSetters() {
        // Crear una instancia de Residence
        Residence residence = new Residence("123 Main St", "House", 3, 200000.0f, "house.jpg", 1);

        // Probar los métodos getters
        assertEquals("123 Main St", residence.getResidence_address());
        assertEquals("House", residence.getResidence_type());
        assertEquals(3, residence.getN_rooms());
        assertEquals(200000.0f, residence.getPrice(), 0.001);
        assertEquals("house.jpg", residence.getImage());
        assertEquals(1, residence.getUser_id());

        // Probar los métodos setters
        residence.setResidence_address("456 Oak St");
        residence.setResidence_type("Apartment");
        residence.setN_rooms(2);
        residence.setPrice(150000.0f);
        residence.setImage("apartment.jpg");
        residence.setUser_id(2);

        // Verificar que los cambios se realizaron correctamente
        assertEquals("456 Oak St", residence.getResidence_address());
        assertEquals("Apartment", residence.getResidence_type());
        assertEquals(2, residence.getN_rooms());
        assertEquals(150000.0f, residence.getPrice(), 0.001);
        assertEquals("apartment.jpg", residence.getImage());
        assertEquals(2, residence.getUser_id());
    }

    @Test
    public void testToString() {
        // Crear una instancia de Residence
        Residence residence = new Residence("123 Main St", "House", 3, 200000.0f, "house.jpg", 1);

        // Probar el método toString
        String expectedToString = "Residence [residence_address=123 Main St, n_rooms=3, price=200000.0, user_id=1]";
        assertEquals(expectedToString, residence.toString());
    }
}

