package implementations;

import java.util.Iterator;

public class ReversedList<E> {
    private final int INITIAL_CAPACITY = 2;

    private Object[] items;
    private int size;
    private int capacity;

    public ReversedList() {
        this.items = new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
    }

    public void add(E element) {

        if (this.size == this.capacity) {
            grow();
        }

        this.items[this.size++] = element;

    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

    public E get(int index) {
        ensureValidIndex(index);

        return (E) this.items[this.size - 1 - index];
    }

    public void removeAt(int index) {
        ensureValidIndex(index);

        int elementIndex = this.size - 1 - index;

        for (int i = elementIndex; i < this.size; i++) {

            this.items[i] = this.items[i + 1];
        }

        this.size--;
    }

    private void ensureValidIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }


    private void grow() {
        Object[] growArr = new Object[this.capacity * 2];

        for (int i = 0; i < this.capacity; i++) {
            growArr[i] = this.items[i];
        }

        this.capacity *= 2;
        this.items = growArr;
    }
}
