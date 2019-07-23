package cga;

import cga.components.Point;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cga.Utils;

public class Maxima {
    public static int[] sortBasedMaxima(Point[] points) {
        List<Integer> list = new LinkedList<Integer>();
        if (points.length == 0)
            return new int[0];
        int[] indices = Utils.argsortWithX(points);
        double altitude = points[indices[indices.length - 1]].y;
        list.add(indices[indices.length - 1]);
        for (int i = indices.length - 2; i >= 0; i--) {
            if (points[indices[i]].y > altitude) {
                list.add(indices[i]);
                altitude = points[indices[i]].y;
            }
        }
        int[] ans = new int[list.size()];
        int i = 0;
        for (int elem : list)
            ans[i++] = elem;
        // No need to sort actually...
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        Point[] points = new Point[] { new Point(9, 1), new Point(8, 2), new Point(1, 10), new Point(2, 5),
                new Point(3, 10), new Point(5, 1) };
        int[] ans1 = sortBasedMaxima(points);
        System.out.println(Arrays.toString(ans1));
    }
}