package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.UserService;
import es.deusto.spq.server.helper.Hashing;
import es.deusto.spq.server.jdo.User;

import java.util.List;

public class UserServiceTest {

    private UserService userService;
    private PersistenceManagerFactory pmf;
    private PersistenceManager pm;
    private Transaction tx;

    @Before
    public void setUp() {
        userService = new UserService();
        pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
    }

    @After
    public void tearDown() {
        pm.close();
    }

    @Test
    public void testSayHello() {
        String response = userService.sayHello().getEntity().toString();
        assertEquals("Hello world!", response);
    }

    @Test
    public void testRegisterUser() {
        try {
            tx.begin();

            // Crear un nuevo usuario para registrar
            User user = new User("testuser", "testpassword", "Test", "User", "123456789", "test@example.com", "user", "123456789A", 1234567890, 123456789, "testAddress");

            // Registrar el usuario
            Response response = userService.registerUser(user);

            // Verificar que la respuesta es OK
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            // Buscar el usuario en la base de datos
            Query<User> query = pm.newQuery(User.class, "username == :username");
            List<User> users = (List<User>) query.execute(user.getUsername());

            if (users != null && !users.isEmpty() && users.get(0) instanceof User) {
                assertTrue(users.size() == 1);
                User retrievedUser = users.get(0);

                // Verificar que el usuario se registró correctamente
                assertNotNull(retrievedUser);
                assertEquals("testuser", retrievedUser.getUsername());
                // Agrega más verificaciones según tus necesidades
            } else {
                fail("La lista está vacía o no contiene instancias de User");
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción durante la prueba: " + e.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    // Agrega más pruebas para los otros métodos de UserService según sea necesario
}
