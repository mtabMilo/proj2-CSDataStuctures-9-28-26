// ArrayStack

import java.util.ArrayList;
import java.util.List;

public class ArrayStack
{
    String[] theStack = new String[10];
    int tail = 0; // the index after the latest item in index 1, 2, 3, null, tail = 4

    public void push(String val)
    {
        if(theStack.length == tail)
        {
            theStack = java.util.Arrays.copyOf(theStack, theStack.length*2);
        }
        theStack[tail] = val;
        tail++;
    }

    public String pop()
    {
        if (theStack == null || tail == 0)
        {
            throw new IllegalArgumentException("The Stack is empty!");
        }
        String returns = theStack[tail-1];
        theStack[tail-1] = null;
        return returns;
    }

    public String peek()
    {
        if (theStack == null || tail == 0)
        {
            throw new IllegalArgumentException("The Stack is empty!");
        }
        return  theStack[tail-1];
    }

    public boolean isEmpty()
    {
        return theStack == null || tail == 0;
    }

    public int size()
    {
        if (theStack == null || tail == 0)
        {
            throw new IllegalArgumentException("The Stack is empty!");
        }
        return tail-1;
    }
}
