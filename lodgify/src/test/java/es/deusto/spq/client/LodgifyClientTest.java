package es.deusto.spq.client;

import static org.junit.Assert.assertEquals;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LodgifyClientTest {

    private LodgifyClient client;

    @Before
    public void setUp() {
        client = new LodgifyClient("localhost", "8080");
    }

    @After
    public void tearDown() {
        client = null;
    }

    @Test
    public void testRegisterUser() {
        String username = "testuser";
        String password = "testpassword";
        String name = "Test";
        String surname = "User";
        String phone_number = "123456789";
        String email = "testuser@gmail.com";
        String user_type = "User";
        String id_card = "11111111M";
        int bank_account = 2;
        int social_SN = 2;
        String address = "111";

        // Realizamos la llamada al método que queremos probar
        Response response = client.registerUser(username, password, name, surname, phone_number, email, user_type, id_card, bank_account, social_SN, address);

        // Verificamos que la respuesta es OK (código 200)
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
