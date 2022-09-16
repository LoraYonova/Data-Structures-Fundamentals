package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {

    private static final int INITIAL_SIZE = 4;
    private Object[] elements;
    private int size;
//    private int capacity;

    public ArrayList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
//        this.capacity = INITIAL_SIZE;
    }

    @Override
    public boolean add(E element) {

        if (this.size == this.elements.length) {
            this.elements = resize();
        }

        this.elements[this.size++] = element;

        return true;
    }

    private Object[] resize() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    @Override
    public boolean add(int index, E element) {

        checkIndex(index);

        if (this.size == this.elements.length) {
            this.elements = resize();
        }

        insert(index);
        this.elements[index] = element;
        size++;
        return true;
    }

    private void insert(int index) {

        for (int i = this.elements.length - 1; i > index; i--) {

            this.elements[i] = this.elements[i - 1];
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %d for size: %d", index, this.size));
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldElement = this.get(index);
        this.elements[index] = element;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E element = get(index);
        this.elements[index] = null;
        this.size--;
        shift(index);
        ensureCapacity();
        return element;
    }

    private void shift(int index) {
        for (int i = index; i < this.elements.length - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private void ensureCapacity() {

        if (this.size < this.elements.length / 3) {
            this.elements = shrink();
        }
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {

            for (int i = 0; i < this.size; i++) {

                if (this.elements[i].equals(element)) {
                    return i;
                }

            }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        return Arrays.asList(this.elements).contains(element);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }
}
