package domain;

import java.io.Serializable;

public class Recharge implements Comparable<Recharge>, Serializable {
    private int id;
    public int money;
    public Recharge(int id,int money) {
        this.id = id;
        this.money = money;
    }

    public Recharge(int money) {
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoney(){
        return this.money;
    }

    public void setUser(int money) {
        this.money = money;
    }

    @Override
    public int compareTo(Recharge o) {
        // TODO Auto-generated method stub
        return this.id-o.id;
    }
}
