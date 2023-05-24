package Pociag.Wagony;

import Exceptions.SameThing;
import Interfejsy.siecElektryczna;

import java.util.ArrayList;
import java.util.List;

public class WagonPasazerski<Osoba> extends Wagon implements siecElektryczna {

    private int miejscaSiedzace;
    private List<Osoba> pasazerowie;
    private double mocElektryczna;

    public WagonPasazerski(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int miejscaSiedzace, double mocElektryczna) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy);
        this.miejscaSiedzace = miejscaSiedzace;
        this.mocElektryczna = mocElektryczna;
        pasazerowie = new ArrayList<>();
    }

    public void dodajPasazera(Osoba osoba) throws SameThing {
        if(pasazerowie.contains(osoba) && !sprawdzCzyPelny()){
                throw new SameThing();
        } else {
            pasazerowie.add(osoba);
            miejscaSiedzace--;
        }
    }

    @Override
    public void podlaczanieDoSieciElektrycznej(){
        System.out.println("Wagon pasazerski zostal podlaczony do sieci");
    }

    @Override
    public double pobierzMocElektryczna() {
        return miejscaSiedzace * 0.7 + mocElektryczna;
    }

    public boolean sprawdzCzyPelny() {
        if (miejscaSiedzace == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getPozostaleMiejscaSiedzace(){
        return miejscaSiedzace;
    }

    public List<Osoba> getListaPasazerow(){
        return pasazerowie;
    }

    @Override
    public String toString() {
        return "Pasazerski " + super.toString() +
                ", miejscaSiedzace = " + miejscaSiedzace +
                ", pasazerowie = " + pasazerowie;
    }
}
