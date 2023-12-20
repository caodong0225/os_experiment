package org.example;

import java.util.ArrayList;
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
        System.out.println("Name\telapse\tremain\tcount\tround\tstate");
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
        System.out.println("Name\telapse\tremain\tcount\tpriori\tstate");
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

    public static void priorityAlgorithm() {
        int finishedSum = 0;
        while (finishedSum != scheduleInfo.size()) {
            scheduleInfo.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
            if (!scheduleInfo.get(0).getState().equals("F")) {
                scheduleInfo.get(0).setState("R");
                if(scheduleInfo.get(0).getCount()==0)
                {
                    scheduleInfo.get(0).setCount(1);
                }
                showPRData();
                int remainingTime = scheduleInfo.get(0).getRemainingTime();
                scheduleInfo.get(0).setRemainingTime(remainingTime - 1);
                scheduleInfo.get(0).setElapsedTime(scheduleInfo.get(0).getElapsedTime() + 1);
                scheduleInfo.get(0).setPriority(scheduleInfo.get(0).getPriority() - 1);
                for (ScheduleInfo info : scheduleInfo) {
                    if (info.getState().equals("W")) {
                        info.setPriority(info.getPriority() + 1);
                    }
                }
                if (scheduleInfo.get(0).getRemainingTime() == 0) {
                    finishedSum++;
                    scheduleInfo.get(0).setState("F");
                } else {
                    scheduleInfo.get(0).setState("W");
                }
            } else {
                scheduleInfo.add(scheduleInfo.get(0));
                scheduleInfo.remove(0);
            }
        }
        showPRData();
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
        Scanner sc = new Scanner(System.in);
        int selected;
        while(true) {
            System.out.println("Welcome to the schedule system!\n"
                    + "Please choose the algorithm:\n"
                    + "1.round robin algorithm\n"
                    + "2.priority algorithm\n"
                    + "3.quit");
            selected = sc.nextInt();
            int serviceNum;
            if (selected==1) {
                scheduleInfo.clear();
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
            }
            else if(selected==2) {
                scheduleInfo.clear();
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
                    scheduleTemporary.setPriority(priority - scheduleTemporary.remainingTime);
                    scheduleTemporary.setState("W");
                    scheduleInfo.add(scheduleTemporary);
                }
                priorityAlgorithm();
            }
            else if(selected==3)
            {
                break;
            }
        }
        sc.close();
    }

}
