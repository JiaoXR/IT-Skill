package com.jaxer.example.prototype;

/**
 * @author jaxer
 * date 10/04/2018
 */
public abstract class Shape implements Cloneable {
    private Integer id;
    protected String type;

    abstract void draw();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
