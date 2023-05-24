package Rzeczy;

public class Bagaz extends Ladunek {

    private static int counter = 1;
    private int id;
    private Osoba wlasciciel;


    public Bagaz(double masa, String kolor, Osoba wlasciciel) {
        super(masa, kolor);
        this.wlasciciel = wlasciciel;
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Bagaz: " + "id = " + id + ", wlasciciel = " + wlasciciel;
    }
}
