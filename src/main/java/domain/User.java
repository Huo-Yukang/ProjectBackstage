package domain;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable
{

	private int id;
	private String username;
	private String name;
	private String password;
	private String call_phone;
	private String address;
	private int balance;
	public User(){
		super();
	}

	public User(int id, String username, int balance) {
		this.id = id;
		this.username = username;
		this.balance = balance;
	}

	public User(int id, String username, String name, String password, String call_phone, String address, int balance) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.call_phone = call_phone;
		this.address = address;
		this.balance = balance;
	}

	public User(String username, String name, String password, String call_phone, String address,int balance) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.call_phone = call_phone;
		this.address = address;
		this.balance = balance;
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
		return call_phone;
	}

	public void setCallphone(String call_phone) {
		this.call_phone = call_phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public void setBalance(int balance){
		this.balance = balance;
	}

	public int getBalance(){
		return this.balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
}

