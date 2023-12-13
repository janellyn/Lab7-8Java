package com.example;

import java.util.Iterator;
import java.util.NoSuchElementException;

interface List<T> {

    int size();

    boolean isEmpty();

    boolean contains(T item);

    T get(int index) throws SinglyLinkedListException;

    void add(T item);

    void add(int index, T item) throws SinglyLinkedListException;

    T remove(int index) throws SinglyLinkedListException;

    void clear();

    Iterator<T> iterator();
}

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
    public boolean contains(T item) {
        Node<T> current = this.head;
        while (current != null) {
            if (current.data == item) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public T get(int index) throws SinglyLinkedListException {
        if (index < 0 || index >= this.size) {
            throw new SinglyLinkedListException("Index out of bounds.");
        }

        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (this.head == null) {
            this.head = newNode;
        } else {
            Node<T> current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        this.size++;
    }

    @Override
    public void add(int index, T item) throws SinglyLinkedListException {
        if (index < 0 || index > this.size) {
            throw new SinglyLinkedListException("Index out of bounds.");
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(item);
            newNode.next = this.head;
            this.head = newNode;
        } else {
            Node<T> current = this.head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(item);
            newNode.next = current.next;
            current.next = newNode;
        }
        this.size++;
    }

    @Override
    public T remove(int index) throws SinglyLinkedListException {
        Node<T> removedNode;
        if (index < 0 || index >= this.size) {
            throw new SinglyLinkedListException("Index out of bounds.");
        }

        if (index == 0) {
            removedNode = this.head;
            this.head = removedNode.next;
        } else {
            Node<T> current = this.head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedNode = current.next;
            current.next = removedNode.next;
        }
        this.size--;
        return removedNode.data;
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    public int indexOf(T item) {
        Node<T> current = this.head;
        int index = 0;
        while (current != null) {
            if (current.data == item) {
                return index;
            }
            index++;
            current = current.next;
        }
        return -1;
    }

    public int lastIndexOf(T item) {
        Node<T> current = this.head;
        int index = this.size - 1;
        while (current != null) {
            if (current.data == item) {
                return index;
            }
            index--;
            current = current.next;
        }
        return -1;
    }

    public void set(int index, T item) throws SinglyLinkedListException {
        if (index < 0 || index >= this.size) {
            throw new SinglyLinkedListException("Index out of bounds.");
        }

        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = item;
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

    public T removeFirst() throws SinglyLinkedListException {
        if (isEmpty()) {
            throw new SinglyLinkedListException("List is empty");
        }

        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public T removeLast() throws SinglyLinkedListException {
        if (isEmpty()) {
            throw new SinglyLinkedListException("List is empty");
        }

        if (size == 1) {
            return removeFirst();
        }

        Node<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }

        T data = current.next.data;
        current.next = null;
        size--;
        return data;
    }

    public T[] toArray(T[] a) {
        if (a.length < this.size) {
            a = (T[]) new Object[this.size];
        }

        Node<T> current = this.head;
        for (int i = 0; i < this.size; i++) {
            a[i] = current.data;
            current = current.next;
        }

        return a;
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
}

public class Main {
    public static void main(String[] args) throws SinglyLinkedListException {

        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        System.out.println("Пустий список? " + list.isEmpty());

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(13);

        System.out.println("Список: " + list);
        System.out.println("Пустий список? " + list.isEmpty());
        System.out.println("Елемент по індексу 1: " + list.get(1));

        try {
            list.set(2, 5);
            System.out.println("Список: " + list);
        } catch (SinglyLinkedListException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Видалений перший елемент: " + list.removeFirst());
            System.out.println("Список: " + list);
        } catch (SinglyLinkedListException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Видалений останній елемент: " + list.removeLast());
            System.out.println("Список: " + list);
        } catch (SinglyLinkedListException e) {
            System.out.println(e.getMessage());
        }

        list.reverse();
        System.out.println("Реверсирований список: " + list);
    }
}