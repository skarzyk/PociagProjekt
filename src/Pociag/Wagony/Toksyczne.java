package Pociag.Wagony;

import Rzeczy.Ciecz;
import Rzeczy.LadunekCiezki;

public class Toksyczne extends Ciezkie<LadunekCiezki> {

    private int bezpiecznyPoziomToksycznosci;
    private int poziomNawietrzenia;
    private Ciecz ciecz;

    public Toksyczne(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int mocSilnika, String przewozonyTowar,
                     int bezpiecznyPoziomToksycznosci, Ciecz ciecz, int poziomNawietrzenia) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy, mocSilnika, przewozonyTowar);
        this.bezpiecznyPoziomToksycznosci = bezpiecznyPoziomToksycznosci;
        this.ciecz = ciecz;
        this.poziomNawietrzenia = poziomNawietrzenia;
    }

    public boolean sprawdzToksycznosc() {
        if (ciecz.getPoziomToksycznosci() > bezpiecznyPoziomToksycznosci) {
            System.out.println("Za wysoki poziom toksycznosci!!");
            return true;
        } else {
            System.out.println("Toksycznosc na odpowiednim poziomie");
            return false;
        }
    }

    public void sprawdzPoziomNawietrzenia() {
        if (sprawdzToksycznosc()){
            this.poziomNawietrzenia += poziomNawietrzenia * 0.05;
            System.out.println("Poziom nawietrzenia zwiekszony");
        } else{
            System.out.println("Poziom nawietrzenia jest odpowiedni");
        }
    }

    @Override
    public String toString() {
        return "Toksyczny " + super.toString() +
                ", bezpiecznyPoziomToksycznosci wynosi = " + bezpiecznyPoziomToksycznosci +
                ", poziomNawietrzenia = " + poziomNawietrzenia +
                ", substancja = " + ciecz;
    }
}


