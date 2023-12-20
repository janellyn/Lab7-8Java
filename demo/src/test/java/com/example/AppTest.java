package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

class AppTest {
    @Test
    void testSize_EmptyList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertEquals(0, list.size());
    }

    @Test
    void testSize_NonEmptyList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
    }

    @Test
    void testIsEmpty_EmptyList() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        assertTrue(list.isEmpty());
    }

    @Test
    void testIsEmpty_NonEmptyList() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("a");
        assertFalse(list.isEmpty());
    }

    @Test
    void testContains_Found() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(10);
        list.add(20);
        assertTrue(list.contains(20));
    }

    @Test
    void testContains_NotFound() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("apple");
        list.add("banana");
        assertFalse(list.contains("orange"));
    }

    @Test
    void testGet_ValidIndex() throws SinglyLinkedListException {
        SinglyLinkedList<Character> list = new SinglyLinkedList<>();
        list.add('a');
        list.add('b');
        list.add('c');
        assertEquals('b', list.get(1));
    }

    @Test
    void testAdd_SingleElement() throws SinglyLinkedListException {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("hello");
        assertEquals(1, list.size());
        assertEquals("hello", list.get(0));
    }

    @Test
    void testAdd_MultipleElements() throws SinglyLinkedListException {
        SinglyLinkedList<Double> list = new SinglyLinkedList<>();
        list.add(1.0);
        list.add(2.5);
        list.add(3.75);
        assertEquals(3, list.size());
        assertEquals(3.75, list.get(0), 0.001);
        assertEquals(1.0, list.get(2), 0.001);
    }

    @Test
    void testAdd_InvalidIndex() throws NullPointerException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertThrowsExactly(NullPointerException.class, () -> {
            list.add(5, 10);
        }, "Index out of bounds");
    }

    @Test
    void testAdd_AtIndexStart() throws SinglyLinkedListException {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("b");
        list.add(0, "a");
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
    }

    @Test
    void testAdd_AtIndexEnd() throws SinglyLinkedListException {
        SinglyLinkedList<Character> list = new SinglyLinkedList<>();
        list.add('x');
        list.add(1, 'y');
        list.add(2, 'z');
        assertEquals(3, list.size());
        assertEquals('z', list.get(2));
    }

    @Test
    void testRemove_ValidIndex() throws SinglyLinkedListException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int removed = list.remove(1);
        assertEquals(2, list.size());
        assertEquals(2, removed);
    }

    @Test
    void testRemove_InvalidIndex() throws IndexOutOfBoundsException {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("hello");
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        }, "Index out of bounds");
    }

    @Test
    void testIndexOf_Found() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(1, list.indexOf(2));
    }

    @Test
    void testIndexOf_NotFound() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("a");
        list.add("b");
        assertEquals(-1, list.indexOf("c"));
    }

    @Test
    void testlastIndexOf_Found() {
        SinglyLinkedList<Character> list = new SinglyLinkedList<>();
        list.add('a');
        list.add('b');
        list.add('a');
        assertEquals(2, list.lastIndexOf('a'));
    }

    @Test
    void testlastIndexOf_NotFound() {
        SinglyLinkedList<Double> list = new SinglyLinkedList<>();
        list.add(1.0);
        list.add(2.5);
        assertEquals(-1, list.lastIndexOf(3.75));
    }

    @Test
    void testSet_ValidIndex() throws SinglyLinkedListException {
        SinglyLinkedList<Boolean> list = new SinglyLinkedList<>();
        list.add(true);
        list.add(false);
        list.set(1, true);
        assertEquals(true, list.get(1));
    }

    @Test
    void testReverse_EmptyList() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.reverse();
        assertEquals(0, list.size());
    }

    @Test
    void testReverse_SingleElementList() throws SinglyLinkedListException {
        SinglyLinkedList<Character> list = new SinglyLinkedList<>();
        list.add('a');
        list.reverse();
        assertEquals(1, list.size());
        assertEquals('a', list.get(0));
    }

    @Test
    void testReverse_MultipleElementList() throws SinglyLinkedListException {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.reverse();
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testToArray_EmptyList() {
        SinglyLinkedList<Double> list = new SinglyLinkedList<>();
        Double[] array = list.toArray(new Double[0]);
        assertEquals(0, array.length);
    }

    @Test
    void testToArray_SingleElementList() {
        SinglyLinkedList<Character> list = new SinglyLinkedList<>();
        list.add('a');
        Character[] array = list.toArray(new Character[1]);
        assertEquals(1, array.length);
        assertEquals('a', array[0]);
    }

    @Test
    void testToArray_MultipleElementList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Integer[] array = list.toArray(new Integer[3]);
        assertEquals(3, array.length);
        assertEquals(3, array[0]);
        assertEquals(2, array[1]);
        assertEquals(1, array[2]);
    }

    @Test
    void testHasNext_EmptyList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        Iterator<Integer> iterator = list.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testHasNext_SingleElementList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testHasNext_MultipleElementList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testNext_EmptyList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        Iterator<Integer> iterator = list.iterator();
        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    @Test
    void testNext_SingleElementList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        Iterator<Integer> iterator = list.iterator();
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testNext_MultipleElementList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        assertEquals(3, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }
}