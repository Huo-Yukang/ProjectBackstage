package domain;

import java.io.Serializable;

public class Business  implements Comparable<Business>, Serializable
{
	private int id;
	private String shopname;
	private String call_phone;
	private String address;
	private int balance;
	public Business(int id, String shopname, String call_phone, String address,int balance){
		this.id = id;
		this.shopname = shopname;
		this.call_phone = call_phone;
		this.address = address;
		this.balance = balance;
	}

	public Business(String shopname, String call_phone, String address,int balance){
		this.shopname = shopname;
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

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getCall_phone() {
		return call_phone;
	}

	public void setCall_phone(String call_phone) {
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




	@Override
	public int compareTo(Business o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
}

