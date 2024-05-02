package es.deusto.spq.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

public class LodgifyClientPerfTest {

    private LodgifyClient lodgifyClient;
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "8080";

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

    @Before
    public void setUp() {
        lodgifyClient = new LodgifyClient(HOSTNAME, PORT);
    }

    @Test
    @com.github.noconnor.junitperf.JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testRegisterUserPerformance() {
        lodgifyClient.registerUser("test-login", "passwd", "test-name", "test-surname", "999999999",
                "test@example.com", "User", "123456789A", 123456789, 0, "test address");
    }

    @Test
    @com.github.noconnor.junitperf.JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testSaveBookingPerformance() {
        lodgifyClient.saveBooking("travelerUsername", "hostUsername", 1L, "startDate", "endDate");
    }
}