package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;

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
        when(query.execute(residence.getId())).thenReturn(new ArrayList<Residence>());

        Response response = residenceService.registerUser(residence);

        assert response.getStatus() == 200;
    }

    @Test
    public void testRegisterUser_NotAllDataFilled() {
        Residence residence = new Residence("", "Apartment", 0, 1000, "user image", "user1");

        Response response = residenceService.registerUser(residence);

        assert response.getStatus() == 400;
        assert response.getEntity().equals("Fill all the data!");
    }

    @Test
    public void testSearchResidences_NoResidencesFound() {
        String address = "Nonexistent Address";

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(address)).thenReturn(new ArrayList<>());

        Response response = residenceService.searchResidences(address);

        assert response.getStatus() == 404;
        assert response.getEntity().equals("No residences found at the specified address.");
    }

    @Test
    public void testSearchResidences_BlankAddress() {
        String address = "";

        Response response = residenceService.searchResidences(address);

        assert response.getStatus() == 400;
        assert response.getEntity().equals("Address query parameter is required");
    }

    /*
     * @Test
     * public void testSearchResidencesID() {
     * String userId = "user1";
     * 
     * when(pm.newQuery(Residence.class)).thenReturn(query);
     * when(query.execute(userId)).thenReturn(new ArrayList<Residence>());
     * 
     * Response response = residenceService.searchResidencesID(userId);
     * 
     * assert response.getStatus() == 200;
     * }
     */
    @Test
    public void testSearchResidencesID_NoResidencesFound() {
        String userId = "nonexistentUser";

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(userId)).thenReturn(new ArrayList<>());

        Response response = residenceService.searchResidencesID(userId);

        assert response.getStatus() == 404;
        assert response.getEntity().equals("No residences found for the specified user ID.");
    }

    @Test
    public void testSearchResidencesID_BlankUserID() {
        String userId = "";

        Response response = residenceService.searchResidencesID(userId);

        assert response.getStatus() == 400;
        assert response.getEntity().equals("User ID query parameter is required");
    }

    @Test
    public void testGetResidenceByID_ResidenceNotFound() {
        Long residenceId = 999L;

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(new ArrayList<>());

        Response response = residenceService.getResidenceByID(residenceId);

        assert response.getStatus() == 404;
        assert response.getEntity().equals("No residences found for the specified residence ID.");
    }

    @Test
    public void testGetResidenceByID_BlankResidenceID() {
        Long residenceId = null;

        Response response = residenceService.getResidenceByID(residenceId);

        assert response.getStatus() == 400;
        assert response.getEntity().equals("Residence ID query parameter is required");
    }

    @Test
    public void testDeleteResidence() {
        Long residenceId = 1L;
        Residence residenceToDelete = new Residence("Some Absurd Address", "Absurd Residence Type",
                9999, 9999, "absurd image", "absurd user");
        residenceToDelete.setId(residenceId);

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(Collections.singletonList(residenceToDelete));

        Response response = residenceService.deleteResidence(residenceId);

        assert response.getStatus() == 404;
        assert response.getEntity().equals("Residence not found.");

    }

    @Test
    public void testDeleteResidence_ResidenceNotFound() {
        Long residenceId = 999L;

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(Collections.emptyList());

        Response response = residenceService.deleteResidence(residenceId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Residence not found.", response.getEntity());
    }

    /*
     * @Test
     * public void testDeleteResidence_NoReservationsFound() {
     * Long residenceId = 1L;
     * Residence residenceToDelete = new Residence(null, null, 0, residenceId, null,
     * null);
     * residenceToDelete.setId(residenceId);
     * 
     * when(pm.newQuery(Residence.class)).thenReturn(query);
     * when(query.execute(residenceId)).thenReturn(Collections.singletonList(
     * residenceToDelete));
     * 
     * Response response = residenceService.deleteResidence(residenceId);
     * 
     * assert response.getStatus() == 404;
     * }
     */

    @Test
    public void testDeleteResidence_Exception() {
        Long residenceId = 1L;
        Residence residenceToDelete = new Residence(null, null, 0, residenceId, null, null);
        residenceToDelete.setId(residenceId);

        when(pm.newQuery(Residence.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(Collections.singletonList(residenceToDelete));

        Response response = residenceService.deleteResidence(residenceId);

        assert response.getStatus() == 404;
    }

}
