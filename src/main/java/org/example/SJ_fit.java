package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Block
{
    int adress;
    int size;
    int end;

    public int getAdress() {
        return adress;
    }

    public void setAdress(int adress) {
        this.adress = adress;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    public Block()
    {
        setAdress(0);
        setSize(32767);
        setEnd(32766);
    }
}
public class SJ_fit {
    static ArrayList<Block> blocks = new ArrayList<>();
    public static void showData()
    {
        System.out.print("index\t*\tadr\t*\tend\t*\tsize\n");
        for(int i=0;i<blocks.size();i++)
        {
            System.out.print((i+1)+"\t\t"+blocks.get(i).getAdress()+"\t\t"+blocks.get(i).getEnd()+"\t\t"+blocks.get(i).getSize()+"\n");
        }
    }
    public static void bestFit()
    {
        Scanner sc = new Scanner(System.in);
        while(true) {
            showData();
            System.out.print("Assign or Accept:(as or ac): ");
            String way = sc.next();
            if (way.equals("ac")) {
                accept(sc);
                blocks.sort(Comparator.comparingInt(Block::getSize));
            } else if (way.equals("as")) {
                assign(sc);
                //将block按照size的值大小，从小到大排序
                blocks.sort(Comparator.comparingInt(Block::getSize));
            } else {
                System.out.println("wrong input!");
                break;
            }
        }
    }
    public static void firstFit()
    {
        Scanner sc = new Scanner(System.in);
        while(true) {
            showData();
            System.out.print("Assign or Accept:(as or ac): ");
            String way = sc.next();
            if (way.equals("ac")) {
                accept(sc);
            } else if (way.equals("as")) {
                assign(sc);
            } else {
                System.out.println("wrong input!");
                break;
            }
        }
    }

    private static void assign(Scanner sc) {
        System.out.print("input APPLICATION:");
        int application = sc.nextInt();
        boolean isFind = false;
        for (Block block : blocks) {
            if (block.getSize() >= application) {
                block.setSize(block.getSize() - application);
                block.setEnd(block.getEnd() - application);
                isFind = true;
                System.out.println("SUCCESS!!!ADDRESS=" + (block.getEnd() + 1));
                break;
            }
        }
        if(!isFind)
        {
            System.out.println("Too   large  application!");
        }
    }

    private static void accept(Scanner sc) {
        System.out.print("Input adr and size: ");
        int address = sc.nextInt()-1;
        int size = sc.nextInt();
        int addSize = address + size;
        boolean isExisted = false;
        for (Block block : blocks) {
            if (address <= block.getEnd() && addSize > block.getEnd()) {
                block.setSize(block.getSize() + addSize - block.getEnd());
                block.setEnd(addSize);
                isExisted = true;
                break;
            } else if ((address <= block.getAdress() && addSize > address)) {
                block.setSize(block.getSize() + block.getAdress() - address + 1);
                block.setAdress(address + 1);
                isExisted = true;
                break;
            }
        }
        if(!isExisted) {
            Block newBlock = new Block();
            newBlock.setAdress(address + 1);
            newBlock.setEnd(addSize);
            newBlock.setSize(size);
            blocks.add(newBlock);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        blocks.add(new Block());
        System.out.print("input the way (best or first): ");
        String way = sc.next();
        if(way.equals("best"))
        {
            bestFit();
        }
        else if(way.equals("first"))
        {
            firstFit();
        }
        else
        {
            System.out.println("wrong input!");
        }
        sc.close();
    }
}
