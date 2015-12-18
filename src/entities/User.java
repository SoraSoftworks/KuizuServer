package entities;

import java.sql.Timestamp;

import org.json.JSONObject;

public class User {

	int id;
	String phoneNumber;
	String userId;
	String emailAddress;
	String password;
	Timestamp joinDate;
	int elo;
	Timestamp lastLogin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Timestamp joinDate) {
		this.joinDate = joinDate;
	}

	public int getElo() {
		return elo;
	}

	public void setElo(int elo) {
		this.elo = elo;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public User(String phoneNumber, String userId, String emailAddress,
			String password) {
		super();
		this.phoneNumber = phoneNumber;
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", phoneNumber=" + phoneNumber + ", userId="
				+ userId + ", emailAddress=" + emailAddress + ", password="
				+ password + ", joinDate=" + joinDate + ", elo=" + elo
				+ ", lastLogin=" + lastLogin + "]";
	}

	public String toJSONString() {
		return "{\"id\": " + id + ", " + "\"userId\": \"" + userId + "\", "
				+ "\"email\": \"" + emailAddress + "\", " + "\"phone\": \""
				+ phoneNumber + "\", " + "\"elo\": " + elo + "}";
	}

	public JSONObject toJSONObject() {
		JSONObject jobj = new JSONObject();
		jobj.put("id", id);
		jobj.put("userId", userId);
		jobj.put("email", emailAddress);
		jobj.put("phone", phoneNumber);
		jobj.put("elo", elo);
		return jobj;
	}
}
