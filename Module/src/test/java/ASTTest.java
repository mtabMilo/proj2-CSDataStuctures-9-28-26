import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ASTTest
{
    AST zero, one, two, three, four, five;

    @BeforeEach
    void setUp()
    {
        zero = new AST.num(0.0);
        one = new AST.num(1.0);
        two = new AST.num(2.0);
        three = new AST.num(3.0);
        four = new AST.num(4.0);
        five = new AST.num(5.0);
    }

    @Test
    void num()
    {

    }

    @Test
    void NumEval()
    {
        assertEquals(5.0, five.eval());
    }

    @Test
    void BinopEval()
    {
        assertEquals(5.0, new AST.Binop("+", two, three).eval());
        assertEquals(2.0, new AST.Binop("-", five, three).eval());
        assertEquals(6.0, new AST.Binop("*", two, three).eval());
        assertEquals(2.5, new AST.Binop("/", five, two).eval());
        assertEquals(8.0, new AST.Binop("^", two, three).eval());

        AST nested = new AST.Binop("*",
                new AST.Binop("+", one, two),
                new AST.Binop("+", three, four));
        assertEquals(21.0, new AST.Binop("*",
                                        new AST.Binop("+", one, two),
                                        new AST.Binop("+", three, four)).eval());

        assertThrows(IllegalArgumentException.class,
                () -> new AST.Binop("%", two, three).eval());
    }
}