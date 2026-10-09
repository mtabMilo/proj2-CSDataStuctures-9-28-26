import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO: write unit tests for ArrayStack
class ArrayStackTest
{
    ArrayStack<String> empty, one, several, full, grown, withNull;

    @BeforeEach
    void setUp()
    {
        empty = new ArrayStack<String>();

        one = new ArrayStack<String>();
        one.push("a");

        several = new ArrayStack<String>();
        several.push("a");
        several.push("b");
        several.push("c");

        full = new ArrayStack<String>();
        for (int i = 0; i < 10; i++)
        {
            full.push("s" + i);
        }

        grown = new ArrayStack<String>();
        for (int i = 0; i < 25; i++)
        {
            grown.push("s" + i);
        }

        withNull = new ArrayStack<String>();
        withNull.push(null);
    }

    @Test
    void isEmpty()
    {
        assertTrue(empty.isEmpty());
        assertFalse(one.isEmpty());
        assertFalse(withNull.isEmpty());

        one.pop();
        assertTrue(one.isEmpty());
    }

    @Test
    void size()
    {
        assertThrows(IllegalArgumentException.class, () -> empty.size());
        assertEquals(1, one.size());
        assertEquals(3, several.size());
        assertEquals(10, full.size());
        assertEquals(25, grown.size());

        several.push("d");
        assertEquals(4, several.size());
        several.pop();
        several.pop();
        assertEquals(2, several.size());
    }

    @Test
    void push()
    {
        empty.push("x");
        assertEquals("x", empty.peek());
        assertEquals(1, empty.size());

        full.push("extra");
        assertEquals(11, full.size());
        assertEquals("extra", full.peek());

        assertEquals("extra", full.pop());
        for (int i = 9; i >= 0; i--)
        {
            assertEquals("s" + i, full.pop());
        }
        assertTrue(full.isEmpty());

        for (int i = 24; i >= 0; i--)
        {
            assertEquals("s" + i, grown.pop());
        }
        assertTrue(grown.isEmpty());

        withNull.push(null);
        assertEquals(2, withNull.size());
        assertNull(withNull.peek());
    }

    @Test
    void pop()
    {
        assertThrows(IllegalArgumentException.class, () -> empty.pop());

        assertEquals("a", one.pop());
        assertThrows(IllegalArgumentException.class, () -> one.pop());

        assertEquals("c", several.pop());
        assertEquals("b", several.pop());
        assertEquals("a", several.pop());

        assertNull(withNull.pop());
        assertTrue(withNull.isEmpty());

        one.push("z");
        assertEquals("z", one.peek());
        assertEquals(1, one.size());
    }

    @Test
    void peek()
    {
        assertThrows(IllegalArgumentException.class, () -> empty.peek());

        assertEquals("c", several.peek());
        assertEquals("c", several.peek());
        assertEquals(3, several.size());

        assertNull(withNull.peek());
        assertEquals(1, withNull.size());
    }
}
