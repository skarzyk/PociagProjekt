package Pociag.Wagony;

import Interfejsy.Ciekle;
import Rzeczy.LadunekPodstawowy;

public class MaterialyCiekle extends Podstawowe<LadunekPodstawowy> implements Ciekle {

    private double objetosc;
    private String oznaczenia;
    private double gestosc;

    public MaterialyCiekle(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, double wysokosc, double szerokosc, double dlugosc, double zaladunek, double objetosc, String oznaczenia, double gestosc) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy, wysokosc, szerokosc, dlugosc, zaladunek);
        this.objetosc = objetosc;
        this.oznaczenia = oznaczenia;
        this.gestosc = gestosc;
    }

    @Override
    public void czySaDodatkoweZabezpieczenia() {
        System.out.println(this.oznaczenia);
    }

    @Override
    public void czyMaterialySaNiebepieczne() {
        System.out.println("Przewozone materialy sa bezpieczne");
    }


    @Override
    public String toString() {
        return "MaterialyCiekle " + super.toString() +
                ", objetosc = " + objetosc +
                ", oznaczenia = " + oznaczenia + '\'' +
                ", gestosc = " + gestosc;
    }

}

