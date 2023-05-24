package Gra;

import Exceptions.NieMoznaDodacWagonu;
import Exceptions.SameThing;
import Pociag.Lokomotywa;
import Pociag.SkladPociagu;
import Rzeczy.*;
import Pociag.Wagony.*;
import Watki.ZapisDoPliku;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private List<Lokomotywa> lokomotywaList = symulacjaLokomotyw();
    private List<Wagon> wagons = symulacjaWagony();
    private List<Stacja> stacje = symulacjaStacji();
    private List<Polaczenie> polaczenia = symulacjaPolaczen();
    private SiecPolaczen siecPolaczen = symulacjaSiecPolaczen();
    private List<SkladPociagu> skladPociagow = symulacjaPociag();
    private List<Osoba> osoby = new ArrayList<>();
    private List<Bagaz> bagaze = new ArrayList<>();
    private List<Paczka> paczki = new ArrayList<>();
    private List<Ciecz> ciecze = new ArrayList<>();
    private List<LadunekPodstawowy> ladunkiPodstawowe = new ArrayList<>();
    private List<LadunekCiezki> ladunkiCiezkie = new ArrayList<>();
    private ZapisDoPliku zapisDoPliku;

    public Menu() {
        while (true) {
            this.zapisDoPliku = new ZapisDoPliku(skladPociagow);
            zapisDoPliku.start();

            System.out.println("1. Dodaj Pojazd lub Stacje: ");
            System.out.println("2. Przypisz wagon do pociagu: ");
            System.out.println("3. Dodaj Rzecz: ");
            System.out.println("4. Przypisz Rzecz: ");
            System.out.println("5. Wyswietl: ");
            System.out.println("6. Usun: ");
            System.out.println("7. Zakoncz program");
            Scanner scanner = new Scanner(System.in);
            int chooseOption = scanner.nextInt();
            switch (chooseOption) {
                case 1 -> {
                    dodajPojazdLubStacje();
                }
                case 2 -> {
                    try {
                        przypiszWagonDoPociagu();
                    } catch (NieMoznaDodacWagonu e) {
                        System.out.println("Nie mozna przypisac wagonu do tego pociagu");
                    }
                }
                case 3 -> {
                    dodajRzecz();
                }
                case 4 -> {
                    przypiszRzecz();
                }
                case 5 -> {
                    wyswietl();
                }
                case 6 -> {
                    usun();
                }
                case 7 -> {
                    System.exit(0);
                }
                default -> System.out.println("Niepoprawna opcja, podaj inny numer");
            }
        }
    }

    private void dodajPojazdLubStacje() {
        System.out.println("a) Dodaj Pociag");
        System.out.println("b) Dodaj lokomotywe");
        System.out.println("c) Dodaj wagon");
        System.out.println("d) Dodaj stacje kolejowa");
        System.out.println("e) Dodaj polaczenie miedzy stacjami");
        Scanner scanner = new Scanner(System.in);
        char chooseOption = scanner.next().charAt(0);
        switch (chooseOption) {
            case 'a' -> {
                dodajPociag();
            }
            case 'b' -> {
                dodajLokomotywe();
            }
            case 'c' -> {
                dodajWagon();
            }
            case 'd' -> {
                dodajStacje();
            }
            case 'e' -> {
                dodajPolaczenie();
            }
            default -> {
                System.out.println("Powrot...");
            }
        }
    }

    private void dodajPociag() {
        Scanner scanner = new Scanner(System.in);
        if (!lokomotywaList.isEmpty()) {
            System.out.println("Wybierz z jakiej lokomotywy ma zostac stworzony pociag: (Podaj id)");
            wyswietlWszystkieLokomotywy();
            int nrId = scanner.nextInt();
            boolean znalezionoLokomotywe = false;
            for (int i = 0; i < lokomotywaList.size(); i++) {
                if (lokomotywaList.get(i).getid() == nrId) {
                    znalezionoLokomotywe = true;
                    System.out.println("Wybierz stacje startowa i koncowa: (Podaj id po enterze)");
                    wyswietlWszystkieStacje();
                    int nrId1 = 0;
                    int nrId2 = 0;
                    try {
                        nrId1 = scanner.nextInt();
                        nrId2 = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Wprowadzono zly format danych");
                        scanner.nextLine();
                    }
                    boolean znaleziono1 = false;
                    boolean znaleziono2 = false;
                    Stacja stacja1 = null;
                    Stacja stacja2 = null;
                    for (int j = 0; j < stacje.size(); j++) {
                        if (stacje.get(j).getId() == nrId1) {
                            znaleziono1 = true;
                            stacja1 = stacje.get(j);
                        }
                        if (stacje.get(j).getId() == nrId2) {
                            znaleziono2 = true;
                            stacja2 = stacje.get(j);
                        }
                    }

                    if (znaleziono1 && znaleziono2) {
                            SkladPociagu skladPociaguTmp = new SkladPociagu(lokomotywaList.get(i), new ArrayList<>(), stacja1, stacja2);
                            skladPociagow.add(skladPociaguTmp);
                            System.out.println("Stworzono Pociag: ");
                            System.out.println(skladPociaguTmp);
                            List<Stacja> trasa = siecPolaczen.wyznaczDroge(stacja1, stacja2);
                            System.out.println(trasa);
                            skladPociaguTmp.setTrasa(trasa);
                            Thread t = new Thread(skladPociaguTmp);
                            t.start();
                    }
                    if (!znaleziono1 || !znaleziono2) {
                        System.out.println("Nie istnieja takie stacje");
                        System.out.println("1. Wybierz inne stacje do Pociagu: ");
                        System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania pociagu: ");
                        try {
                            int chooseOption = scanner.nextInt();
                            if (chooseOption == 1) {
                                dodajPociag();
                            }
                        } catch (InputMismatchException ignored) {

                        }
                    }
                }
            }
            if (!znalezionoLokomotywe) {
                System.out.println("Nie ma takiej lokomotywy w systemie");
                System.out.println("1. Wybierz inna lokomotywe: ");
                System.out.println("2. Dodaj lokomotywe: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        dodajPociag();
                    } else if (chooseOption == 2) {
                        dodajLokomotywe();
                        dodajPociag();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakiejkolwiek lokomotywy w systemie, nie da sie stworzyc pociagu");
            System.out.println("Chcesz dodac lokomotywe do systemu ?");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajLokomotywe();
                    dodajPociag();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void dodajLokomotywe() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[6];
        System.out.println("Podaj:");
        System.out.print("nazwe lokomotywy: ");
        tab[0] = scanner.next();
        System.out.print("maksymalna liczbe wagonow: ");
        tab[1] = scanner.next();
        System.out.print("maksymalny uciag lokomotywy: ");
        tab[2] = scanner.next();
        System.out.print("maksymalna liczbe wagonow wymagajacych podlaczenia do sieci elektrycznej: ");
        tab[3] = scanner.next();
        System.out.print("stacje macierzysta: ");
        tab[4] = scanner.next();
        System.out.print("predkosc poczatkowa: ");
        tab[5] = scanner.next();
        lokomotywaList.add(new Lokomotywa(tab[0], Integer.parseInt(tab[1]), Double.parseDouble(tab[2]), Integer.parseInt(tab[3]), tab[4], Double.parseDouble(tab[5])));
        System.out.println("Dodana lokomotywa: ");
        System.out.println(lokomotywaList.get(lokomotywaList.size() - 1));
    }

    private void dodajWagon() {
        System.out.println("Wybierz wagon ktory chcesz dodac: ");
        System.out.println("1. Wagon Pasażerski");
        System.out.println("2. Wagon Pocztowy");
        System.out.println("3. Wagon Bagazowo-Pocztowy");
        System.out.println("4. Wagon Restauracyjny");
        System.out.println("5. Wagon Towarowy Podstawowy");
        System.out.println("6. Wagon Towarowy Ciężki");
        System.out.println("7. Wagon Chlodniczy");
        System.out.println("8. Wagon na Materialy Ciekle");
        System.out.println("9. Wagon na Materialy Gazowe");
        System.out.println("10. Wagon na Materialy Wybuchowe");
        System.out.println("11.Wagon na Materialy Toksyczne");
        System.out.println("12.Wagon na Ciekle Materialy Toksyczne");
        Scanner scanner = new Scanner(System.in);
        int chooseOption = scanner.nextInt();
        switch (chooseOption) {
            case 1 -> {
                dodajWagonPasazerski();
            }
            case 2 -> {
                dodajWagonPocztowy();
            }
            case 3 -> {
                dodajWagonBagazowoPocztowy();
            }
            case 4 -> {
                dodajWagonRestauracyjny();
            }
            case 5 -> {
                dodajWagonTowarowyPodstawowy();
            }
            case 6 -> {
                dodajWagonTowarowyCiezki();
            }
            case 7 -> {
                dodajWagonChlodniczy();
            }
            case 8 -> {
                dodajWagonNaMaterialyCiekle();
            }
            case 9 -> {
                dodajWagonNaMaterialyGazowe();
            }
            case 10 -> {
                dodajWagonNaMaterialyWybuchowe();
            }
            case 11 -> {
                dodajWagonNaMaterialyToksyczne();
            }
            case 12 -> {
                dodajWagonNaCiekleMaterialyToksyczne();
            }
        }
    }

    private void dodajWagonPasazerski() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[6];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy wagon ma system hamulcowy ? ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Ile miejsc siedzących ma wagon: ");
        tab[4] = scanner.next();
        System.out.println("Jaka ma moc elektryczna: ");
        tab[5] = scanner.next();
        Wagon wagonTmp = new WagonPasazerski<Osoba>(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), Double.parseDouble(tab[5]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonPocztowy() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[7];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy istnieje system hamulcowy w wagonie? : ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Liczbe Wolnych Schowkow: ");
        tab[4] = scanner.next();
        System.out.print("Liczbe Pojemnikow na Listy: ");
        tab[5] = scanner.next();
        System.out.println("Jaka ma moc elektryczna: ");
        tab[6] = scanner.next();
        Wagon wagonTmp = new Pocztowy<Paczka>(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), Integer.parseInt(tab[5]), Double.parseDouble(tab[6]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonBagazowoPocztowy() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[5];
        System.out.println("Podaj: ");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy istnieje system hamulcowy w wagonie? : ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Pojemnosc bagazowa: ");
        tab[4] = scanner.next();
        Wagon wagonTmp = new BagazowoPocztowy<Bagaz>(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]));
        wagons.add(wagonTmp);
        System.out.println("Stworzono: ");
        System.out.println(wagonTmp);
    }

    private void dodajWagonRestauracyjny() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[7];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy istnieje system hamulcowy w wagonie? : ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Ilosc miejsc siedzacych: ");
        tab[4] = scanner.next();
        System.out.print("Menu: ");
        tab[5] = scanner.next();
        System.out.println("Jaka ma moc elektryczna: ");
        tab[6] = scanner.next();
        Wagon wagonTmp = new Restauracja(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), tab[5], Double.parseDouble(tab[6]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonTowarowyPodstawowy() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[8];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy istnieje system hamulcowy w wagonie? : ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Wysokosc wagonu: ");
        tab[4] = scanner.next();
        System.out.print("Szerokosc wagonu: ");
        tab[5] = scanner.next();
        System.out.println("Dlugosc wagonu: ");
        tab[6] = scanner.next();
        System.out.println("Procent zaladowania wagonu: ");
        tab[7] = scanner.next();
        Wagon wagonTmp = new Podstawowe<LadunekPodstawowy>(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Double.parseDouble(tab[4]), Double.parseDouble(tab[5]), Double.parseDouble(tab[6]), Double.parseDouble(tab[7]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonTowarowyCiezki() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[6];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy wagon ma system hamulcowy ? ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Moc silnika: ");
        tab[4] = scanner.next();
        System.out.println("Przewozony towar: ");
        tab[5] = scanner.next();
        Wagon wagonTmp = new Ciezkie<LadunekCiezki>(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), tab[5]);
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonChlodniczy() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[11];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Wysokosc wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Szerokosc wagonu: ");
        tab[3] = scanner.next();
        System.out.print("Dlugosc wagonu: ");
        tab[4] = scanner.next();
        System.out.print("Procent zaladowanego zaladunku: ");
        tab[5] = scanner.next();
        System.out.print("Nadawce: ");
        tab[6] = scanner.next();
        System.out.print("Czy istnieje system hamulcowy w wagonie? : ");
        tab[7] = scanner.next();
        if (tab[7].equals("tak")) tab[3] = "true";
        else if (tab[7].equals("nie")) tab[3] = "false";
        System.out.print("Ilosc palet: ");
        tab[8] = scanner.next();
        System.out.print("Temperature: ");
        tab[9] = scanner.next();
        System.out.println("Moc elektryczna sieci: ");
        tab[10] = scanner.next();
        Wagon wagonTmp = new Chlodniczy(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), Double.parseDouble(tab[2]), Double.parseDouble(tab[3]), Double.parseDouble(tab[4]), Double.parseDouble(tab[5]), tab[6], Boolean.parseBoolean(tab[7]), Integer.parseInt(tab[8]), Double.parseDouble(tab[9]), Double.parseDouble(tab[10]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonNaMaterialyCiekle() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[11];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy wagon ma system hamulcowy ? ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Wysokosc wagonu: ");
        tab[4] = scanner.next();
        System.out.println("Szerokosc wagonu: ");
        tab[5] = scanner.next();
        System.out.println("Dlugosc wagonu: ");
        tab[6] = scanner.next();
        System.out.print("Procent zaladowania wagonu: ");
        tab[7] = scanner.next();
        System.out.print("Objetosc wagonu: ");
        tab[8] = scanner.next();
        System.out.print("Dodatkowe oznaczenia wagonu: ");
        tab[9] = scanner.next();
        System.out.println("Gestosc materialu cieklego: ");
        tab[10] = scanner.next();
        Wagon wagonTmp = new MaterialyCiekle(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Double.parseDouble(tab[4]), Double.parseDouble(tab[5]), Double.parseDouble(tab[6]), Double.parseDouble(tab[7]), Double.parseDouble(tab[8]), tab[9], Double.parseDouble(tab[10]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonNaMaterialyGazowe() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[10];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy wagon ma system hamulcowy ? ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Wysokosc wagonu: ");
        tab[4] = scanner.next();
        System.out.println("Szerokosc wagonu: ");
        tab[5] = scanner.next();
        System.out.println("Dlugosc wagonu: ");
        tab[6] = scanner.next();
        System.out.print("Procent zaladowania wagonu: ");
        tab[7] = scanner.next();
        System.out.print("Cisnienie gazu w wagonie: ");
        tab[8] = scanner.next();
        System.out.print("Pojemnosc wagonu: ");
        tab[9] = scanner.next();
        Wagon wagonTmp = new Gazowe(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Double.parseDouble(tab[4]), Double.parseDouble(tab[5]), Double.parseDouble(tab[6]), Double.parseDouble(tab[7]), Double.parseDouble(tab[8]), Double.parseDouble(tab[9]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonNaMaterialyWybuchowe() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[8];
        System.out.println("Podaj:");
        System.out.print("Wage Netto wagonu: ");
        tab[0] = scanner.next();
        System.out.print("Wage Brutto wagonu: ");
        tab[1] = scanner.next();
        System.out.print("Nadawcę wagonu: ");
        tab[2] = scanner.next();
        System.out.print("Czy istnieje system hamulcowy w wagonie? : ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        System.out.print("Moc silnika: ");
        tab[4] = scanner.next();
        System.out.print("Przewozony towar: ");
        tab[5] = scanner.next();
        System.out.println("Ilosc gasnic w wagonie: ");
        tab[6] = scanner.next();
        System.out.println("Ilosc dni trwalosci ladunku: ");
        tab[7] = scanner.next();
        Wagon wagonTmp = new Wybuchowe(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), tab[5], Integer.parseInt(tab[6]), Integer.parseInt(tab[7]));
        wagons.add(wagonTmp);
        System.out.println(wagonTmp);
    }

    private void dodajWagonNaMaterialyToksyczne() {
        Scanner scanner = new Scanner(System.in);
        if(!ciecze.isEmpty()){
            String[] tab = new String[10];
            System.out.println("Podaj:");
            System.out.println("Ktora ciecz chcesz w wagonie: (Podaj nazwe)");
            wyswietlWszystkieCiecze();
            tab[8] = scanner.next();
            boolean znaleziono = false;
            for (int i = 0; i < ciecze.size(); i++) {
                if (ciecze.get(i).getNazwa().equals(tab[8])) {
                    znaleziono = true;
                    System.out.print("Wage Netto wagonu: ");
                    tab[0] = scanner.next();
                    System.out.print("Wage Brutto wagonu: ");
                    tab[1] = scanner.next();
                    System.out.print("Nadawcę wagonu: ");
                    tab[2] = scanner.next();
                    System.out.print("Czy wagon ma system hamulcowy ? ");
                    tab[3] = scanner.next();
                    if (tab[3].equals("tak")) tab[3] = "true";
                    else if (tab[3].equals("nie")) tab[3] = "false";
                    System.out.print("Moc silnika: ");
                    tab[4] = scanner.next();
                    System.out.println("Przewozony towar: ");
                    tab[5] = scanner.next();
                    System.out.println("Bezpieczny poziom toksycznosci w wagonie: ");
                    tab[6] = scanner.next();
                    System.out.print("Poziom toksycznosci substancji: ");
                    tab[7] = scanner.next();
                    System.out.print("Poziom nawietrzenia wagonu: ");
                    tab[9] = scanner.next();
                    Wagon wagonTmp = new Toksyczne(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), tab[5], Integer.parseInt(tab[6]), ciecze.get(i), Integer.parseInt(tab[9]));
                    wagons.add(wagonTmp);
                    System.out.println(wagonTmp);
                }
            }
            if (!znaleziono) {
                System.out.println("Nie ma takiej cieczy w systemie");
                System.out.println("1. Wybierz inna ciecz: ");
                System.out.println("2. Dodaj nowa ciecz: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                try {
                    int chooseOption = scanner.nextInt( );
                    if (chooseOption == 1) {
                        dodajWagonNaMaterialyToksyczne();
                    } else if (chooseOption == 2) {
                        dodajCiecz();
                        dodajWagonNaMaterialyToksyczne();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakiejkolwiek cieczy w systemie, nie mozna stworzyc wagonu toksycznego");
            System.out.println("Chcesz dodac ciecz do systemu ?");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania wagonu: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajCiecz();
                    dodajWagonNaMaterialyToksyczne();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void dodajWagonNaCiekleMaterialyToksyczne() {
        Scanner scanner = new Scanner(System.in);
        if(!ciecze.isEmpty()){
            String[] tab = new String[10];
            System.out.println("Podaj:");
            System.out.println("Ktora ciecz chcesz w wagonie: (Podaj nazwe)");
            wyswietlWszystkieCiecze();
            tab[8] = scanner.next();
            boolean znaleziono = false;
            for (int i = 0; i < ciecze.size(); i++) {
                if (ciecze.get(i).getNazwa().equals(tab[8])) {
                    znaleziono = true;
                    System.out.print("Wage Netto wagonu: ");
                    tab[0] = scanner.next();
                    System.out.print("Wage Brutto wagonu: ");
                    tab[1] = scanner.next();
                    System.out.print("Nadawcę wagonu: ");
                    tab[2] = scanner.next();
                    System.out.print("Czy wagon ma system hamulcowy ? ");
                    tab[3] = scanner.next();
                    if (tab[3].equals("tak")) tab[3] = "true";
                    else if (tab[3].equals("nie")) tab[3] = "false";
                    System.out.print("Moc silnika: ");
                    tab[4] = scanner.next();
                    System.out.println("Przewozony towar: ");
                    tab[5] = scanner.next();
                    System.out.println("Bezpieczny poziom toksycznosci w wagonie: ");
                    tab[6] = scanner.next();
                    System.out.print("Poziom toksycznosci substancji: ");
                    tab[7] = scanner.next();
                    System.out.print("Poziom nawietrzenia wagonu: ");
                    tab[9] = scanner.next();
                    Wagon wagonTmp = new Toksyczne(Double.parseDouble(tab[0]), Double.parseDouble(tab[1]), tab[2], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[4]), tab[5], Integer.parseInt(tab[6]), ciecze.get(i), Integer.parseInt(tab[9]));
                    wagons.add(wagonTmp);
                    System.out.println(wagonTmp);
                }
            }
            if (!znaleziono) {
                System.out.println("Nie ma takiej cieczy w systemie");
                System.out.println("1. Wybierz inna ciecz: ");
                System.out.println("2. Dodaj nowa ciecz: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                try {
                    int chooseOption = scanner.nextInt( );
                    if (chooseOption == 1) {
                        dodajWagonNaCiekleMaterialyToksyczne();
                    } else if (chooseOption == 2) {
                        dodajCiecz();
                        dodajWagonNaCiekleMaterialyToksyczne();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakiejkolwiek cieczy w systemie, nie mozna stworzyc wagonu cieklego toksycznego");
            System.out.println("Chcesz dodac ciecz do systemu ?");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania wagonu: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajCiecz();
                    dodajWagonNaCiekleMaterialyToksyczne();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void dodajStacje() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj nazwe stacji: ");
        String nazwa = scanner.next();
        Stacja stacjaTmp = new Stacja(nazwa);
        stacje.add(stacjaTmp);
        System.out.print("Dodana Stacja: ");
        System.out.println(stacjaTmp);
    }

    private void dodajPolaczenie() {
        if (!stacje.isEmpty()) {
            System.out.println("Ktore stacje chcesz ze soba polaczyc? (Podaj id)");
            wyswietlWszystkieStacje();
            Scanner scanner = new Scanner(System.in);
            int nrId1 = 0;
            int nrId2 = 0;
            try {
                nrId1 = scanner.nextInt();
                nrId2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono zly format danych");
                scanner.nextLine();
            }
            boolean znaleziono1 = false;
            boolean znaleziono2 = false;
            Stacja stacja1 = null;
            Stacja stacja2 = null;
            for (int i = 0; i < stacje.size(); i++) {
                if (stacje.get(i).getId() == nrId1) {
                    znaleziono1 = true;
                    stacja1 = stacje.get(i);
                }
                if (stacje.get(i).getId() == nrId2) {
                    znaleziono2 = true;
                    stacja2 = stacje.get(i);
                }

                if (znaleziono1 && znaleziono2) {
                    Polaczenie polaczenieTmp = new Polaczenie(stacja1, stacja2, (int) (Math.random() * 100));
                    polaczenia.add(polaczenieTmp);
                    System.out.print("Stworzono nowe polaczenie: ");
                    System.out.println(polaczenieTmp);
                }
            }
            if (!znaleziono1 || !znaleziono2) {
                System.out.println("Nie istnieja takie stacje");
                System.out.println("1. Wybierz inne stacje do polaczenia: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania polaczenia: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        dodajPolaczenie();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak stacji ktore mozna polaczyc");
        }
    }

    private void przypiszWagonDoPociagu() throws NieMoznaDodacWagonu {
        Scanner scanner = new Scanner(System.in);
        if (!skladPociagow.isEmpty()) {
            System.out.println("Do ktorego pociagu chcesz dodac wagon? (Podaj Id lokomotywy)");
            wyswietlWszystkiePociagi();
            int nrId = scanner.nextInt();
            boolean znalezionoLokomotywe = false;
            for (int i = 0; i < skladPociagow.size(); i++) {
                if (skladPociagow.get(i).getLokomotywa().getid() == nrId) {
                    znalezionoLokomotywe = true;
                    if (skladPociagow.get(i).getWagony().size() == skladPociagow.get(i).getLokomotywa().getIloscWagonow()) {
                        throw new NieMoznaDodacWagonu();
                    }
                    if (!wagons.isEmpty()) {
                        System.out.println("Ktory wagon chcesz przypisac do wybranego pociagu? (Podaj Id)");
                        wyswietlWszystkieWagony();
                        nrId = scanner.nextInt();
                        boolean znalezionoWagon = false;
                        for (int j = 0; j < wagons.size(); j++) {
                            if (wagons.get(j).getId() == nrId) {
                                znalezionoWagon = true;
                                double uciagLokomotywy = skladPociagow.get(i).getLokomotywa().getUciag();
                                if (wagons.get(j).getWagaBrutto() <= uciagLokomotywy) {
                                    skladPociagow.get(i).getWagony().add(wagons.get(j));
                                    skladPociagow.get(i).getLokomotywa().setUciag(uciagLokomotywy - wagons.get(j).getWagaBrutto());
                                    System.out.println("Przypisano Wagon: " + wagons.get(j) + " do pociągu" + skladPociagow.get(i));
                                } else {
                                    throw new NieMoznaDodacWagonu();
                                }
                            }
                        }
                        if (!znalezionoWagon) {
                            System.out.println("Nie ma takiego wagonu w systemie");
                            System.out.println("1. Wybierz inny wagon: ");
                            System.out.println("2. Dodaj wagon: ");
                            System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania wagonu do lokomotywy: ");
                            try {
                                int chooseOption = scanner.nextInt();
                                if (chooseOption == 1) {
                                    przypiszWagonDoPociagu();
                                } else if (chooseOption == 2) {
                                    dodajWagon();
                                    przypiszWagonDoPociagu();
                                }
                            } catch (InputMismatchException ignored) {

                            }
                        }
                    } else {
                        System.out.println("Brak jakiegokolwiek wagonu w systemie");
                        System.out.println("Chcesz dodac wagon do systemu ?");
                        System.out.println("1. Tak");
                        System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania wagonu do lokomotywy: ");
                        try {
                            int chooseOption = scanner.nextInt();
                            if (chooseOption == 1) {
                                dodajWagon();
                                przypiszWagonDoPociagu();
                            }
                        } catch (InputMismatchException ignored) {

                        }
                    }
                }
            }
            if (!znalezionoLokomotywe) {
                System.out.println("Nie ma takiej lokomotywy w systemie");
                System.out.println("1. Wybierz inna lokomotywe: ");
                System.out.println("2. Dodaj lokomotywe: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania wagonu do lokomotywy: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        przypiszWagonDoPociagu();
                    } else if (chooseOption == 2) {
                        dodajLokomotywe();
                        przypiszWagonDoPociagu();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakiejkolwiek pociagu w systemie");
            System.out.println("Chcesz dodac pociag do systemu ?");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajPociag();
                    przypiszWagonDoPociagu();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void dodajRzecz() {
        System.out.println("a) Dodaj Osobe");
        System.out.println("b) Dodaj Bagaz");
        System.out.println("c) Dodaj Paczke");
        System.out.println("d) Dodaj Ciecz");
        System.out.println("e) Dodaj Ladunek Podstawowy");
        System.out.println("f) Dodaj Ladunek Ciezki");
        Scanner scanner = new Scanner(System.in);
        char chooseOption = scanner.next().charAt(0);
        switch (chooseOption) {
            case 'a' -> {
                dodajOsobe();
            }
            case 'b' -> {
                dodajBagaz();
            }
            case 'c' -> {
                dodajPaczke();
            }
            case 'd' -> {
                dodajCiecz();
            }
            case 'e' -> {
                dodajLadunekPodstawowy();
            }
            case 'f' -> {
                dodajLadunekCiezki();
            }
            default -> System.out.println("Powrot...");
        }
    }

    private void dodajOsobe() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[3];
        System.out.println("Podaj:");
        System.out.print("imie osoby: ");
        tab[0] = scanner.next();
        System.out.print("nazwisko osoby: ");
        tab[1] = scanner.next();
        System.out.print("wiek osoby: ");
        tab[2] = scanner.next();
        osoby.add(new Osoba(tab[0], tab[1], Integer.parseInt(tab[2])));
    }

    private void dodajBagaz() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[3];
        if (!osoby.isEmpty()) {
            System.out.println("Ktora osobe chcesz przypisac jako wlasciciela do bagazu? (Podaj Imie)");
            wyswietlWszystkieOsoby();
            tab[2] = scanner.next();
            boolean znaleziono = false;
            for (int i = 0; i < osoby.size(); i++) {
                if (osoby.get(i).getImie().equals(tab[2])) {
                    znaleziono = true;
                    System.out.println("Podaj:");
                    System.out.print("mase bagazu: ");
                    tab[0] = scanner.next();
                    System.out.print("kolor bagazu: ");
                    tab[1] = scanner.next();
                    Bagaz bagazTmp = new Bagaz(Double.parseDouble(tab[0]), tab[1], osoby.get(i));
                    bagaze.add(bagazTmp);
                    System.out.println("Dodany bagaz: ");
                    System.out.println(bagazTmp);
                }
            }
            if (!znaleziono) {
                System.out.println("Nie ma takiej osoby w systemie");
                System.out.println("1. Wybierz inna osobe: ");
                System.out.println("2. Dodaj osobe: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        dodajBagaz();
                    } else if (chooseOption == 2) {
                        dodajOsobe();
                        dodajBagaz();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakiejkolwiek osoby w systemie, nie mozna stworzyc bagazu");
            System.out.println("Chcesz dodac osobe do systemu ?");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajOsobe();
                    dodajBagaz();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void dodajPaczke() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[4];
        System.out.println("Podaj: ");
        System.out.println("Mase paczki: ");
        tab[0] = scanner.next();
        System.out.println("Kolor paczki: ");
        tab[1] = scanner.next();
        System.out.println("Czy jest ona priorytetowa ? ");
        tab[2] = scanner.next();
        if (tab[2].equals("tak")) tab[2] = "true";
        else if (tab[2].equals("nie")) tab[3] = "false";
        System.out.println("Podaj jej wartosc: ");
        tab[3] = scanner.next();
        Paczka paczkaTmp = new Paczka(Double.parseDouble(tab[0]), tab[1], Boolean.parseBoolean(tab[3]), Integer.parseInt(tab[3]));
        paczki.add(paczkaTmp);
        System.out.println(paczkaTmp);
    }

    private void dodajCiecz() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[4];
        System.out.println("Podaj: ");
        System.out.println("Mase cieczy: ");
        tab[0] = scanner.next();
        System.out.println("Kolor cieczy: ");
        tab[1] = scanner.next();
        System.out.println("Nazwe cieczy: ");
        tab[2] = scanner.next();
        System.out.println("Podaj poziom toksycznosci cieczy: ");
        tab[3] = scanner.next();
        Ciecz cieczTmp = new Ciecz(Double.parseDouble(tab[0]), tab[1], tab[2], Integer.parseInt(tab[3]));
        ciecze.add(cieczTmp);
        System.out.println(cieczTmp);
    }

    private void dodajLadunekPodstawowy() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[4];
        System.out.println("Podaj: ");
        System.out.println("Mase ladunku podstawowego: ");
        tab[0] = scanner.next();
        System.out.println("Kolor ladunku podstawowego: ");
        tab[1] = scanner.next();
        System.out.println("Ilosc palet: ");
        tab[2] = scanner.next();
        System.out.println("Wartosc: ");
        tab[3] = scanner.next();
        LadunekPodstawowy ladunekPodstawowyTmp = new LadunekPodstawowy(Double.parseDouble(tab[0]), tab[1], Integer.parseInt(tab[2]), Double.parseDouble(tab[3]));
        ladunkiPodstawowe.add(ladunekPodstawowyTmp);
        System.out.println("Dodano Ladunek Podstawowy: ");
        System.out.println(ladunekPodstawowyTmp);
    }

    private void dodajLadunekCiezki() {
        Scanner scanner = new Scanner(System.in);
        String[] tab = new String[4];
        System.out.println("Podaj: ");
        System.out.println("Mase ladunku ciezkiego: ");
        tab[0] = scanner.next();
        System.out.println("Kolor ladunku ciezkiego: ");
        tab[1] = scanner.next();
        System.out.println("Czy ma mechanizm dzwigajacy: ");
        tab[2] = scanner.next();
        if (tab[2].equals("tak")) tab[3] = "true";
        else if (tab[2].equals("nie")) tab[3] = "false";
        System.out.println("System zabezpieczen: ");
        tab[3] = scanner.next();
        if (tab[3].equals("tak")) tab[3] = "true";
        else if (tab[3].equals("nie")) tab[3] = "false";
        LadunekCiezki ladunekCiezkiTmp = new LadunekCiezki(Double.parseDouble(tab[0]), tab[1], Boolean.parseBoolean(tab[2]), Boolean.parseBoolean(tab[3]));
        ladunkiCiezkie.add(ladunekCiezkiTmp);
        System.out.println("Dodano Ladunek Ciezki: ");
        System.out.println(ladunekCiezkiTmp);
    }

    private void przypiszRzecz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("a) Przypisz osobe: ");
        System.out.println("b) Przypisz bagaz: ");
        System.out.println("c) Przypisz paczke: ");
        char chooseOption = scanner.next().charAt(0);
        switch (chooseOption) {
            case 'a' -> {
                przypiszOsobe();
            }
            case 'b' -> {
                przypiszBagaz();
            }
            case 'c' -> {
                przypiszPaczke();
            }
            default -> System.out.println("Powrot...");
        }
    }

    private void przypiszOsobe() {
        Scanner scanner = new Scanner(System.in);
        if (!wagons.isEmpty()) {
            boolean znalezionoWagonPasazerski = false;
            for (int i = 0; i < wagons.size(); i++) {
                if (wagons.get(i) instanceof WagonPasazerski<?>) {
                    znalezionoWagonPasazerski = true;
                    System.out.println("Do ktorego wagonu chcesz dodac osobe? (Podaj id wagonu)");
                    int counter = 1;
                    for (int j = 0; j < wagons.size(); j++) {
                        if (wagons.get(j) instanceof WagonPasazerski<?>) {
                            System.out.println(counter + ". " + wagons.get(j));
                            counter++;
                        }
                    }
                    int nrId = scanner.nextInt();
                    boolean znalezionoWagonWybranyPrzezUsera = false;
                    for (int j = 0; j < wagons.size(); j++) {
                        if (wagons.get(j).getId() == nrId && wagons.get(j) instanceof WagonPasazerski<?>) {
                            znalezionoWagonWybranyPrzezUsera = true;
                            if (!osoby.isEmpty()) {
                                String imie;
                                System.out.println("Ktora osobe chcesz przypisac do wagonu? (Podaj Imie)");
                                wyswietlWszystkieOsoby();
                                imie = scanner.next();
                                boolean znaleziono = false;
                                for (int k = 0; k < osoby.size(); k++) {
                                    if (osoby.get(k).getImie().equals(imie)) {
                                        znaleziono = true;
                                        WagonPasazerski<Osoba> pasazerskiTmp = (WagonPasazerski<Osoba>) wagons.get(j);
                                        try {
                                            pasazerskiTmp.dodajPasazera(osoby.get(k));
                                        } catch (SameThing e) {
                                            System.out.println("Nie mozna przypisac 2 razy tej samej osoby");
                                            break;
                                        }
                                        System.out.println("Przypisano osobe: " + osoby.get(k) + " do wagonu " + pasazerskiTmp);
                                    }
                                }
                                if (!znaleziono) {
                                    System.out.println("Nie ma takiej osoby w systemie");
                                    System.out.println("1. Wybierz inna osobe: ");
                                    System.out.println("2. Dodaj osobe: ");
                                    System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                                    try {
                                        int chooseOption = scanner.nextInt();
                                        if (chooseOption == 1) {
                                            przypiszOsobe();
                                        } else if (chooseOption == 2) {
                                            dodajOsobe();
                                            przypiszOsobe();
                                        }
                                    } catch (InputMismatchException ignored) {

                                    }
                                }
                            } else {
                                System.out.println("Brak jakichkolwiek osob do przypisania");
                                System.out.println("Czy chcesz stworzyc osobe? ");
                                System.out.println("1. Tak");
                                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania osoby: ");
                                try {
                                    int chooseOption = scanner.nextInt();
                                    if (chooseOption == 1) {
                                        dodajOsobe();
                                        przypiszOsobe();
                                    }
                                } catch (InputMismatchException ignored) {

                                }
                            }
                        }
                    }
                    if (!znalezionoWagonWybranyPrzezUsera) {
                        System.out.println("Nie ma takiego wagonu w systemie");
                        System.out.println("1. Wybierz inny wagon: ");
                        System.out.println("2. Dodaj wagon: ");
                        System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                        try {
                            int chooseOption = scanner.nextInt();
                            if (chooseOption == 1) {
                                przypiszOsobe();
                            } else if (chooseOption == 2) {
                                dodajWagonPasazerski();
                                przypiszOsobe();
                            }
                        } catch (InputMismatchException ignored) {

                        }
                    }
                }
            }
            if (!znalezionoWagonPasazerski) {
                System.out.println("Brak wagonu pasazerskiego, nie mozna przypisac osoby");
                System.out.println("Chcesz dodac wagon pasazerski? ");
                System.out.println("1. Tak");
                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania osoby: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        dodajWagonPasazerski();
                        przypiszOsobe();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakichkolwiek wagonow do ktorych mozna przypisac osobe");
            System.out.println("Czy chcesz stworzyc wagon? ");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania osoby: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajWagonPasazerski();
                    przypiszOsobe();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void przypiszBagaz() {
        Scanner scanner = new Scanner(System.in);
        if (!wagons.isEmpty()) {
            boolean znalezionoWagonBagazowy = false;
            for (int i = 0; i < wagons.size(); i++) {
                if (wagons.get(i) instanceof BagazowoPocztowy<?>) {
                    znalezionoWagonBagazowy = true;
                    System.out.println("Do ktorego wagonu chcesz dodac bagaz? (Podaj id wagonu)");
                    int counter = 1;
                    for (int j = 0; j < wagons.size(); j++) {
                        if (wagons.get(j) instanceof BagazowoPocztowy<?>) {
                            System.out.println(counter + ". " + wagons.get(j));
                            counter++;
                        }
                    }
                    int nrId = scanner.nextInt();
                    boolean znalezionoWagonWybranyPrzezUsera = false;
                    for (int j = 0; j < wagons.size(); j++) {
                        if (wagons.get(j).getId() == nrId) {
                            znalezionoWagonWybranyPrzezUsera = true;
                            if (!bagaze.isEmpty()) {
                                System.out.println("Ktory bagaz chcesz przypisac do wagonu? (Podaj Id)");
                                wyswietlWszystkieBagaze();
                                nrId = scanner.nextInt();
                                boolean znaleziono = false;
                                for (int k = 0; k < bagaze.size(); k++) {
                                    if (bagaze.get(k).getId() == nrId) {
                                        znaleziono = true;
                                        BagazowoPocztowy<Bagaz> bagazowoPocztowyTmp = (BagazowoPocztowy<Bagaz>) wagons.get(j);
                                        try {
                                            bagazowoPocztowyTmp.dodajBagaz(bagaze.get(k));
                                        } catch (SameThing e) {
                                            System.out.println("Nie mozna przypisac 2 razy tego samego bagazu");
                                            break;
                                        }
                                        System.out.println("Przypisano bagaz: " + bagaze.get(k) + " do wagonu " + bagazowoPocztowyTmp);
                                    }
                                }
                                if (!znaleziono) {
                                    System.out.println("Nie ma takiego bagazu  w systemie");
                                    System.out.println("1. Wybierz inny bagaz: ");
                                    System.out.println("2. Dodaj bagaz: ");
                                    System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                                    try {
                                        int chooseOption = scanner.nextInt();
                                        if (chooseOption == 1) {
                                            przypiszBagaz();
                                        } else if (chooseOption == 2) {
                                            dodajBagaz();
                                            przypiszBagaz();
                                        }
                                    } catch (InputMismatchException ignored) {

                                    }
                                }
                            } else {
                                System.out.println("Brak jakichkolwiek bagazy do przypisania");
                                System.out.println("Czy chcesz stworzyc bagaz? ");
                                System.out.println("1. Tak");
                                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania bagazu: ");
                                try {
                                    int chooseOption = scanner.nextInt();
                                    if (chooseOption == 1) {
                                        dodajBagaz();
                                        przypiszBagaz();
                                    }
                                } catch (InputMismatchException ignored) {

                                }
                            }
                        }
                    }
                    if (!znalezionoWagonWybranyPrzezUsera) {
                        System.out.println("Nie ma takiego wagonu w systemie");
                        System.out.println("1. Wybierz inny wagon: ");
                        System.out.println("2. Dodaj wagon: ");
                        System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                        try {
                            int chooseOption = scanner.nextInt();
                            if (chooseOption == 1) {
                                przypiszBagaz();
                            } else if (chooseOption == 2) {
                                dodajWagonBagazowoPocztowy();
                                przypiszBagaz();
                            }
                        } catch (InputMismatchException ignored) {

                        }
                    }
                }
            }
            if (!znalezionoWagonBagazowy) {
                System.out.println("Brak wagonu bagazowego, nie mozna przypisac bagazu");
                System.out.println("Chcesz dodac wagon bagazowy? ");
                System.out.println("1. Tak");
                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania bagazu: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        dodajWagonBagazowoPocztowy();
                        przypiszBagaz();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakichkolwiek wagonow do ktorych mozna przypisac bagaz");
            System.out.println("Czy chcesz stworzyc wagon? ");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania bagazu: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajWagonBagazowoPocztowy();
                    przypiszBagaz();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void przypiszPaczke() {
        Scanner scanner = new Scanner(System.in);
        if (!wagons.isEmpty()) {
            boolean znalezionoWagonPocztowy = false;
            for (int i = 0; i < wagons.size(); i++) {
                if (wagons.get(i) instanceof Pocztowy<?>) {
                    znalezionoWagonPocztowy = true;
                    System.out.println("Do ktorego wagonu chcesz dodac paczke? (Podaj id wagonu)");
                    int counter = 1;
                    for (int j = 0; j < wagons.size(); j++) {
                        if (wagons.get(j) instanceof Pocztowy<?>) {
                            System.out.println(counter + ". " + wagons.get(j));
                            counter++;
                        }
                    }
                    int nrId = scanner.nextInt();
                    boolean znalezionoWagonWybranyPrzezUsera = false;
                    for (int j = 0; j < wagons.size(); j++) {
                        if (wagons.get(j).getId() == nrId) {
                            znalezionoWagonWybranyPrzezUsera = true;
                            if (!paczki.isEmpty()) {
                                System.out.println("Ktora paczke chcesz przypisac do wagonu? (Podaj Id)");
                                wyswietlWszystkiePaczki();
                                nrId = scanner.nextInt();
                                boolean znaleziono = false;
                                for (int k = 0; k < paczki.size(); k++) {
                                    if (paczki.get(k).getId() == nrId) {
                                        znaleziono = true;
                                        Pocztowy<Paczka> pocztowyTmp = (Pocztowy<Paczka>) wagons.get(j);
                                        try {
                                            pocztowyTmp.dodajPaczke(paczki.get(k));
                                        } catch (SameThing e) {
                                            System.out.println("Nie mozna przypisac 2 razy tej samej paczki");
                                            break;
                                        }
                                        System.out.println("Przypisano paczke: " + paczki.get(k) + " do wagonu " + pocztowyTmp);
                                    }
                                }
                                if (!znaleziono) {
                                    System.out.println("Nie ma takiej paczki  w systemie");
                                    System.out.println("1. Wybierz inna paczke: ");
                                    System.out.println("2. Dodaj paczke: ");
                                    System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                                    try {
                                        int chooseOption = scanner.nextInt();
                                        if (chooseOption == 1) {
                                            przypiszPaczke();
                                        } else if (chooseOption == 2) {
                                            dodajPaczke();
                                            przypiszPaczke();
                                        }
                                    } catch (InputMismatchException ignored) {

                                    }
                                }
                            } else {
                                System.out.println("Brak jakichkolwiek paczek do przypisania");
                                System.out.println("Czy chcesz stworzyc paczke? ");
                                System.out.println("1. Tak");
                                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania paczki: ");
                                try {
                                    int chooseOption = scanner.nextInt();
                                    if (chooseOption == 1) {
                                        dodajPaczke();
                                        przypiszPaczke();
                                    }
                                } catch (InputMismatchException ignored) {

                                }
                            }
                        }
                    }
                    if (!znalezionoWagonWybranyPrzezUsera) {
                        System.out.println("Nie ma takiego wagonu w systemie");
                        System.out.println("1. Wybierz inny wagon: ");
                        System.out.println("2. Dodaj wagon: ");
                        System.out.println("   Kliknij cokolwiek aby wyjsc z dodawania rzeczy: ");
                        try {
                            int chooseOption = scanner.nextInt();
                            if (chooseOption == 1) {
                                przypiszPaczke();
                            } else if (chooseOption == 2) {
                                dodajWagonPocztowy();
                                przypiszPaczke();
                            }
                        } catch (InputMismatchException ignored) {

                        }
                    }
                }
            }
            if (!znalezionoWagonPocztowy) {
                System.out.println("Brak wagonu pocztowego, nie mozna przypisac paczki");
                System.out.println("Chcesz dodac wagon pocztowy? ");
                System.out.println("1. Tak");
                System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania paczki: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        dodajWagonPocztowy();
                        przypiszPaczke();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakichkolwiek wagonow do ktorych mozna przypisac paczke");
            System.out.println("Czy chcesz stworzyc wagon? ");
            System.out.println("1. Tak");
            System.out.println("   Kliknij cokolwiek aby wyjsc z przypisywania paczki: ");
            try {
                int chooseOption = scanner.nextInt();
                if (chooseOption == 1) {
                    dodajWagonPocztowy();
                    przypiszPaczke();
                }
            } catch (InputMismatchException ignored) {

            }
        }
    }

    private void wyswietl() {
        System.out.println("a) Wyswietl Pociagi");
        System.out.println("b) Wyswietl Lokomotywy");
        System.out.println("c) Wyswietl Wagony");
        System.out.println("d) Wyswietl Stacje");
        System.out.println("e) Wyswietl Polaczenia");
        System.out.println("f) Wyswietl Rzeczy");
        Scanner scanner = new Scanner(System.in);
        char chooseOption = scanner.next().charAt(0);
        switch (chooseOption) {
            case 'a' -> {
                wyswietlWszystkiePociagi();
            }
            case 'b' -> {
                wyswietlWszystkieLokomotywy();
            }
            case 'c' -> {
                wyswietlWszystkieWagony();
            }
            case 'd' -> {
                wyswietlWszystkieStacje();
            }
            case 'e' -> {
                wyswietlWszystkiePolaczenia();
            }
            case 'f' -> {
                wyswietlRzeczy();
            }
        }
    }

    private void wyswietlWszystkiePociagi() {
        for (SkladPociagu skladPociagu : skladPociagow) {
            System.out.println(skladPociagu);
        }
    }

    private void wyswietlWszystkieLokomotywy() {
        for (Lokomotywa lokomotywa : lokomotywaList) {
            System.out.println(lokomotywa);
        }
    }

    private void wyswietlWszystkieWagony() {
        for (Wagon wagon : wagons) {
            System.out.println(wagon);
        }
    }

    private void wyswietlWszystkieStacje() {
        for (Stacja stacja : stacje) {
            System.out.println(stacja);
        }
    }

    private void wyswietlWszystkiePolaczenia() {
        for (Polaczenie polaczenie : polaczenia) {
            System.out.println(polaczenie);
        }
    }

    private void wyswietlRzeczy() {
        System.out.println("1. Wyswietl Osoby");
        System.out.println("2. Wyswietl Bagaze");
        System.out.println("3. Wyswietl Paczki");
        System.out.println("4. Wyswietl Ciecze");
        System.out.println("5. Wyswietl Ladunki Podstawowe");
        System.out.println("6. Wyswietl Ladunki Ciezkie");
        Scanner scanner = new Scanner(System.in);
        int chooseOption = scanner.nextInt();
        switch (chooseOption) {
            case 1 -> {
                wyswietlWszystkieOsoby();
            }
            case 2 -> {
                wyswietlWszystkieBagaze();
            }
            case 3 -> {
                wyswietlWszystkiePaczki();
            }
            case 4 -> {
                wyswietlWszystkieCiecze();
            }
            case 5 -> {
                wyswietlWszystkieLadunkiPodstawowe();
            }
            case 6 -> {
                wyswietlWszystkieLadunkiCiezkie();
            }
            default -> {
                System.out.println("Powrot...");
            }
        }
    }

    private void wyswietlWszystkieOsoby() {
        for (Osoba osoba : osoby) {
            System.out.println(osoba);
        }
    }

    private void wyswietlWszystkieBagaze() {
        for (Bagaz bagaz : bagaze) {
            System.out.println(bagaz);
        }
    }

    private void wyswietlWszystkiePaczki() {
        for (Paczka paczka : paczki) {
            System.out.println(paczka);
        }
    }

    private void wyswietlWszystkieCiecze() {
        for (Ciecz ciecz : ciecze) {
            System.out.println(ciecz);
        }
    }

    private void wyswietlWszystkieLadunkiPodstawowe() {
        for (LadunekPodstawowy ladunekPodstawowy : ladunkiPodstawowe) {
            System.out.println(ladunekPodstawowy);
        }
    }

    private void wyswietlWszystkieLadunkiCiezkie() {
        for (LadunekCiezki ladunekCiezki : ladunkiCiezkie) {
            System.out.println(ladunekCiezki);
        }
    }

    private void usun() {
        System.out.println("a) Usun Lokomotywe: ");
        System.out.println("b) Usun Wagon: ");
        System.out.println("c) Usun Osobe: ");
        System.out.println("d) Usun Rzecz: ");
        Scanner scanner = new Scanner(System.in);
        char chooseOption = scanner.next().charAt(0);
        switch (chooseOption) {
            case 'a' -> {
                usunLokomotywe();
            }
            case 'b' -> {
                usunWagon();
            }
            case 'c' -> {
                usunOsobe();
            }
        }
    }

    private void usunLokomotywe() {
        if (!lokomotywaList.isEmpty()) {
            System.out.println("Ktora lokomotywe chcesz usunac? (Podaj id)");
            wyswietlWszystkieLokomotywy();
            Scanner scanner = new Scanner(System.in);
            int nrId = 0;
            try {
                nrId = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono zly format danych");
                scanner.nextLine();
            }
            boolean znaleziono = false;
            for (int i = 0; i < lokomotywaList.size(); i++) {
                if (lokomotywaList.get(i).getid() == nrId) {
                    znaleziono = true;
                    lokomotywaList.get(i).posprzataj();
                    lokomotywaList.remove(i);
                }
            }
            if (!znaleziono) {
                System.out.println("Nie istnieje taka lokomotywa");
                System.out.println("1. Wybierz inna lokomotywe do usuniecia: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z usuwania: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        usunLokomotywe();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakichkolwiek lokomotyw do usuniecia");
        }
    }

    private void usunWagon() {
        if (!wagons.isEmpty()) {
            System.out.println("Ktory wagon chcesz usunac? (Podaj id)");
            wyswietlWszystkieWagony();
            Scanner scanner = new Scanner(System.in);
            int nrId = 0;
            try {
                nrId = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono zly format danych");
                scanner.nextLine();
            }
            boolean znaleziono = false;
            for (int i = 0; i < wagons.size(); i++) {
                if (wagons.get(i).getId() == nrId) {
                    znaleziono = true;
                    wagons.remove(i);
                }
            }
            if (!znaleziono) {
                System.out.println("Nie istnieje taki wagon");
                System.out.println("1. Wybierz inny wagon do usuniecia: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z usuwania: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        usunWagon();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakichkolwiek wagonow do usuniecia");
        }
    }

    private void usunOsobe() {
        if (!osoby.isEmpty()) {
            System.out.println("Ktora osobe chcesz usunac? (Podaj id)");
            wyswietlWszystkieOsoby();
            Scanner scanner = new Scanner(System.in);
            int nrId = 0;
            try {
                nrId = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono zly format danych");
                scanner.nextLine();
            }
            boolean znaleziono = false;
            for (int i = 0; i < osoby.size(); i++) {
                if (osoby.get(i).getId() == nrId) {
                    znaleziono = true;
                    osoby.remove(i);
                }
            }
            if (!znaleziono) {
                System.out.println("Nie istnieje taka osoba");
                System.out.println("1. Wybierz inna osobe do usuniecia: ");
                System.out.println("   Kliknij cokolwiek aby wyjsc z usuwania: ");
                try {
                    int chooseOption = scanner.nextInt();
                    if (chooseOption == 1) {
                        usunOsobe();
                    }
                } catch (InputMismatchException ignored) {

                }
            }
        } else {
            System.out.println("Brak jakichkolwiek osob do usuniecia");
        }
    }

    public void setSkladPociagow(List<SkladPociagu> skladPociagow) {
        this.skladPociagow = skladPociagow;
    }

    public void setLokomotywaList(List<Lokomotywa> lokomotywaList) {
        this.lokomotywaList = lokomotywaList;
    }

    public void setWagons(List<Wagon> wagons) {
        this.wagons = wagons;
    }

    public void setStacje(List<Stacja> stacje) {
        this.stacje = stacje;
    }

    public void setPolaczenia(List<Polaczenie> polaczenia) {
        this.polaczenia = polaczenia;
    }

    public void setOsoby(List<Osoba> osoby) {
        this.osoby = osoby;
    }

    public void setBagaze(List<Bagaz> bagaze) {
        this.bagaze = bagaze;
    }

    public void setPaczki(List<Paczka> paczki) {
        this.paczki = paczki;
    }

    public void setCiecze(List<Ciecz> ciecze) {
        this.ciecze = ciecze;
    }

    public void setLadunkiPodstawowe(List<LadunekPodstawowy> ladunkiPodstawowe) {
        this.ladunkiPodstawowe = ladunkiPodstawowe;
    }

    public void setLadunkiCiezkie(List<LadunekCiezki> ladunkiCiezkie) {
        this.ladunkiCiezkie = ladunkiCiezkie;
    }

    public List<Stacja> symulacjaStacji() {
        List<Stacja> stacje = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            stacje.add(new Stacja("Stacja" + (i + 1)));
        }
        return stacje;
    }

    public List<Polaczenie> symulacjaPolaczen() {
        List<Polaczenie> polaczenia = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            polaczenia.add(new Polaczenie(stacje.get(i), stacje.get(i + 1), (int) (Math.random() * 100)));
        }
        return polaczenia;
    }

    public SiecPolaczen symulacjaSiecPolaczen(){
        SiecPolaczen siecPolaczen = new SiecPolaczen();
        for (int i = 0; i < stacje.size(); i++) {
            siecPolaczen.dodajStacje(stacje.get(i));
            if(i != 99){
                stacje.get(i).dodajPolaczenie(polaczenia.get(i));
                siecPolaczen.dodajPolaczenie(polaczenia.get(i));
            } else {
                stacje.get(i).dodajPolaczenie(polaczenia.get(0));
                siecPolaczen.dodajPolaczenie(polaczenia.get(0));
            }
        }
        return siecPolaczen;
    }

    public List<Lokomotywa> symulacjaLokomotyw() {
        List<Lokomotywa> lokomotywy = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            lokomotywy.add(new Lokomotywa("Lokomotywa" + (i + 1), (int) (Math.random() * 5 + 5), (int) (Math.random() * 1000), (int) (Math.random() * 3), "StacjaMacierzysta" + (i + 1), (int) (Math.random() * 198 + 1)));
        }
        return lokomotywy;
    }

    public List<Wagon> symulacjaWagony() {
        List<Wagon> wagony = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            wagony.add(new Chlodniczy((Math.random() * 100 + 10), (Math.random() * 100 + 10), (Math.random() * 100 + 10), (Math.random() * 100 + 10), (Math.random() * 100 + 10), (Math.random() * 100 + 1), "Nadawca" + (i + 1), false, (int) (Math.random() * 200 + 1), (Math.random() * 50 - 100), (Math.random() * 200 + 1)));
            wagony.add(new Ciezkie<LadunekCiezki>((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Nadawca" + (i + 1), true, (int) (Math.random() * 100 + 10), "Towar" + (i + 1)));
            wagony.add(new Restauracja((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Nadawca" + (i + 1), true, (int) (Math.random() * 100 + 10), "Menu" + (i + 1), (Math.random() * 1000 + 1)));
            wagony.add(new Pocztowy<>((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Nadawca" + (i + 1), true, (int) (Math.random() * 100 + 10), (int) (Math.random() * 100 + 1), (Math.random() * 1000 + 1)));
            wagony.add(new WagonPasazerski<>((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Nadawca" + (i + 1), true, (int) (Math.random() * 50 + 10), (int) (Math.random() * 200 + 1)));
            wagony.add(new Wybuchowe((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Nadawca" + (i + 1), true, (int) (Math.random() * 100 + 10), "PrzewozonyTowar" + (i + 1), (int) (Math.random() * 200 + 1), (int) (Math.random() * 10 + 5)));
        }
        return wagony;
    }
    public List<SkladPociagu> symulacjaPociag() {
        List<SkladPociagu> skladPociagow = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < 25; i++) {
            int x = (int) (Math.random() * 5 + 5);
            List<Wagon> wagony = new ArrayList<>();
            for (int j = 0; j < x; j++) {
                wagony.add(wagons.get(counter));
                counter++;
            }
            SkladPociagu skladPociaguTmp = new SkladPociagu(lokomotywaList.get(i), wagony, stacje.get((int)(Math.random() * 50)), stacje.get((int)(Math.random() * 50 + 50)));
            skladPociagow.add(skladPociaguTmp);
            List<Stacja> trasa = siecPolaczen.wyznaczDroge(skladPociaguTmp.getStacjaZrodlowa(), skladPociaguTmp.getStacjaDocelowa());
            System.out.println(trasa);
            skladPociaguTmp.setTrasa(trasa);
            Thread t = new Thread(skladPociaguTmp);
            t.start();
        }
        return skladPociagow;
    }
}

