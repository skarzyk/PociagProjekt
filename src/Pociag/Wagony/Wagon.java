package Pociag.Wagony;

public abstract class Wagon {

    private static int counter = 1;
    private int id = 0;
    private double wagaNetto;
    private double wagaBrutto;
    private String nadawca;
    protected boolean systemHamulcowy;

    public Wagon(double wagaNetto, double wagaBrutto, String nadawca, boolean systemHamulcowy) {
        this.wagaNetto = wagaNetto;
        this.wagaBrutto = wagaBrutto;
        this.nadawca = nadawca;
        this.systemHamulcowy = systemHamulcowy;
        this.id = counter;
        counter++;
    }

    public String getNadawca() {
        return nadawca;
    }

    public void wlaczHamulce() {
        if (!systemHamulcowy) {
            System.out.println("Hamulce zostały włączone.");
            systemHamulcowy = true;
        }
    }

    public void naprawSystemHamulcowy(){
        System.out.println("System hamulcowy został naprawiony.");
        systemHamulcowy = true;
    }

    public double getWagaBrutto() {
        return wagaBrutto;
    }

    public double getWagaNetto() {
        return wagaNetto;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Wagon" +
                " ma id = " + id +
                ", wagaNetto = " + wagaNetto +
                ", wagaBrutto = " + wagaBrutto +
                ", nadawca = " + nadawca +
                ", systemHamulcowy = " + systemHamulcowy;
    }

}
