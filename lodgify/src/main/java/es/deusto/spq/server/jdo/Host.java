package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Host extends User {
	
	private String id_card;
	private int bank_account;
	private int social_SN;
	private String address;
	
	public Host(int user_id, String username, String password, String name, String surname, int phone_number, String email, String id_card, int bank_account, int social_SN, String address) {
		super(user_id, username, password, name, surname, phone_number, email);
		this.id_card = id_card;
		this.bank_account = bank_account;
		this.social_SN = social_SN;
		this.address = address;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public int getBank_account() {
		return bank_account;
	}

	public void setBank_account(int bank_account) {
		this.bank_account = bank_account;
	}

	public int getSocial_SN() {
		return social_SN;
	}

	public void setSocial_SN(int social_SN) {
		this.social_SN = social_SN;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String toString() {
        return "Host: id --> " + this.id_card;
    }
}
