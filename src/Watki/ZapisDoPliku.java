package Watki;

import Pociag.SkladPociagu;
import Pociag.Wagony.Wagon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;


public class ZapisDoPliku extends Thread{

    List<SkladPociagu> skladPociaguList;

    public ZapisDoPliku(List<SkladPociagu> skladPociaguList){
        this.skladPociaguList = skladPociaguList;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                zapisDoPliku(skladPociaguList);
                Thread.sleep(5000);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void zapisDoPliku(List<SkladPociagu> skladPociaguList) throws IOException{
        Comparator<Wagon> wagonComparatorWaga = (o1, o2) -> (int) (o1.getWagaBrutto() - o2.getWagaBrutto());

        File file = new File("src/AppState.txt");
        FileOutputStream fos = new FileOutputStream(file);
        if(!file.exists()){
            file.createNewFile();
        }

        for (int i = 0; i < skladPociaguList.size(); i++) {
            skladPociaguList.get(i).getWagony().sort(wagonComparatorWaga);
            fos.write(skladPociaguList.get(i).toString().getBytes());
            fos.write("\n".getBytes());
        }
    }
}



