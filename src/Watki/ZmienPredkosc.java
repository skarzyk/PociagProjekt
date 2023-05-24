package Watki;

import Exceptions.RailroadHazard;
import Pociag.Lokomotywa;

public class ZmienPredkosc extends Thread{
    private Lokomotywa lokomotywa;
    public ZmienPredkosc(Lokomotywa lokomotywa){
        this.lokomotywa = lokomotywa;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            double x = Math.random();
            double staraPredkosc = lokomotywa.getPredkosc();
            double nowaPredkosc;
            if (x > 0.5) nowaPredkosc = staraPredkosc + staraPredkosc * 0.03;
            else nowaPredkosc = staraPredkosc - staraPredkosc * 0.03;
            lokomotywa.setPredkosc(nowaPredkosc);
            try {
                lokomotywa.czyPrzekroczonoPredkosc();
            } catch (RailroadHazard e) {
             System.out.println("UWAGA PRZEKROCZONA PREDKOSC");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
