import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ASTTest
{
    AST zero, one, two, three, four, five;

    @BeforeEach
    void setUp()
    {
        zero = new Num(0.0);
        one = new Num(1.0);
        two = new Num(2.0);
        three = new Num(3.0);
        four = new Num(4.0);
        five = new Num(5.0);
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
        assertEquals(5.0, new Binop("+", two, three).eval());
        assertEquals(2.0, new Binop("-", five, three).eval());
        assertEquals(6.0, new Binop("*", two, three).eval());
        assertEquals(2.5, new Binop("/", five, two).eval());
        assertEquals(8.0, new Binop("^", two, three).eval());

        AST nested = new Binop("*",
                new Binop("+", one, two),
                new Binop("+", three, four));
        assertEquals(21.0, new Binop("*",
                                        new Binop("+", one, two),
                                        new Binop("+", three, four)).eval());

        assertThrows(IllegalArgumentException.class,
                () -> new Binop("%", two, three).eval());
    }

    @Test
    void numEvalsToItsValue() {
        assertEquals(3.0, new Num(3.0).eval(), 1e-9);
        assertEquals(3.0, new Num(3.0).num(), 1e-9);
    }

    @Test
    void binopAdds() {
        assertEquals(5.0, new Binop("+", new Num(2), new Num(3)).eval(), 1e-9);
    }
}