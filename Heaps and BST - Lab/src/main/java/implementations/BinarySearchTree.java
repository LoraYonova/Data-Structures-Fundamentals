package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree() {}
    public BinarySearchTree(Node<E> root) {
            this.copy(root);
    }

    private void copy(Node<E> node) {
        if (node != null) {
            this.insert(node.value);
            this.copy(node.leftChild);
            this.copy(node.rightChild);
        }
    }


    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.root == null) {
            this.root = newNode;
        } else {
            Node<E> current = this.root;
            Node<E> prev = current;
            while (current != null) {
                prev = current;
                if (less(element, current.value)) {
                    current = current.leftChild;
                } else if (greater(element, current.value)) {
                    current = current.rightChild;
                } else {
                    return;
                }
            }
            current = newNode;
            if (less(element, prev.value)) {
                prev.leftChild = newNode;
            } else if (greater(element, prev.value)) {
                prev.rightChild = newNode;
            }

        }
    }

    private boolean less(E first, E second) {
        return first.compareTo(second) < 0;
    }

    private boolean greater(E first, E second) {
        return first.compareTo(second) > 0;
    }

//    private boolean equal(E first, E second) {
//        return first.compareTo(second) == 0;
//    }

    @Override
    public boolean contains(E element) {
        Node<E> current = this.root;
        while (current != null) {
            if (less(element, current.value)) {
                current = current.leftChild;
            } else if (greater(element, current.value)) {
                current = current.rightChild;
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {

        AbstractBinarySearchTree<E> tree = new BinarySearchTree<>();
        Node<E> current = this.root;
        while (current != null) {
            if (less(element, current.value)) {
                current = current.leftChild;
            } else if (greater(element, current.value)) {
                current = current.rightChild;
            } else {
                return new BinarySearchTree<E>(current);
            }
        }
        return tree;
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
