package Pociag.Wagony;

import Interfejsy.siecElektryczna;

public class Restauracja extends Wagon implements siecElektryczna {

    private int miejscaSiedzace;
    private String menu;
    private double mocElektryczna;

    public Restauracja(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int miejscaSiedzace, String menu, double mocElektryczna) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy);
        this.miejscaSiedzace = miejscaSiedzace;
        this.menu = menu;
        this.mocElektryczna = mocElektryczna;
    }

    public void zmienMenu(String newMenu) {
        this.menu = newMenu;
    }

    public void usadzKlienta() {
        if (miejscaSiedzace == 0) {
            System.out.println("Brak miejsc");
        } else {
            miejscaSiedzace--;
        }
    }

    @Override
    public void podlaczanieDoSieciElektrycznej() {
        System.out.println("Wagon restauracyjny zostal podlaczony do sieci");
    }

    @Override
    public double pobierzMocElektryczna() {
        return miejscaSiedzace * 0.7 + mocElektryczna;
    }

    public int getMiejscaSiedzace() {
        return miejscaSiedzace;
    }

    @Override
    public String toString() {
        return "Restauracyjny " + super.toString() +
                ", miejscSiedzacych = " + miejscaSiedzace +
                ", menu = " + menu;
    }
}
