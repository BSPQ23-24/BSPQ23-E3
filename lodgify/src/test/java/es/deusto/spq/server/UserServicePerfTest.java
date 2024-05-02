package es.deusto.spq.server;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import es.deusto.spq.server.jdo.User;

public class UserServicePerfTest {

    private WebTarget target;

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

    @Before
    public void setUp() {
        Client client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/rest/");
    }

    @Test
    @com.github.noconnor.junitperf.JUnitPerfTest(threads = 1, durationMs = 1000)
    public void testRegisterUserPerf() {
        User user = new User("newUser", "password", "Jane", "Doe", "987654321", "jane@example.com", "user", "987654321", 987654321, 1234, "456 Test St");

        Response response = target.path("register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        // Check for successful response
        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }

    /*@Test
    @com.github.noconnor.junitperf.JUnitPerfTest(threads = 1, durationMs = 30000)
    public void testLoginUserPerf() {
        // Simulate a login request with minimal data
        User user = new User("testUser", "password", "a", "a", "111111111", "iser@gmail.com", "user", "1", 0, 0, "as");

        Response response = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        // Check for successful response
        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
    }*/

}
