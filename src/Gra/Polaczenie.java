package Gra;

import Pociag.SkladPociagu;

public class Polaczenie {
    private Stacja stacjaPoczatkowa;
    private Stacja stacjaKoncowa;
    private int odleglosc;
    public Polaczenie(Stacja stacjaPoczatkowa, Stacja stacjaKoncowa, int odleglosc) {
        this.stacjaPoczatkowa = stacjaPoczatkowa;
        this.stacjaKoncowa = stacjaKoncowa;
        this.odleglosc = odleglosc;
    }

    public Stacja getStacjaPoczatkowa() {
        return stacjaPoczatkowa;
    }

    public Stacja getStacjaKoncowa() {
        return stacjaKoncowa;
    }

    @Override
    public String toString() {
        return "Polaczenie: " + " stacjaPoczatkowa = " + stacjaPoczatkowa +
                ", stacjaKoncowa = " + stacjaKoncowa + ", odleglosc miedzy nimi = "  + odleglosc;
    }
}
