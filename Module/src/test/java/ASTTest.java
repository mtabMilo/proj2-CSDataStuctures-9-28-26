import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ASTTest
{
    AST zero, one, two, three, four, five;

    @BeforeEach
    void setUp()
    {
        zero = new AST.Num(0.0);
        one = new AST.Num(1.0);
        two = new AST.Num(2.0);
        three = new AST.Num(3.0);
        four = new AST.Num(4.0);
        five = new AST.Num(5.0);
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

    @Test
    void numEvalsToItsValue() {
        assertEquals(3.0, new AST.Num(3.0).eval(), 1e-9);
        assertEquals(3.0, new AST.Num(3.0).value(), 1e-9);
    }

    @Test
    void binopAdds() {
        assertEquals(5.0, new AST.Binop("+", new AST.Num(2), new AST.Num(3)).eval(), 1e-9);
    }
}