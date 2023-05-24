package Pociag.Wagony;

import Exceptions.SameThing;
import Interfejsy.siecElektryczna;

import java.util.ArrayList;
import java.util.List;

public class Pocztowy<Paczka> extends Wagon implements siecElektryczna {

    private int liczbaWolnychSchowkow;
    private int pojemnikiNaListy;
    private List<Paczka> paczki;
    private double mocElektryczna;

    public Pocztowy(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int liczbaWolnychSchowkow, int pojemnikiNaListy, double mocElektryczna) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy);
        this.liczbaWolnychSchowkow = liczbaWolnychSchowkow;
        this.pojemnikiNaListy = pojemnikiNaListy;
        this.mocElektryczna = mocElektryczna;
        paczki = new ArrayList<>();
    }

    public void dodajPaczke(Paczka paczka) throws SameThing {
        if (paczki.contains(paczka)) {
            throw new SameThing();
        } else {
            paczki.add(paczka);
            liczbaWolnychSchowkow--;
        }
    }

    public void przerzucPaczkeDoListow(){
        pojemnikiNaListy--;
        liczbaWolnychSchowkow++;
    }

    @Override
    public void podlaczanieDoSieciElektrycznej() {
        System.out.println("Wagon pocztowy zostal podlaczony do sieci elektrycznej");
    }

    @Override
    public double pobierzMocElektryczna() {
        return pojemnikiNaListy * 0.3 + mocElektryczna;
    }

    public int getPojemnikiNaListy() {
        return pojemnikiNaListy;
    }

    public int getLiczbaWolnychSchowkow() {
        return liczbaWolnychSchowkow;
    }

    public List<Paczka> getListaPaczek(){
        return paczki;
    }

    @Override
    public String toString() {
        return "Pocztowy " + super.toString() +
                ", WolnychSchowkow =  " + liczbaWolnychSchowkow +
                ", pojemnikiNaListy =  " + pojemnikiNaListy +
                ", paczki = " + paczki;
    }

}




