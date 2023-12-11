import java.util.List;
import java.util.Arrays;

interface SinglyLinkedListCollection<T> {
    void add(T item);
    void remove(T item);
    boolean contains(T item);
    int size();
    void clear();
}

class Node<T> {
    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
    }
}

class SinglyLinkedList<T> implements SinglyLinkedListCollection<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
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
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void remove(T item) {
        if (head == null) {
            return;
        }

        if (head.data.equals(item)) {
            head = head.next;
            size--;
            return;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(item)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public boolean contains(T item) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            result.append(current.data);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }
}

public class App{
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        System.out.println("list1: " + list1);

        SinglyLinkedList<String> list2 = new SinglyLinkedList<>("A");
        list2.add("B");
        list2.add("C");

        System.out.println("list2: " + list2);

        List<Double> values = Arrays.asList(1.1, 2.2, 3.3);
        SinglyLinkedList<Double> list3 = new SinglyLinkedList<>(values);

        System.out.println("list3: " + list3);
    }
}