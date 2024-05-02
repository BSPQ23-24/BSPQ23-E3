/*package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import javax.ws.rs.core.Response;

public class ResidenceServicePerfTest {

    private ResidenceService residenceService;

    @Before
    public void setUp() {
        residenceService = new ResidenceService();
    }

    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/residence_service_report.html"));

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    @JUnitPerfTestRequirement(averageLatency = 100)
    public void testRegisterUserPerformance() {
        Response response = residenceService.registerUser(createTestResidence());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 2000)
    @JUnitPerfTestRequirement(averageLatency = 50)
    public void testSearchResidencesPerformance() {
        Response response = residenceService.searchResidences("test_address");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}*/