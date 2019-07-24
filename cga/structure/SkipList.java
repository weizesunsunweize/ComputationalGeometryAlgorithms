package cga.structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

class SkipNode<T> {
    public final T val;
    public final int level;
    private Comparator<T> comp;
    public ArrayList<SkipNode<T>> next;

    public SkipNode(T val, int level, Comparator<T> comp) {
        this.val = val;
        this.level = level;
        this.comp = comp;
        this.next = new ArrayList<SkipNode<T>>();
        for (int i = 0; i < level; i++)
            this.next.add(null);
    }

    public SkipNode<T> search(T val, int level) {
        if (this.comp.compare(this.val, val) == 0)
            return this;
        if (this.next.get(level) != null && comp.compare(this.next.get(level).val, val) <= 0)
            return this.next.get(level).search(val, level);
        if (this.next.get(level) != null && comp.compare(this.next.get(level).val, val) > 0 && level > 0)
            return this.next.get(level - 1).search(val, level - 1);
        return this;
    }

    public void insert(SkipNode<T> node, int level) {
        if (this.next.get(level) != null && comp.compare(this.next.get(level).val, node.val) <= 0) {
            this.next.get(level).insert(node, level);
            return;
        }
        if (this.next.get(level) == null || this.comp.compare(this.next.get(level).val, node.val) > 0) {
            node.next.set(level, this.next.get(level));
            this.next.set(level, node);
        }
        if (level > 0)
            this.insert(node, level - 1);
    }

    public void delete(T val, int level) {
        if (this.next.get(level) != null && this.comp.compare(this.next.get(level).val, val) < 0) {
            this.next.get(level).delete(val, level);
            return;
        }
        if (this.next.get(level) != null && this.comp.compare(this.next.get(level).val, val) == 0)
            this.next.set(level, this.next.get(level).next.get(level));
        if (level > 0)
            this.delete(val, level - 1);
        return;
    }
}

public class SkipList<T> {
    public final int maxLevel;
    private Comparator<T> comp;
    private SkipNode<T> head;
    private Random random;
    private double threshold;

    public SkipList(int maxLevel, double threshold, Comparator<T> comp) {
        this.maxLevel = maxLevel;
        this.threshold = threshold;
        this.comp = comp;
        this.head = new SkipNode<T>(null, maxLevel, comp);
        this.random = new Random();
    }

    public T search(T val) {
        SkipNode<T> node = this.head.search(val, this.maxLevel - 1);
        return (node != this.head && this.comp.compare(node.val, val) == 0 ? node.val : null);
    }

    private SkipNode<T> createNode(T val) {
        int level = 1;
        while (level < this.maxLevel && this.random.nextDouble() > threshold)
            level++;
        return new SkipNode<T>(val, level, comp);
    }

    public void insert(T val) {
        SkipNode<T> node = createNode(val);
        head.insert(node, node.level - 1);
    }

    public void delete(T val) {
        this.head.delete(val, this.maxLevel - 1);
    }

    @Override
    public String toString() {
        String ans = "";
        for (int level = this.maxLevel - 1; level >= 0; level--) {
            SkipNode<T> node = this.head.next.get(level);
            while (node != null) {
                ans += node.val + " -> ";
                node = node.next.get(level);
            }
            ans += "null\n";
        }
        return ans;
    }
}
