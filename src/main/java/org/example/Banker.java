package org.example;

import java.util.Scanner;

public class Banker {
    static int[][] max;
    static int[][] allocated;
    static int[][] need;
    static int[][] work;
    static int resource;
    static int customer;
    static int[] available;
    static String[] customerName;
    public static boolean judge(int[] request,int[] actual)
    {
        for(int i=0;i<resource;i++)
        {
            if(request[i]>actual[i])
            {
                return false;
            }
        }
        return true;
    }
    public static void cal_need()
    {
        need = new int[customer][resource];
        for(int i=0;i<customer;i++)
        {
            for(int j=0;j<resource;j++)
            {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }
    }
    public static void cal_work(int[] availableCopy,int[][] allocatedCopy,int[][] needCopy)
    {
        int[] visited = new int[customer];
        int record = 0;
        work = new int[customer][resource];
        System.out.println("Name\tWork\tNeed\tAllo\tWork+A\tFinish");
        for(int k=0;k<customer;k++) {
            for (int i = 0; i < customer; i++) {
                if (judge(needCopy[i], availableCopy) && visited[i] == 0) {
                    System.out.print(customerName[i] + "\t");
                    for (int j = 0; j < resource; j++) {
                        System.out.print(availableCopy[j] + " ");
                        availableCopy[j] = availableCopy[j] + allocatedCopy[i][j];
                        work[i][j] = availableCopy[j];
                    }
                    System.out.print("\t");
                    for (int j = 0; j < resource; j++) {
                        System.out.print(needCopy[i][j] + " ");
                    }
                    System.out.print("\t");
                    for (int j = 0; j < resource; j++) {
                        System.out.print(allocatedCopy[i][j] + " ");
                    }
                    System.out.print("\t");
                    for (int j = 0; j < resource; j++) {
                        System.out.print(work[i][j] + " ");
                    }
                    System.out.print("\t");
                    System.out.print("T");
                    System.out.println();
                    visited[i] = 1;
                    record++;
                    break;
                }
            }
        }
        if(record==customer)
        {
            System.out.println("SYSTEM SECURITY!!!");
        }
        else
        {
            System.out.println("No Customer Available!!!");
            System.out.println("SYSTEM IS NOT SECURITY!!!");
        }
    }
    public static void systemSecurity()
    {
        cal_need();
        int[] availableCopy = new int[resource];
        System.arraycopy(available, 0, availableCopy, 0, resource);
        int[][] allocatedCopy = new int[customer][resource];
        int[][] needCopy = new int[customer][resource];
        for(int i=0;i<customer;i++)
        {
            System.arraycopy(allocated[i], 0, allocatedCopy[i], 0, resource);
            System.arraycopy(need[i], 0, needCopy[i], 0, resource);
        }
        cal_work(availableCopy,allocatedCopy,needCopy);
    }
    public static void requestSecurity()
    {
        Scanner requestScanner = new Scanner(System.in);
        System.out.println("Please input the customer’s name and request:\n" +
                "For example, if the customer’s name is customer0 and the request is 1 2 3 4, then input customer0 1 2 3 4");
        String customerNameTemp = requestScanner.next();
        int customerIndex = Integer.parseInt(customerNameTemp.substring(customerNameTemp.length()-1));
        int[] request = new int[resource];
        for(int i=0;i<resource;i++)
        {
            request[i] = requestScanner.nextInt();
        }
        cal_need();
        int[] availableCopy = new int[resource];
        System.arraycopy(available, 0, availableCopy, 0, resource);
        int[][] allocatedCopy = new int[customer][resource];
        int[][] needCopy = new int[customer][resource];
        for(int i=0;i<customer;i++)
        {
            System.arraycopy(allocated[i], 0, allocatedCopy[i], 0, resource);
            System.arraycopy(need[i], 0, needCopy[i], 0, resource);
        }
        if(judge(request,availableCopy))
        {
            for(int i=0;i<resource;i++)
            {
                availableCopy[i] = availableCopy[i] - request[i];
                allocatedCopy[customerIndex][i] = allocatedCopy[customerIndex][i] + request[i];
                needCopy[customerIndex][i] = needCopy[customerIndex][i] - request[i];
            }
            cal_work(availableCopy,allocatedCopy,needCopy);
        }
        else
        {
            System.out.println("RESOURCE INSUFFICIENT!!!");
            System.out.println(customerName[customerIndex]+" CAN NOT  OBTAIN RESOURCES IMMEDIATELY.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the type of the resource and the number of customer:");
        resource = scanner.nextInt();
        customer = scanner.nextInt();
        max = new int[customer][resource];
        allocated = new int[customer][resource];
        available = new int[resource];
        customerName = new String[customer];
        System.out.println("Input the amount of resource (maximum , allocated) of each customer:");
        System.out.println("For example, if the maximum amount of resource of customer0 is 10, 20, 30, 40, and the allocated amount is 5, 10, 15, 20, then input customer0 10 20 30 40 5 10 15 20");
        for(int j=0;j<customer;j++)
        {
            String customerNameTemp = scanner.next();
            int index = Integer.parseInt(customerNameTemp.substring(customerNameTemp.length()-1));
            customerName[index] = customerNameTemp;
            int[] maxTem = new int[resource];
            int[] allocatedTem = new int[resource];
            for(int i=0;i<resource;i++)
            {
                maxTem[i] = scanner.nextInt();
            }
            for(int i=0;i<resource;i++)
            {
                allocatedTem[i] = scanner.nextInt();
            }
            max[index] = maxTem;
            allocated[index] = allocatedTem;
        }
        System.out.println("Input the amount of each available resource:");
        for(int i=0;i<resource;i++)
        {
            available[i] = scanner.nextInt();
        }
        int status;
        label:
        do
        {
            System.out.println("1、judge the system security\n" +
                    "2、judge the request security\n" +
                    "3、quit\n");
            System.out.println("Input your choice:");
            status = scanner.nextInt();
            switch (status) {
                case 1:
                    systemSecurity();
                    break;
                case 2:
                    requestSecurity();
                    break;
                case 3:
                    break label;
            }
        }while (true);
        scanner.close();
    }
}
