package org.iplab.ProducerCosumer;

/**
 * Created by js982 on 2017/6/5.
 */
public class BargainApp {


    public static void main(String arg[]){
        Number num = new Number();
        ProducerRunnable produce = new ProducerRunnable(num);
        ConsumerRunnable consumer = new ConsumerRunnable(num);
        Thread PT1 = new Thread(produce);
        Thread PT2 = new Thread(produce);
        Thread CT1 = new Thread(consumer);
        Thread CT2 = new Thread(consumer);

        PT1.start();
        PT2.start();
        CT1.start();
        CT2.start();
    }
}
