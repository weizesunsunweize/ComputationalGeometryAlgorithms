package cga;

import cga.components.Point;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cga.Utils;

public class ConvexHull {
    public static int[] grahamScan(Point[] points) {
        if (points.length == 0)
            return new int[] {};
        if (points.length == 1)
            return new int[] { 0 };
        if (points.length == 2)
            return (new Utils.PointXComparator()).compare(points[0], points[1]) <= 0 ? new int[] { 0, 1 }
                    : new int[] { 1, 0 };
        Integer[] indices = Utils.argsortWithX(points);
        Stack<Integer> upperHull = new Stack<Integer>();
        upperHull.push(indices[0]);
        upperHull.push(indices[1]);
        for (int i = 2; i < indices.length; i++) {
            while (upperHull.size() > 1) {
                int q = upperHull.pop(), p = upperHull.pop(), r = indices[i];
                if (Utils.orient(points[p], points[q], points[r]) <= 0) {
                    upperHull.push(p);
                    upperHull.push(q);
                    break;
                }
                upperHull.push(p);
            }
            upperHull.push(indices[i]);
        }
        Stack<Integer> lowerHull = new Stack<Integer>();
        lowerHull.push(indices[0]);
        lowerHull.push(indices[1]);
        for (int i = 2; i < indices.length; i++) {
            while (lowerHull.size() > 1) {
                int q = lowerHull.pop(), p = lowerHull.pop(), r = indices[i];
                if (Utils.orient(points[p], points[q], points[r]) >= 0) {
                    lowerHull.push(p);
                    lowerHull.push(q);
                    break;
                }
                lowerHull.push(p);
            }
            lowerHull.push(indices[i]);
        }
        int uhSize = upperHull.size(), lhSize = lowerHull.size();
        int[] ans = new int[uhSize + lhSize - 2];
        for (int i = 0; i < uhSize; i++)
            ans[uhSize - 1 - i] = upperHull.pop();
        for (int i = 0; i < lhSize; i++) {
            if (i == 0 || i == lhSize - 1)
                lowerHull.pop();
            else
                ans[uhSize - 1 + i] = lowerHull.pop();
        }
        return ans;
    }

    public static int[] giftWrapping(Point[] points) {
        if (points.length == 0)
            return new int[] {};
        if (points.length == 1)
            return new int[] { 0 };
        if (points.length == 2)
            return (new Utils.PointXComparator()).compare(points[0], points[1]) <= 0 ? new int[] { 0, 1 }
                    : new int[] { 1, 0 };
        List<Integer> list = new ArrayList<Integer>();
        int bottom = Utils.argminY(points);
        list.add(bottom);
        Point farleft = new Point(points[bottom].x - 1, points[bottom].y);
        Comparator<Point> comp = new Comparator<Point>() {
            private double epsilon = 1e-5;

            @Override
            public int compare(Point p1, Point p2) {
                // Avoid zero-vectors
                if (Point.distance(p1, farleft) < this.epsilon || Point.distance(p1, points[bottom]) < this.epsilon)
                    return -1;
                if (Point.distance(p2, farleft) < this.epsilon || Point.distance(p2, points[bottom]) < this.epsilon)
                    return 1;
                double a1 = Utils.angle(farleft, points[bottom], p1);
                double a2 = Utils.angle(farleft, points[bottom], p2);
                if (a1 < a2) {
                    return -1;
                } else if (a1 == a2) {
                    double d1 = Point.distance(points[bottom], p1);
                    double d2 = Point.distance(points[bottom], p2);
                    if (d1 < d2)
                        return 1;
                    else if (d1 == d2)
                        return 0;
                    else
                        return -1;
                } else {
                    return 1;
                }
            }
        };
        int second = Utils.argmax(points, comp);
        list.add(second);
        while (true) {
            Point lastPoint = points[list.get(list.size() - 1)];
            Point secondLastPoint = points[list.get(list.size() - 2)];
            comp = new Comparator<Point>() {
                private double epsilon = 1e-5;

                @Override
                public int compare(Point p1, Point p2) {
                    // Avoid zero-vectors
                    if (Point.distance(p1, lastPoint) < this.epsilon
                            || Point.distance(p1, secondLastPoint) < this.epsilon)
                        return -1;
                    if (Point.distance(p2, lastPoint) < this.epsilon
                            || Point.distance(p2, secondLastPoint) < this.epsilon)
                        return 1;
                    double a1 = Utils.angle(secondLastPoint, lastPoint, p1);
                    double a2 = Utils.angle(secondLastPoint, lastPoint, p2);
                    if (a1 < a2) {
                        return -1;
                    } else if (a1 == a2) {
                        double d1 = Point.distance(lastPoint, p1);
                        double d2 = Point.distance(lastPoint, p2);
                        if (d1 < d2)
                            return 1;
                        else if (d1 == d2)
                            return 0;
                        else
                            return -1;
                    } else {
                        return 1;
                    }
                }
            };
            int next = Utils.argmax(points, comp);
            if (next == list.get(0))
                break;
            list.add(next);
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            ans[i] = list.get(i);
        return ans;
    }

    public static void main(String[] args) {
        Point[] points = new Point[] { new Point(5, 10), new Point(2, 1), new Point(5, 2), new Point(4, 2),
                new Point(5, 8), new Point(3, 4) };
        int[] ans1 = grahamScan(points);
        System.out.println(Arrays.toString(ans1));
        int[] ans2 = giftWrapping(points);
        System.out.println(Arrays.toString(ans2));
    }
}