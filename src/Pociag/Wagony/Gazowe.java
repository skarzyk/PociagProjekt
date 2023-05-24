package Pociag.Wagony;

import Rzeczy.LadunekPodstawowy;

public class Gazowe extends Podstawowe<LadunekPodstawowy> {

    private double cisnienie;
    private double pojemnosc;

    public Gazowe(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, double wysokosc, double szerokosc, double dlugosc, double zaladunek, double cisnienie, double pojemnosc) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy, wysokosc, szerokosc, dlugosc, zaladunek);
        this.cisnienie = cisnienie;
        this.pojemnosc = pojemnosc;
    }

    public void wentylacja(){
        if(isGasDangerous()){
            System.out.println("Niebezpieczny gaz w wagonie");
        } else {
            System.out.println("Wagon nie zawiera niebezpiecznego gazu");
        }
    }

    public boolean isGasDangerous() {
        return false;
    }

    public void ustawCisnienie(){
        if(this.cisnienie > 40){
            this.cisnienie = 40;
        }
    }

    @Override
    public String toString() {
        return "Gazowy " + super.toString() +
                "cisnienia: " + cisnienie +
                " pojemnosc: " + pojemnosc;
    }
}
