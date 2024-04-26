package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.server.jdo.Residence;

public class ResidenceServiceTest {

    private ResidenceService residenceService;

    @Mock
    private PersistenceManager persistenceManager;

    @Mock
    private Query<Residence> query;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initializing static mock object PersistenceManagerFactory
        try (MockedStatic<JDOHelper> jdoHelper = Mockito.mockStatic(JDOHelper.class)) {
            PersistenceManagerFactory pmf = mock(PersistenceManagerFactory.class);
            jdoHelper.when(() -> JDOHelper.getPersistenceManagerFactory("datanucleus.properties")).thenReturn(pmf);

            when(pmf.getPersistenceManager()).thenReturn(persistenceManager);

            // Instantiate tested object with mock dependencies
            residenceService = new ResidenceService();
        }
    }

    @Test
    public void testSearchResidences() {
        // Prepare mock query object to be returned by mock persistence manager
        @SuppressWarnings("unchecked")
        Query<Residence> query = mock(Query.class);
        when(persistenceManager.newQuery(Residence.class)).thenReturn(query);

        // Prepare response when execute method on mock Query object is called with expected parameters
        String address = "123 Main St";
        when(query.execute(address)).thenReturn(null);

        // Prepare mock transaction behavior
        Mockito.doNothing().when(persistenceManager).close();
        // Call tested method
        Response response = residenceService.searchResidences(address);

        // Verify that the filter is set with the correct value
        ArgumentCaptor<String> filterCaptor = ArgumentCaptor.forClass(String.class);
        verify(query).setFilter(filterCaptor.capture());
        assertEquals("residence_address == :address", filterCaptor.getValue());

        // Check expected response
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
        assertEquals("No residences found at the specified address.", response.getEntity());
    }
}