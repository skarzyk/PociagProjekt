package Rzeczy;

public class LadunekPodstawowy extends Ladunek{

    private int iloscPalet;
    private double wartosc;

    public LadunekPodstawowy(double masa, String kolor, int iloscPalet, double wartosc) {
        super(masa, kolor);
        this.iloscPalet = iloscPalet;
        this.wartosc = wartosc;
    }

    public void sprawdzIloscPalet(){
        System.out.println(this.iloscPalet);
    }


    @Override
    public String toString() {
        return "LadunekPodstawowy " +
                ", iloscPalet = '" + iloscPalet + '\'' +
                ", jego wartosc = " + wartosc;
    }
}
