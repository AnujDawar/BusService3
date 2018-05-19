package com.example.anujdawar.busservice3;

import java.util.Comparator;

public class ComparatorClass implements Comparator<product> {

    @Override
    public int compare(product o1, product o2) {
        if(Integer.parseInt(o1.getMyBusFare()) < Integer.parseInt(o2.getMyBusFare()))
            return 1;
        return -1;
    }
}
