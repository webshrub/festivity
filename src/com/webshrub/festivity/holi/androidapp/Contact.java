package com.webshrub.festivity.holi.androidapp;

public class Contact {
    private String name;
    private String number;
    private boolean selected;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
        selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name + "(" + number + ")";
    }
}