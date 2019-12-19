package domain;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable
{

	private int id;
	private String username;
	private String password;
	private String callphone;
	private String address;
	public Shopping shopping;
	public User(){
		super();
	}


	public User(int id, String username, String password, String callphone, String address,Shopping shopping) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.callphone = callphone;
		this.address = address;
		this.shopping = shopping;
	}

	public User(String username, String password, String callphone, String address,Shopping shopping) {
		this.username = username;
		this.password = password;
		this.callphone = callphone;
		this.address = address;
		this.shopping = shopping;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCallphone() {
		return callphone;
	}

	public void setCallphone(String callphone) {
		this.callphone = callphone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Shopping getShopping() {
		return shopping;
	}

	public void setShopping(Shopping shopping) {
		this.shopping = shopping;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
}

