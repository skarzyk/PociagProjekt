package Rzeczy;

public abstract class Ladunek {
    private double masa;
    private String kolor;

    public Ladunek(double masa, String kolor) {
        this.masa = masa;
        this.kolor = kolor;
    }

    public double getMasa() {
        return masa;
    }

    public String getKolor() {
        return kolor;
    }

    @Override
    public String toString() {
        return " masa = " + masa + ", kolor = " + kolor;
    }
}
