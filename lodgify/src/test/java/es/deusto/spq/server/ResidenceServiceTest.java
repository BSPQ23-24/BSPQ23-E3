package es.deusto.spq.server;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.jdo.Residence;

public class ResidenceServiceTest {

    private ResidenceService residenceService;
    private PersistenceManagerFactory pmf;
    private PersistenceManager pm;
    private Transaction tx;
    private Query<Residence> query;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        residenceService = new ResidenceService();

        pmf = mock(PersistenceManagerFactory.class);
        pm = mock(PersistenceManager.class);
        tx = mock(Transaction.class);
        query = mock(Query.class);

        when(pmf.getPersistenceManager()).thenReturn(pm);
        when(pm.currentTransaction()).thenReturn(tx);
    }

    @Test
    public void testRegisterUser() {
        Residence residence = new Residence("Some Address", "Apartment", 2, 1000, "user image", "user1");

        when(pm.newQuery(Residence.class, "id == :id")).thenReturn(query);
        when(query.execute(residence.getId())).thenReturn(null);

        Response response = residenceService.registerUser(residence);

        assert response.getStatus() == 200;
    }

    @Test
    public void testSearchResidences() {
        String address = "Some Address";

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(address)).thenReturn(new ArrayList<Residence>());

        Response response = residenceService.searchResidences(address);

        assert response.getStatus() == 200;
    }

    @Test
    public void testSearchResidencesID() {
        String userId = "user1";

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(userId)).thenReturn(new ArrayList<Residence>());

        Response response = residenceService.searchResidencesID(userId);

        assert response.getStatus() == 200;
    }

    @Test
    public void testGetResidenceByID() {
        Long residenceId = 1L;

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(new ArrayList<Residence>());

        Response response = residenceService.getResidenceByID(residenceId);

        assert response.getStatus() == 200;
    }

}
