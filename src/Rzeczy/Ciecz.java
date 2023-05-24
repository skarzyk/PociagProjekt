package Rzeczy;

public class Ciecz extends Ladunek{

    private String nazwa;
    private int poziomToksycznosci;

    public Ciecz(double masa, String kolor, String nazwa, int poziomToksycznosci){
        super(masa, kolor);
        this.nazwa = nazwa;
        this.poziomToksycznosci = poziomToksycznosci;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getPoziomToksycznosci() {
        return poziomToksycznosci;
    }

    @Override
    public String toString() {
        return "Ciecz: " + super.toString() + ", nazwa = '" + nazwa + '\'' + ", poziomToksycznosci = " + poziomToksycznosci;
    }
}
