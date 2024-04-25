package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import es.deusto.spq.server.jdo.Message;
import es.deusto.spq.server.jdo.User;
import java.lang.reflect.Field;

public class MessageTest {

    @Test
    public void testConstructorAndGetters() throws Exception {
        // Crear una instancia de Message
        Message message = new Message("Test message");

        // Obtener el valor del atributo text utilizando reflexión
        Field textField = Message.class.getDeclaredField("text");
        textField.setAccessible(true);
        String textValue = (String) textField.get(message);

        // Probar el constructor y el atributo text
        assertEquals("Test message", textValue);
    }

    @Test
    public void testSetUser() {
        // Crear una instancia de Message
        Message message = new Message("Test message");

        // Crear un usuario
        User user = new User("JohnDoe", "john@example.com");

        // Establecer el usuario en el mensaje
        message.setUser(user);

        // Verificar que el usuario se estableció correctamente
        assertEquals(user, message.getUser());
    }

    @Test
    public void testToString() {
        // Crear una instancia de Message
        Message message = new Message("Hello, world!");

        // Probar el método toString
        String expectedToString = "Message: message --> Hello, world";
        assertEquals(expectedToString, message.toString().substring(0, expectedToString.length()));
    }
}
