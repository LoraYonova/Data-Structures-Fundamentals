package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... children) {
        this.value = value;
        this.children = new ArrayList<>();

        for (Tree<E> child : children) {
            child.setParent(this);
            this.children.add(child);
        }
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);

    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.value;
    }

    @Override
    public String getAsString() {

       StringBuilder stringBuilder = new StringBuilder();
        this.dfs(this, stringBuilder, 0);
        return stringBuilder.toString().trim();
    }

    private void dfs(Tree<E> tree, StringBuilder stringBuilder, int ident) {

        stringBuilder.append(this.getPadding(ident));
        stringBuilder.append(tree.getKey());
        stringBuilder.append(System.lineSeparator());
        for (Tree<E> child : tree.children) {
            this.dfs(child, stringBuilder, ident + 2);
        }
    }

    private String getPadding(int ident) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ident; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (queue.size() > 0) {
            Tree<E> current = queue.poll();

            for (Tree<E> child : current.children) {
                if (child.children.size() == 0) {
                    result.add(child.value);
                }
                queue.offer(child);
            }
        }

        return result.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        return null;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        return null;
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



