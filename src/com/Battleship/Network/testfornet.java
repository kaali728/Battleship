package com.Battleship.Network;

import java.util.ArrayList;
import java.util.Arrays;

public class testfornet {
    public static void main(String[] args) {
        int ca = 5;
        int bat = 4;
        int subma = 3;
        int des= 2;
        ArrayList<Integer> i = new ArrayList<>();
        i.add(ca);
        i.add(bat);
        i.add(subma);
        i.add(des);
        String list = Arrays.toString(i.toArray()).replace("[", "").replace("]", "");

        System.out.println(list.replace(",", ""));
    }
}
