package io.yamyamiya.telegram.bot.dto;

public class Temperature {
    private double value;
    private Grad grad;


    public Temperature(double value, Grad grad) {
        this.value = value;
        this.grad = grad;
    }

    public Temperature(double value) {
        this.value = value;
    }

    public Temperature() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public enum Grad{
        CELSIUS
    }


}
