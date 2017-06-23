package org.iplab.ProducerCosumer;

/**
 * Created by js982 on 2017/6/4.
 */
public class ProducerRunnable implements Runnable {

    private Number number;

    public ProducerRunnable(Number num) {
        this.number = num;
    }

    @Override
    public void run() {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    number.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
