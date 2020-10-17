package com.ebdaa.reportapp;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortUtil implements Comparator<List<String>> {
    private static int SORT_FACTOR = -1;
    private static boolean sort_asc = true;

    public static void sortBy(ArrayList<List<String>> data,int columnIndex) {
        if (SORT_FACTOR == columnIndex && !sort_asc) {
            Collections.sort(data, Collections.reverseOrder(new SortUtil()));
            Collections.sort(data,Collections.reverseOrder(new SortUtil()));
            sort_asc = true;
        } else {
            SORT_FACTOR = columnIndex;
            Collections.sort(data, new SortUtil());
            sort_asc = false;
        }
    }








    @Override
    public int compare(List<String> s, List<String> t1) {
        for (int i = 0; i < s.size(); i++) {


            if (i == SORT_FACTOR) {
                return s.get(i).compareTo(t1.get(i));
            }
        }
        return s.get(0).compareTo(t1.get(0));
    }




}

