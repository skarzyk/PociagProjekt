package Pociag.Wagony;

import Rzeczy.LadunekPodstawowy;
import Interfejsy.siecElektryczna;
import Rzeczy.Ladunek;

import java.util.Arrays;

public class Chlodniczy extends Podstawowe<LadunekPodstawowy> implements siecElektryczna {

    private int liczbaPalet;
    private double temperatura;
    private Ladunek[] ladunki;
    private double mocElektryczna;


    public Chlodniczy(double wagaNetto, double wagaBrutto, double wysokosc, double szerokosc, double dlugosc, double zaladunek, String nadawca, boolean systemHamulcowy, int liczbaPalet, double temperatura, double mocElektryczna) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy, wysokosc, szerokosc, dlugosc, zaladunek );
        this.liczbaPalet = liczbaPalet;
        this.temperatura = temperatura;
        this.mocElektryczna = mocElektryczna;
    }

    public boolean czyLadunekZnajdujeSiewWagonie(Ladunek ladunek){
        for(Ladunek c : ladunki){
            if(c.equals(ladunek)){
                return true;
            }
        }
        return false;
    }

    public void obnizTemeprature(){
        if(temperatura > 2){
            temperatura = 2;
        } else {
            System.out.println("Temperatura jest w normie i wynosi: " + this.temperatura);
        }
    }

    @Override
    public void podlaczanieDoSieciElektrycznej(){
        System.out.println("Wagon Chlodniczy zostal podlaczony do sieci elektrycznej");
    }

    @Override
    public double pobierzMocElektryczna() {
        return liczbaPalet * 0.5 + mocElektryczna;
    }

    public double getTemperatura() {
        return temperatura;
    }

    @Override
    public String toString() {
        return "Chlodniczy " + super.toString() +
                ", Palet = " + liczbaPalet +
                ", temperatura = " + temperatura +
                ", ladunki = " + Arrays.toString(ladunki);
    }
}
