package Gra;

import java.util.ArrayList;
import java.util.List;

public class Stacja {

    private static int counter = 1;
    private int id;
    private String nazwa;
    private List<Polaczenie> polaczenia;
    public Stacja(String nazwa) {
        polaczenia = new ArrayList<>();
        this.nazwa = nazwa;
        this.id = counter;
        counter++;
    }

    public void dodajPolaczenie(Polaczenie polaczenie){
        this.polaczenia.add(polaczenie);
    }

    public List<Polaczenie> getPolaczenia() {
        return polaczenia;
    }

    public int getId() {
        return id;
    }
    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return "Stacja: " + "id = " + id + ", nazwa = " + nazwa;
    }
}