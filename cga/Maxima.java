package cga;

import cga.components.Point;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cga.Utils;

public class Maxima {
    public static int[] sortBasedMaxima(Point[] points) {
        if (points.length == 0)
            return new int[0];
        List<Integer> list = new LinkedList<Integer>();
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

    public static int[] deletionBasedMaxima(Point[] points) {
        if (points.length == 0)
            return new int[0];
        List<Integer> ansList = new LinkedList<Integer>();
        List<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < points.length; i++)
            list.add(i);
        Utils.PointXComparator comp = new Utils.PointXComparator();
        while (!list.isEmpty()) {
            int rightmost = -1;
            for (int index : list) {
                if (rightmost < 0 || comp.compare(points[index], points[rightmost]) > 0)
                    rightmost = index;
            }
            ansList.add(rightmost);
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int index = it.next();
                if (index == rightmost
                        || points[index].x <= points[rightmost].x && points[index].y <= points[rightmost].y)
                    it.remove();
            }
        }
        int i = 0;
        int[] ans = new int[ansList.size()];
        for (int elem : ansList)
            ans[i++] = elem;
        Arrays.sort(ans);
        return ans;
    }

    // TODO:
    // Double trick

    public static void main(String[] args) {
        Point[] points = new Point[] { new Point(9, 1), new Point(8, 2), new Point(1, 10), new Point(2, 5),
                new Point(3, 10), new Point(5, 1), new Point(5, 10) };
        int[] ans1 = sortBasedMaxima(points);
        System.out.println(Arrays.toString(ans1));
        int[] ans2 = deletionBasedMaxima(points);
        System.out.println(Arrays.toString(ans2));
    }
}