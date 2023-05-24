package Pociag.Wagony;

public class Ciezkie<LadunekCiezki> extends Wagon {

    private int mocSilnika;
    private String przewozonyTowar;

    public Ciezkie(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy, int mocSilnika,  String przewozonyTowar) {
        super(wagaNetto, wagaBrutto, nadawca, systemHamulcowy);
        this.mocSilnika = mocSilnika;
        this.przewozonyTowar = przewozonyTowar;
    }

    public void zwiekszMocSilnika(){
        this.mocSilnika += 100;
        System.out.println("Moc silnika zostala zwiekszona");
    }

    public double ileZostaloWolnegoMiejsca(){
        return getWagaBrutto() - getWagaNetto();
    }

    @Override
    public String toString() {
        return "Ciezki " + super.toString() +
                ", mocSilnika = " + mocSilnika +
                ", przewozonyTowar = " +  przewozonyTowar;
    }
}
