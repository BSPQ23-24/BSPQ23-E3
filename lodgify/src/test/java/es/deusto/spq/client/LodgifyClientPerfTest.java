package es.deusto.spq.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

public class LodgifyClientPerfTest {

    private LodgifyClient lodgifyClient;
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "8080";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
    @com.github.noconnor.junitperf.JUnitPerfTest(threads = 5, durationMs = 5000, maxExecutionsPerSecond = 10)
    public void testSaveBookingPerformance() throws ParseException {
        Date startDate = DATE_FORMAT.parse("2024-05-22");
        Date endDate = DATE_FORMAT.parse("2024-05-25");
        lodgifyClient.saveBooking("travelerUsername", "hostUsername", 1L, startDate, endDate);
    }
}
