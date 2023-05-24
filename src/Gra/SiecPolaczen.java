package Gra;

import java.util.*;

public class SiecPolaczen {

    private List<Stacja> stacje;
    private List<Polaczenie> polaczenia;

    public SiecPolaczen(){
        polaczenia = new ArrayList<>();
        stacje = new ArrayList<>();
    }
    public void dodajPolaczenie(Polaczenie polaczenie){
        polaczenia.add(polaczenie);
    }
    public void dodajStacje(Stacja stacja){
        stacje.add(stacja);
    }
    public List<Polaczenie> getPolaczenia(Stacja stacja){
        List<Polaczenie> polaczenia = new ArrayList<>();
        for (Polaczenie polaczenie : stacja.getPolaczenia()){
            if(polaczenie.getStacjaPoczatkowa() == stacja){
                polaczenia.add(polaczenie);
            }
        }
        return polaczenia;
    }
    public List<Stacja> wyznaczDroge(Stacja stacjaStartowa, Stacja stacjaKoncowa){
        Map<Stacja, Stacja> poprzedniaStacja = new HashMap<>();
        Queue<Stacja> queue = new LinkedList<>();

        queue.add(stacjaStartowa);

        while(!queue.isEmpty()){
            Stacja aktualnaStacja = queue.poll();

            if(aktualnaStacja == stacjaKoncowa){
                break;
            }

            for (Polaczenie polaczenie : getPolaczenia(aktualnaStacja)){
                Stacja sasiad = polaczenie.getStacjaKoncowa();
                if(!poprzedniaStacja.containsKey(sasiad)){
                    poprzedniaStacja.put(sasiad, aktualnaStacja);
                    queue.add(sasiad);
                }
            }
        }

        List<Stacja> trasa = new ArrayList<>();
        Stacja aktualnaStacja = stacjaKoncowa;

        while(aktualnaStacja != stacjaStartowa){
            trasa.add(aktualnaStacja);
            aktualnaStacja = poprzedniaStacja.get(aktualnaStacja);
        }

        trasa.add(stacjaStartowa);
        Collections.reverse(trasa);
        return trasa;
    }

    @Override
    public String toString() {
        return "SiecPolaczen " + ", stacje = " + stacje + "\n" + polaczenia;
    }
}
