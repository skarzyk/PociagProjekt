package Pociag.Wagony;

import Exceptions.SameThing;

import java.util.ArrayList;
import java.util.List;

public class BagazowoPocztowy<Bagaz> extends Wagon {

    private int pojemnoscBagazowa;
    private List<Bagaz> bagaze;

    public BagazowoPocztowy(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int pojemnoscBagazowa) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy);
        this.pojemnoscBagazowa = pojemnoscBagazowa;
        bagaze = new ArrayList<>();
    }

    public void dodajBagaz(Bagaz bagaz) throws SameThing {
        if (bagaze.contains(bagaz) && !sprawdzCzyPelny()) {
            throw new SameThing();
        } else {
            bagaze.add(bagaz);
            pojemnoscBagazowa--;
        }
    }

    public boolean sprawdzCzyPelny() {
        if (pojemnoscBagazowa == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "BagazowoPocztowy " + super.toString() +
                ", pojemnoscBagazowa = " + pojemnoscBagazowa +
                ", bagaze = " + bagaze;
    }

    public int getPozostaleMiejscaBagazu() {
        return pojemnoscBagazowa;
    }

    public List<Bagaz> getListaBagaz() {
        return bagaze;
    }
}



