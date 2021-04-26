package top.hiccup.jdk.concurrent.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * 在两个线程之间交换数据，只能是2个线程，类似Linux的管道
 *
 * 这种设计跟程序员自己做线程共享相比有什么优势嚒？
 *
 * @author wenhy
 * @date 2019/8/12
 */
public class ExchangerTest {

    static class Producer extends Thread {
        List<Integer> list = new ArrayList<>();
        Exchanger<List<Integer>> exchanger = null;

        public Producer(Exchanger<List<Integer>> exchanger) {
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                list.clear();
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                try {
                    list = exchanger.exchange(list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        List<Integer> list = new ArrayList<>();
        Exchanger<List<Integer>> exchanger = null;

        public Consumer(Exchanger<List<Integer>> exchanger) {
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    list = exchanger.exchange(list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(list.get(0) + ", ");
                System.out.print(list.get(1) + ", ");
                System.out.print(list.get(2) + ", ");
                System.out.print(list.get(3) + ", ");
                System.out.println(list.get(4) + ", ");
            }
        }
    }

    public static void main(String[] args) {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        new Consumer(exchanger).start();
        new Producer(exchanger).start();
    }
}