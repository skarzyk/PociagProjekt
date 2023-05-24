package Pociag;

import Exceptions.RailroadHazard;
import Gra.Stacja;
import Pociag.Wagony.Wagon;

import java.util.List;

public class SkladPociagu implements Runnable{

    private Lokomotywa lokomotywa;
    private List<Wagon> wagony;
    private Stacja stacjaZrodlowa;
    private Stacja stacjaDocelowa;
    private double predkosc;
    private List<Stacja> trasa;

    public SkladPociagu(Lokomotywa lokomotywa, List<Wagon> wagony, Stacja stacjaZrodlowa, Stacja stacjaDocelowa){
        this.lokomotywa = lokomotywa;
        this.wagony = wagony;
        this.stacjaZrodlowa = stacjaZrodlowa;
        this.stacjaDocelowa = stacjaDocelowa;
        this.predkosc = lokomotywa.getPredkosc();
    }

    public Stacja getStacjaZrodlowa() {
        return stacjaZrodlowa;
    }

    public Stacja getStacjaDocelowa() {
        return stacjaDocelowa;
    }

    public void setTrasa(List<Stacja> trasa) {
        this.trasa = trasa;
    }

    public Lokomotywa getLokomotywa() {
        return lokomotywa;
    }

    public List<Wagon> getWagony() {
        return wagony;
    }

    @Override
    public String toString() {
        return "SkladPociagu: " + "lokomotywa: " + lokomotywa + ", wagony: " + wagony + ", stacjaZrodlowa: '" + stacjaZrodlowa + '\'' + ", stacjaDocelowa: '" + stacjaDocelowa;
    }

    @Override
    public void run() {
        while(true){
            for (int i = 0; i < trasa.size(); i++) {
                Stacja stacja = trasa.get(i);
                System.out.println("Pociag z " + this.lokomotywa + " zatrzymal sie w: " + stacja.getNazwa());
                try{
                    if(i == trasa.size() - 1){
                        Thread.sleep(30000);

                    } else {
                        Thread.sleep(2000);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
