package com.alsahmou.focusapp;

public class Name {

    private String name;


    public String getName(){

        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public static void staticMethod(int var1, int var2){

        int sum;
        sum = var1 + var2;
    }
}

class Initiator {

    public void nameChanger(){

        Name name1 = new Name();
        name1.setName("name5");

        Name name2 = new Name();
        name2.setName("name6");

        Name sum = new Name();
        sum.staticMethod(2, 3);
    }
}

