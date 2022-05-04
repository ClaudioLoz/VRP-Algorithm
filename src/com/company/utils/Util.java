package com.company.utils;

import com.company.Order;
import com.company.utils.graph.CityNode;

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

    public static double calculateDistanceByHaversineFormula(double lon1, double lat1,
                                                           double lon2, double lat2) {

        double earthRadius = 6371; // km

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dlon = (lon2 - lon1);
        double dlat = (lat2 - lat1);

        double sinlat = Math.sin(dlat / 2);
        double sinlon = Math.sin(dlon / 2);

        double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
        double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

        double distanceInMeters = earthRadius * c * 1000;

        return distanceInMeters;

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

  public static double haversine(CityNode city1, CityNode city2) {
    double lat1 = city1.getLatitude();
    double lon1 = city1.getLongitude();
    double lat2 = city2.getLatitude();
    double lon2 = city2.getLongitude();
    // distance between latitudes and longitudes
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);

    // convert to radians
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    // apply formulae
    double a = Math.pow(Math.sin(dLat / 2), 2) +
        Math.pow(Math.sin(dLon / 2), 2) *
            Math.cos(lat1) *
            Math.cos(lat2);
    double rad = 6371;
    double c = 2 * Math.asin(Math.sqrt(a));
    return rad * c;
  }

}
