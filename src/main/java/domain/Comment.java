package domain;

import java.io.Serializable;

public class Comment implements Comparable<Comment>, Serializable {
    private int id;
    private String description;
    public User user;

    public Comment(int id, String description, User user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }

    public Comment(String description, User user) {
        this.description = description;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Comment o) {
        // TODO Auto-generated method stub
        return this.id-o.id;
    }
}
