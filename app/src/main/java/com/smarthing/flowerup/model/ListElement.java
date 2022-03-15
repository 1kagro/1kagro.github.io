package com.smarthing.flowerup.model;

public class ListElement {
    private String color;
    private String name;
    private String category;
    private String room;
    private float humedad;
    private float temp;
    private boolean sw_temp;
    private boolean sw_humedad;

    public ListElement(String name, String category, String room, boolean sw_temp, boolean sw_humedad) {
        this.setName(name);
        this.setCategory(category);
        this.setRoom(room);
        this.setSw_temp(sw_temp);
        this.setSw_humedad(sw_humedad);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public boolean isSw_temp() {
        return sw_temp;
    }

    public void setSw_temp(boolean sw_temp) {
        this.sw_temp = sw_temp;
    }

    public boolean isSw_humedad() {
        return sw_humedad;
    }

    public void setSw_humedad(boolean sw_humedad) {
        this.sw_humedad = sw_humedad;
    }
}
