package com.datastructuresproject3;

public class Driver1 {
    public static void main(String[] args) {
        HashMap hash = new HashMap(10);
        
        hash.insert(new MartyrDate("10/10/2020"));
        hash.insert(new MartyrDate("20/20/2102"));
        hash.insert(new MartyrDate("13/23/1212"));
        hash.insert(new MartyrDate("Hello"));
        hash.insert(new MartyrDate("cc"));
        hash.insert(new MartyrDate("dd"));
        hash.insert(new MartyrDate("ff"));
        hash.printHash();
        
        System.out.println();
        
        System.out.println("Find Index 'Hello': " + hash.find("Hello") + "\n");
        
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("10/10/2020") + "\n");
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("20/20/2102") + "\n");
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("13/23/1212") + "\n");
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("Hello") + "\n");
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("cc") + "\n");
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("dd") + "\n");
        System.out.println("Find Index 'Hello': " + hash.findHashEntry("ff") + "\n");
        
        System.out.println("Find 'Hello': " + hash.find("Hello") + "\n");
        
        System.out.println("Find 'cc': " + hash.find("cc") + "\n");
        
        System.out.println("Remove '20/20/2102': " + hash.remove("20/20/2102") + "\n");
        hash.printHash();
        
        System.out.println("Find '20/20/2102': " + hash.find("20/20/2102") + "\n");
        
        System.out.println("Contains 'cc': " + hash.contains("cc") + "\n");
        
        System.out.println("Remove 'ff': " + hash.remove("ff") + "\n");
        hash.printHash();
        
        System.out.println("Remove 'Hello': " + hash.remove("Hello") + "\n");
        hash.printHash();
        
        System.out.println("Remove 'ff' again: " + hash.remove("ff") + "\n");
        hash.printHash();
        
        
    }
}
