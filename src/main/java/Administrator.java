import java.io.Serializable;

public class Administrator implements Comparable<Administrator>, Serializable
{
	private int id;
	private String admername;
	private String password;
	public User user;
	public Business business;
	public Food food;
	public Administrator(int id, String admername, String password, User user, Business business, Food food){
		this.id = id;
		this.admername = admername;
		this.password = password;
		this.user = user;
		this.business = business;
		this.food = food;
	}
	public Administrator(String admername, String password, User user, Business business, Food food){
		this.admername = admername;
		this.password = password;
		this.user = user;
		this.business = business;
		this.food = food;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}


	@Override
	public int compareTo(Administrator o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}


}

