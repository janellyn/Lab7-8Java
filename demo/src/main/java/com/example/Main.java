package com.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.lang.reflect.Array;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class Node<T> {

    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.next = null;
    }
}

class SinglyLinkedListException extends Exception {
    SinglyLinkedListException(String message) {
        super(message);
    }
}

class SinglyLinkedList<T> implements List<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public SinglyLinkedList(T item) {
        head = new Node<>(item);
        size = 1;
    }

    public SinglyLinkedList(Iterable<T> collection) {
        head = null;
        size = 0;
        for (T item : collection) {
            add(item);
        }
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
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    public void reverse() {
        Node<T> current = this.head;
        Node<T> previous = null;
        while (current != null) {
            Node<T> next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        this.head = previous;
    }

    @Override
    public Iterator<T> iterator() {
        return new SinglyLinkedListIterator(this);
    }

    private class SinglyLinkedListIterator<T> implements Iterator<T> {

        private Node<T> current;

        public SinglyLinkedListIterator(SinglyLinkedList<T> list) {
            this.current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T data = current.data;
            current = current.next;
            return data;
        }
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder("[");
            Iterator<T> iterator = this.iterator();
            while (iterator.hasNext()) {
                builder.append(iterator.next()).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("]");
            return builder.toString();
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        int i = 0;
        for (Node<T> current = this.head; current != null; current = current.next) {
            array[i++] = current.data;
        }
        return array;
    }

    @Override
    public boolean remove(Object o) {
        if (this.head == null) {
            return false;
        }

        if (o == null) {
            for (Node<T> current = this.head; current != null; current = current.next) {
                if (current.data == null) {
                    this.remove(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = this.head; current != null; current = current.next) {
                if (o.equals(current.data)) {
                    this.remove(current);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.addAll(this.size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > this.size) {
            try {
                throw new SinglyLinkedListException("Index out of bounds");
            } catch (SinglyLinkedListException e) {
                e.printStackTrace();
            }
        }

        if (c.isEmpty()) {
            return false;
        }

        for (T item : c) {
            this.add(index++, item);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Node<T> current = this.head; current != null;) {
            if (c.contains(current.data)) {
                Node<T> next = current.next;
                this.remove(current);
                current = next;
                modified = true;
            } else {
                current = current.next;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (Node<T> current = this.head; current != null;) {
            if (!c.contains(current.data)) {
                Node<T> next = current.next;
                this.remove(current);
                current = next;
                modified = true;
            } else {
                current = current.next;
            }
        }
        return modified;
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > this.size) {
            try {
                throw new SinglyLinkedListException("Index out of bounds");
            } catch (SinglyLinkedListException e) {
                e.printStackTrace();
            }
        }
        return (ListIterator<T>) new SinglyLinkedListIterator(this);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > this.size || fromIndex > toIndex) {
            try {
                throw new SinglyLinkedListException("Invalid sublist range");
            } catch (SinglyLinkedListException e) {
                e.printStackTrace();
            }
        }

        SinglyLinkedList<T> subList = new SinglyLinkedList<>();
        for (Node<T> current = this.getNodeAt(fromIndex); current != null && toIndex > 0; current = current.next) {
            subList.add(current.data);
            toIndex--;
        }
        return subList;
    }

    private Node<T> getNodeAt(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        for (Node<T> current = this.head; current != null; current = current.next) {
            if (o == null ? current.data == null : o.equals(current.data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        }

        int i = 0;
        for (Node<T> current = (Node<T>) this.head; current != null; current = current.next) {
            a[i++] = current.data;
        }

        if (a.length > this.size) {
            a[this.size] = null;
        }

        return a;
    }

    @Override
    public boolean add(T e) {
        Node<T> newNode = new Node<>(e);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            try {
                throw new SinglyLinkedListException("Index out of bounds");
            } catch (SinglyLinkedListException e) {
                e.printStackTrace();
            }
        }

        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= this.size) {
            try {
                throw new SinglyLinkedListException("Index out of bounds");
            } catch (SinglyLinkedListException e) {
                e.printStackTrace();
            }
        }

        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T oldData = current.data;
        current.data = element;
        return oldData;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > this.size) {
            try {
                throw new SinglyLinkedListException("Index out of bounds");
            } catch (SinglyLinkedListException e) {
                e.printStackTrace();
            }
        }

        if (index == 0) {
            this.add(element);
            return;
        }

        Node<T> newNode = new Node<>(element);
        Node<T> current = this.head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        this.size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        T data;
        if (index == 0) {
            data = this.head.data;
            this.head = this.head.next;
        } else {
            Node<T> current = this.head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            data = current.next.data;
            current.next = current.next.next;
        }
        this.size--;
        return data;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node<T> current = this.head; current != null; current = current.next) {
            if (o == null ? current.data == null : o.equals(current.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        int index = 0;
        for (Node<T> current = this.head; current != null; current = current.next) {
            if (o == null ? current.data == null : o.equals(current.data)) {
                lastIndex = index;
            }
            index++;
        }
        return lastIndex;
    }
}

public class Main {
    public static void main(String[] args) throws SinglyLinkedListException {

        List<Integer> list = new SinglyLinkedList<>();
        System.out.println("Пустий список? " + list.isEmpty());

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(13);

        System.out.println("Список: " + list);
        System.out.println("Пустий список: " + list.isEmpty());
        System.out.println("Елемент по індексу 1: " + list.get(1));

        list.set(2, 5);
        System.out.println("Список: " + list);

        System.out.println("Видалений перший елемент: " + list.removeFirst());
        System.out.println("Список: " + list);

        System.out.println("Видалений останній елемент: " + list.removeLast());
        System.out.println("Список: " + list);
    }
}