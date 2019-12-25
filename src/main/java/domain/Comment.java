package domain;

import java.io.Serializable;

public class Comment implements Comparable<Comment>, Serializable {
    private int id;
    private String description;

    public Comment(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Comment(String description) {
        this.description = description;
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


    @Override
    public int compareTo(Comment o) {
        // TODO Auto-generated method stub
        return this.id-o.id;
    }
}
