package Pociag.Wagony;

import Rzeczy.LadunekCiezki;

public class  Wybuchowe<Paczka> extends Ciezkie<LadunekCiezki> {

    private int iloscGasnic;
    private int wytrzymaloscLadunku;

    public Wybuchowe(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int mocSilnika, String przewozonyTowar, int iloscGasnic, int wytrzymaloscLadunku) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy, mocSilnika, przewozonyTowar);
        this.iloscGasnic = iloscGasnic;
        this.wytrzymaloscLadunku = wytrzymaloscLadunku;
    }

    public boolean czyIloscGasnicJestPoprawna() {
        if (iloscGasnic >= 4) {
            return true;
        } else {
            this.iloscGasnic++;
            return false;
        }
    }

    public void czasMagazynowania() {
        int WytrzymaloscTNT = 365;
        int WytrzymaloscC4 = 180;
        int WytrzymaloscNitrogliceryny = 90;
        int czas;
        if (this.wytrzymaloscLadunku > WytrzymaloscTNT) {
            System.out.println("Należy natychmiast rozładować ładunek!");
        } else if (this.wytrzymaloscLadunku > WytrzymaloscC4 && this.wytrzymaloscLadunku < WytrzymaloscTNT) {
            czas = WytrzymaloscTNT - this.wytrzymaloscLadunku;
            System.out.println("Masz " + czas + " dni by rozładować ładunek");
        } else if (this.wytrzymaloscLadunku > WytrzymaloscNitrogliceryny && this.wytrzymaloscLadunku < WytrzymaloscC4) {
            czas = WytrzymaloscC4 - this.wytrzymaloscLadunku;
            System.out.println("Masz " + czas + " dni by rozładować ładunek");
        } else {
            czas = WytrzymaloscNitrogliceryny - this.wytrzymaloscLadunku;
            System.out.println("Masz " + czas + " dni by rozładować ładunek");
        }
    }

    public int getWytrzymaloscLadunku() {
        return wytrzymaloscLadunku;
    }

    @Override
    public String toString() {
        return "Wybuchowe maja " + super.toString() +
                "gasnic=" + iloscGasnic +
                ", a dni do rozladunku" + wytrzymaloscLadunku;
    }
}










