package Pociag.Wagony;

public class Podstawowe<LadunekPodstawowy> extends Wagon {

    private double wysokosc;
    private double szerokosc;
    private double dlugosc;
    private double zaladunek;

    public Podstawowe(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, double wysokosc, double szerokosc, double dlugosc, double zaladunek) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy);
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        this.dlugosc = dlugosc;
        this.zaladunek = zaladunek; // od 0-1
    }

    public double obliczObjetosc() {
        return wysokosc * szerokosc * dlugosc;
    }

    public double obliczPojemnoscLadunkowa() {
        double objetosc = dlugosc * szerokosc * wysokosc;
        double wypelnione = objetosc * this.zaladunek;
        return objetosc - wypelnione;
    }

    public double getZaladunek() {
        return zaladunek;
    }

    @Override
    public String toString() {
        return "ma " + super.toString() + this.wysokosc + "m wysokosci, " + this.szerokosc + "m szerokosci i " + this.dlugosc + "m dlugosci";
    }
}
