package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class ScheduleInfo {
    String name;
    int remainingTime;
    int count;
    int elapsedTime;
    int round;
    String state;
    int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

public class Schedule {
    static ArrayList<ScheduleInfo> scheduleInfo = new ArrayList<>();
    static int round;
    static int priority = 50;
    public static void showRRData() {
        System.out.println("N\tet\trt\tc\tr\ts");
        for (ScheduleInfo info : scheduleInfo) {
            System.out.print(info.getName() + "\t");
            System.out.print(info.getElapsedTime() + "\t");
            System.out.print(info.getRemainingTime() + "\t");
            System.out.print(info.getCount() + "\t");
            System.out.print(info.getRound() + "\t");
            System.out.print(info.getState() + "\t");
            System.out.println();
        }
        printReady();
    }
    public static void showPRData() {
        System.out.println("N\tet\trt\tc\tp\ts");
        for (ScheduleInfo info : scheduleInfo) {
            System.out.print(info.getName() + "\t");
            System.out.print(info.getElapsedTime() + "\t");
            System.out.print(info.getRemainingTime() + "\t");
            System.out.print(info.getCount() + "\t");
            System.out.print(info.getPriority() + "\t");
            System.out.print(info.getState() + "\t");
            System.out.println();
        }
        printReady();
    }

    private static void printReady() {
        System.out.print("Ready query：");
        for (ScheduleInfo info : scheduleInfo) {
            if (info.getState().equals("W")) {
                System.out.print(info.getName() + " ");
            }
        }
        System.out.println();
        System.out.print("Finished query：");
        for (ScheduleInfo info : scheduleInfo) {
            if (info.getState().equals("F")) {
                System.out.print(info.getName() + " ");
            }
        }
        System.out.println();
    }

    public static void priorityAlgorithm()
    {
        int finishedSum = 0;
        scheduleInfo.sort(new Comparator<ScheduleInfo>() {
            public int compare(ScheduleInfo o1, ScheduleInfo o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
        scheduleInfo.get(0).setState("R");
        showPRData();
        scheduleInfo.get(0).setCount(scheduleInfo.get(0).getCount() + 1);
        scheduleInfo.get(0).setElapsedTime(scheduleInfo.get(0).getElapsedTime() + scheduleInfo.get(0).getRemainingTime());
        scheduleInfo.get(0).setRemainingTime(0);
    }
    public static void RR() {
        int finishedSum = 0;
        while (finishedSum != scheduleInfo.size()) {
            if (!scheduleInfo.get(0).getState().equals("F")) {
                scheduleInfo.get(0).setState("R");
                showRRData();
                int remainingTime = scheduleInfo.get(0).getRemainingTime();
                if (remainingTime > round) {
                    scheduleInfo.get(0).setRemainingTime(remainingTime - round);
                    scheduleInfo.get(0).setElapsedTime(scheduleInfo.get(0).getElapsedTime() + round);
                    scheduleInfo.get(0).setCount(scheduleInfo.get(0).getCount() + 1);
                    scheduleInfo.get(0).setState("W");
                    scheduleInfo.add(scheduleInfo.get(0));
                    scheduleInfo.remove(0);
                } else {
                    scheduleInfo.get(0).setCount(scheduleInfo.get(0).getCount() + 1);
                    int temp = round;
                    for (int i = 0; i < scheduleInfo.size(); i++) {
                        remainingTime = scheduleInfo.get(0).getRemainingTime();
                        if (scheduleInfo.get(0).getState().equals("F")) {
                            scheduleInfo.add(scheduleInfo.get(0));
                            scheduleInfo.remove(0);
                        } else {
                            scheduleInfo.get(0).setRemainingTime(Math.max(remainingTime - temp, 0));
                            scheduleInfo.get(0).setElapsedTime(scheduleInfo.get(0).getElapsedTime() + remainingTime - Math.max(remainingTime - temp, 0));
                            temp -= remainingTime;
                            scheduleInfo.get(0).setState("F");
                            finishedSum++;
                            scheduleInfo.add(scheduleInfo.get(0));
                            scheduleInfo.remove(0);
                            if (temp <= 0) {
                                break;
                            }
                        }
                    }
                }
            } else {
                scheduleInfo.add(scheduleInfo.get(0));
                scheduleInfo.remove(0);
            }
        }
        showRRData();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the schedule system!\n"
                + "Please choose the algorithm:\n"
                + "1.round robin algorithm\n"
                + "2.priority algorithm\n"
                + "3.quit");
        Scanner sc = new Scanner(System.in);
        int selected;
        do {
            selected = sc.nextInt();
            int serviceNum;
            switch (selected) {
                case 1:
                    System.out.println("Please input the round time:");
                    round = sc.nextInt();
                    System.out.println("Please input the service number:");
                    serviceNum = sc.nextInt();
                    System.out.println("Please input name and service time:\n"
                            + "For example, if the name is a0 and the service time is 3, then input a0 3");
                    for (int i = 0; i < serviceNum; i++) {
                        ScheduleInfo scheduleTemporary = new ScheduleInfo();
                        scheduleTemporary.setName(sc.next());
                        scheduleTemporary.setRemainingTime(sc.nextInt());
                        scheduleTemporary.setCount(0);
                        scheduleTemporary.setElapsedTime(0);
                        scheduleTemporary.setRound(round);
                        scheduleTemporary.setState("W");
                        scheduleInfo.add(scheduleTemporary);
                    }
                    RR();
                    break;
                case 2:
                    System.out.println("Please input the service number:");
                    serviceNum = sc.nextInt();
                    System.out.println("Please input name and service time:\n"
                            + "For example, if the name is a0 and the service time is 3, then input a0 3");
                    for (int i = 0; i < serviceNum; i++) {
                        ScheduleInfo scheduleTemporary = new ScheduleInfo();
                        scheduleTemporary.setName(sc.next());
                        scheduleTemporary.setRemainingTime(sc.nextInt());
                        scheduleTemporary.setCount(0);
                        scheduleTemporary.setElapsedTime(0);
                        scheduleTemporary.setPriority(priority-scheduleTemporary.remainingTime);
                        scheduleTemporary.setState("W");
                        scheduleInfo.add(scheduleTemporary);
                    }
                    priorityAlgorithm();
                    break;
            }
        } while (selected != 3);
        sc.close();
    }

}
