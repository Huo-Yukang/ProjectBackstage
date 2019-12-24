package domain;

import java.io.Serializable;
import java.util.Set;

public class Administrator implements Comparable<Administrator>, Serializable
{
	private int id;
	private String admername;
	private String password;

	public Administrator(int id, String admername, String password) {
		this.id = id;
		this.admername = admername;
		this.password = password;
	}

	public Administrator(String admername, String passwor) {
		this.admername = admername;
		this.password = password;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdmername() {
		return admername;
	}

	public void setAdmername(String admername) {
		this.admername = admername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int compareTo(Administrator o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}


}

