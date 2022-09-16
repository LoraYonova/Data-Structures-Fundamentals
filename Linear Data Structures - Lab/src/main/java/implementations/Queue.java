package implementations;

import interfaces.AbstractQueue;
import org.w3c.dom.Node;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> next;


        public Node(E value) {
            this.element = value;
            this.next = null;
        }
    }

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void offer(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.isEmpty()) {
            this.head = newNode;
            this.size++;
            return;
        }
        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }
       current.next = newNode;
        this.size++;

    }

    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.head.element;
        if (this.size == 1) {
            this.head = null;
        } else {
            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;
        }
        this.size--;
        return element;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Queue.Node<E> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E element = current.element;
                this.current = current.next;
                return element;
            }
        };
    }
}
