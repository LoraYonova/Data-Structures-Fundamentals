package implementations;

import interfaces.AbstractTree;
import org.w3c.dom.Node;

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
        extracted(result);

        return result.stream().sorted().collect(Collectors.toList());
    }

    private void extracted(List<E> result) {
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
    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (queue.size() > 0) {
            Tree<E> current = queue.poll();

            for (Tree<E> child : current.children) {
                if (child.children.size() != 0 && child.parent != null) {
                    result.add(child.value);
                }
                queue.offer(child);
            }
        }

        return result;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {

        List<Tree<E>> tree = getTreesBfs();

        Tree<E> deepestLeftmostNode = null;

        int maxPath = 0;

        for (Tree<E> eTree : tree) {
            if (eTree.isLeaf()) {
                int currentPath = getStepsFromLeftToRoot(eTree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLeftmostNode = eTree;
                }
            }
        }

        return deepestLeftmostNode;
    }

    private List<Tree<E>> getTreesBfs() {
        List<Tree<E>> tree = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (queue.size() > 0) {
            Tree<E> current = queue.poll();

            for (Tree<E> child : current.children) {
                tree.add(child);
                queue.offer(child);
            }
        }
        return tree;
    }

    private int getStepsFromLeftToRoot(Tree<E> eTree) {
        int count = 0;
        Tree<E> current = eTree;
        while (current.parent != null) {
            count++;
            current = current.parent;
        }

        return count;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }


    @Override
    public List<E> getLongestPath() {

        List<Tree<E>> trees = getTreesBfs();

        List<E> longestPath = new ArrayList<>();
        int path = 0;

        for (Tree<E> tree : trees) {
            List<E> currentElements = new ArrayList<>();
            int currentPath = 0;

            while (tree.parent != null) {
                currentPath++;
                currentElements.add(tree.value);
                tree = tree.parent;
            }

            if (currentPath > path) {
                path = currentPath;
                longestPath = currentElements;
            }
        }
        longestPath.add(this.value);
        Collections.reverse(longestPath);

        return longestPath;
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



