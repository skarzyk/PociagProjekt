package Pociag.Wagony;

import Interfejsy.Ciekle;
import Rzeczy.Ciecz;;

public class CiekleToksyczne extends Toksyczne implements Ciekle {

    private String systemyIdentyfikacyjne;

    private boolean czyJestSczelny;

    public CiekleToksyczne(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int mocSilnika, String przewozonyTowar, int bezpiecznyPoziomToksycznosci, Ciecz ciecz, int poziomNawietrzenia, String systemyIdentyfikacyjne, boolean czyJestSczelny) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy, mocSilnika, przewozonyTowar, bezpiecznyPoziomToksycznosci, ciecz, poziomNawietrzenia);
        this.systemyIdentyfikacyjne = systemyIdentyfikacyjne;
        this.czyJestSczelny = czyJestSczelny;
    }

    public void szczelnosc(){
       if(true) {
           System.out.println("Wagon jest szczelny");
       } else {
           System.out.println("Wagon jest nieszczelny");
       }
    }
    public void systemyIdentyfikacyjne(){
        if(true) {
            System.out.println("Sa systemy idnetyfikacyjne");
        } else {
            System.out.println("Nie ma systemow idetyfikacyjnych");
        }
    }


    @Override
    public void czySaDodatkoweZabezpieczenia() {
        System.out.println(this.systemyIdentyfikacyjne);
    }

    @Override
    public void czyMaterialySaNiebepieczne() {
        System.out.println("Przewozone materialy sa niebezpieczne");
    }
}
