package Rzeczy;

public class Paczka extends Ladunek{
    private static int counter = 1;
    private int id;
    private boolean isPriorytet;
    private int wartosc;

    public Paczka(double masa, String kolor, boolean isPriorytet, int wartosc) {
        super(masa, kolor);
        this.isPriorytet = isPriorytet;
        this.wartosc = wartosc;
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Paczka: " + "id = " + id + ", isPriorytet = " + isPriorytet + ", wartosc = " + wartosc;
    }
}
