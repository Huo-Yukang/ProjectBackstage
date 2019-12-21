package domain;

import java.io.Serializable;
import java.util.Set;

public class Shopping implements Comparable<Shopping>, Serializable
{
	
	private int id;
	public User user;
	public Food food;
	public Shopping(){
		super();
	}

	public Shopping(int id, User user, Food food) {
		this.id = id;
		this.user = user;
		this.food = food;
	}

	public Shopping(User user, Food food) {
		this.user = user;
		this.food = food;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	@Override
	public int compareTo(Shopping o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
}

