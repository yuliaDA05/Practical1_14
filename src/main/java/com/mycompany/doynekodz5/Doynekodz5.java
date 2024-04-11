/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.doynekodz5;

import java.util.Scanner;

/**
 *
 * @author yulia
 */
public class Doynekodz5 {

    private static final Object lock = new Object();
    private static int currentThread = 0;

    public static void main(String[] args) {
        Thread thread0 = new Thread(new MyRunnable(0));
        Thread thread1 = new Thread(new MyRunnable(1));
        Thread thread2 = new Thread(new MyRunnable(2));

        thread0.start();
        thread1.start();
        thread2.start();

        System.out.println("Нажмите Enter для остановки программы.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        thread0.interrupt();
        thread1.interrupt();
        thread2.interrupt();
    }

    static class MyRunnable implements Runnable {
        private final int num;

        public MyRunnable (int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    while (currentThread != num) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Thread-" + num);
                    currentThread = (currentThread + 1) % 3;
                    lock.notifyAll();
                }

                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
