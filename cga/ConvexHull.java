package cga;

import cga.components.Point;

import cga.exceptions.TooFewPointsException;

import java.util.Arrays;
import java.util.Stack;

import cga.Utils;

public class ConvexHull {
    public static int[] grahamScan(Point[] points) throws TooFewPointsException {
        if (points.length < 3)
            throw new TooFewPointsException();
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

    public static void main(String[] args) throws Exception {
        Point[] points = new Point[] { new Point(5, 10), new Point(2, 1), new Point(5, 2), new Point(4, 2),
                new Point(5, 8) };
        int[] ans = grahamScan(points);
        System.out.println(Arrays.toString(ans));
    }
}