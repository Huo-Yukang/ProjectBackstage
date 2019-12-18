package domain;

import java.io.Serializable;

public class Food  implements Comparable<Food>, Serializable
{
	private int id;
	private String foodno;
	private String foodname;
	private int price;
	private int total;
	public Food(int id, String foodno, String foodname, int price, int total){
		this.id = id;
		this.foodno = foodno;
		this.foodname = foodname;
		this.price = price;
		this.total = total;
	}
	public Food(String foodno, String foodname, int price, int total){
		this.foodno = foodno;
		this.foodname = foodname;
		this.price = price;
		this.total = total;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFoodno() {
		return foodno;
	}

	public void setFoodno(String foodno) {
		this.foodno = foodno;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public int compareTo(Food o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
}

