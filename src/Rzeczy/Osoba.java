package Rzeczy;

public class Osoba {
    private static int counter = 1;
    private int id;
    private String imie;
    private String nazwisko;
    private int wiek;

    public Osoba(String imie, String nazwisko, int wiek) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
        this.id = counter;
        counter++;
    }

    public String getImie() {
        return imie;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Osoba: " + "id = " + id + ", imie = " + imie + ", nazwisko = " + nazwisko + ", wiek = " + wiek;
    }
}
