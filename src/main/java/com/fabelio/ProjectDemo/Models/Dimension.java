package com.fabelio.ProjectDemo.Models;

public class Dimension {
    private Integer length;
    private Integer width;
    private Integer height;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Dimension() {
        super();
    }

    public Dimension(Integer length, Integer width, Integer height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Integer[] toArray() {
        return new Integer[]{this.length, this.height, this.width};
    }
}
