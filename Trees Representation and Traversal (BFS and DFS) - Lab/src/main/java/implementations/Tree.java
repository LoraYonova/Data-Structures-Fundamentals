package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {
        this.value = key;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }

    @Override
    public List<E> orderBfs() {

        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (queue.size() > 0) {
            Tree<E> current = queue.poll();
            result.add(current.value);

            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    @Override
    public List<E> orderDfs() {

        List<E> result = new ArrayList<>();
        this.dfs(this, result);

        return result;
    }

    private void dfs(Tree<E> current, List<E> result) {
        for (Tree<E> child : current.children) {
            this.dfs(child, result);
        }
        result.add(current.value);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> current = find(parentKey);

        if (current == null) {
            throw new IllegalArgumentException();
        }

        current.children.add(child);
        child.parent = current;

    }

    private Tree<E> find(E parentKey) {

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (queue.size() > 0) {
            Tree<E> current = queue.poll();
            if (current.value.equals(parentKey)) {
                return current;
            }

            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return null;
    }


    @Override
    public void removeNode(E nodeKey) {
        Tree<E> eTree = find(nodeKey);
        if (eTree == null) {
            throw new NullPointerException();
        }
        children.remove(eTree);
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> first = find(firstKey);
        Tree<E> second = find(secondKey);

        if (first == null || second == null) {
            throw new IllegalArgumentException();
        }


        Tree<E> currentFirst = first.parent;
        Tree<E> currentSecond = second.parent;

        if (currentFirst == null) {
            this.value = currentSecond.value;
            this.parent = null;
            this.children = currentSecond.children;
            currentSecond.parent = null;
            return;
        } else if (currentSecond == null) {
            this.value = currentFirst.value;
            this.parent = null;
            this.children = currentFirst.children;
            currentFirst.parent = null;
            return;
        }


        int firstIndex = currentFirst.children.indexOf(first);
        int secondIndex = currentSecond.children.indexOf(second);

        currentFirst.children.set(firstIndex, second);
        currentSecond.children.set(secondIndex, first);


    }
}



