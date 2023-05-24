package Pociag;

import Exceptions.RailroadHazard;
import Watki.ZmienPredkosc;

public
    class Lokomotywa{

    private static int counter = 1;
    private int id = 0;
    private String nazwa;
    private int iloscWagonow;
    private double uciag;
    private int maxWagonSiec;
    private String stacjaMacierzysta;
    private double predkosc;
    private ZmienPredkosc zmienPredkosc;
    public Lokomotywa(String nazwa, int iloscWagonow, double uciag, int maxWagonSiec, String stacjaMacierzysta, double predkosc) {
         this.nazwa = nazwa;
         this.iloscWagonow = iloscWagonow;
         this.uciag = uciag;
         this.maxWagonSiec = maxWagonSiec;
         this.stacjaMacierzysta = stacjaMacierzysta;
         this.predkosc = predkosc;
         this.id = counter;
         counter++;
         this.zmienPredkosc = new ZmienPredkosc(this);
         zmienPredkosc.start();
    }

    public double getPredkosc(){
        return predkosc;
    }
    public int getid(){
         return id;
     }
    public double getUciag(){
         return uciag;
    }
    public int getIloscWagonow(){
         return iloscWagonow;
    }
    public void setPredkosc(double predkosc) {
        this.predkosc = predkosc;
    }

    public void setUciag(double uciag) {
        this.uciag = uciag;
    }

    public void czyPrzekroczonoPredkosc() throws RailroadHazard {
        if(this.predkosc > 200){
            throw new RailroadHazard();
        }
    }

    @Override
    public String toString() {
        return "Lokomotywa: " + "id = " + id + ", nazwa = " + nazwa + ", iloscWagonow = " + iloscWagonow + ", uciag = " + uciag +
                ", maxWagonSiec = " + maxWagonSiec + ", stacjaMacierzysta = '" + stacjaMacierzysta + '\'' + ", predkosc = " + predkosc;
    }

    public void posprzataj(){
         this.zmienPredkosc.interrupt();
    }
}
