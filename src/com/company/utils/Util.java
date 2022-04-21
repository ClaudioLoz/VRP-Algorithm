package com.company.utils;

import com.company.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Util {

    public static int randomIndex(int limit) {
        Random random = new Random();
        return limit == 0 ? 0 : random.nextInt(limit);
    }


    public static double randomDouble() {
        Random random = new Random();
        return random.nextDouble();
    }

    //out of use
    public static double euclideanDistance(int x1, int x2, int y1, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double euclideanDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static List<Order>[] splitRoute(List<Order> route) {
        List<Order> first = new ArrayList<>();
        List<Order> second = new ArrayList<>();
        int size = route.size();

        if (size != 0) {
            int partitionIndex = Util.randomIndex(size);

            for (int i = 0; i < route.size(); i++) {
                if (partitionIndex > i) {
                    first.add(route.get(i));
                } else {
                    second.add(route.get(i));
                }
            }
        }
        return new List[]{first, second};
    }

    public static ArrayList<ArrayList<Order>> splitRoute(List<Order> route, int k) {
        ArrayList<ArrayList<Order>> parts = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            parts.add(new ArrayList<>());
        }

        int[] partitionIndices = new int[k - 1];

        for (int i = 0; i < k - 1; i++) {
            partitionIndices[i] = Util.randomIndex(route.size());
        }

        Arrays.sort(partitionIndices);

        for (int i = 0; i < route.size(); i++) {
            for (int j = 0; j < partitionIndices.length; j++) {
                if (i < partitionIndices[j]) {
                    parts.get(j).add(route.get(i));
                    break;
                } else if (i >= partitionIndices[partitionIndices.length - 1]) {
                    parts.get(parts.size() - 1).add(route.get(i));
                    break;
                }
            }
        }

        return parts;
    }

}
