package Rzeczy;

public class LadunekCiezki extends Ladunek{

    private boolean mechanizmDzwigajacy;
    private boolean systemZabepieczen;

    public LadunekCiezki(double masa, String kolor, boolean mechanizmDzwigajacy, boolean systemZabepieczen) {
        super(masa, kolor);
        this.mechanizmDzwigajacy = mechanizmDzwigajacy;
        this.systemZabepieczen = systemZabepieczen;
    }

    @Override
    public String toString() {
        return "LadunekCiezki " +
                "dzwig: " + mechanizmDzwigajacy + '\'' +
                ", systemZabepieczen: " + systemZabepieczen;
    }
}
