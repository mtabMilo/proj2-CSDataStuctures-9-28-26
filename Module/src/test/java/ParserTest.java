import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ParserTest
{
    AST n1, n2, n3, n4, n5, n8, n16;

    @BeforeEach
    void setUp()
    {
        n1 = new AST.Num(1.0);
        n2 = new AST.Num(2.0);
        n3 = new AST.Num(3.0);
        n4 = new AST.Num(4.0);
        n5 = new AST.Num(5.0);
        n8 = new AST.Num(8.0);
        n16 = new AST.Num(16.0);
    }

    @Test
    void parseInfix()
    {
        assertEquals(n5, Parser.parseInfix("5"));

        assertEquals(new AST.Binop("+", n2, n3), Parser.parseInfix("2 + 3"));

        assertEquals(new AST.Binop("+", n2, new AST.Binop("*", n3, n4)),
                Parser.parseInfix("2 + 3 * 4"));

        assertEquals(new AST.Binop("+", new AST.Binop("*", n2, n3), n4),
                Parser.parseInfix("2 * 3 + 4"));

        assertEquals(new AST.Binop("*", new AST.Binop("+", n2, n3), n4),
                Parser.parseInfix("( 2 + 3 ) * 4"));

        assertEquals(new AST.Binop("*", new AST.Binop("+", n1, n2), new AST.Binop("+", n3, n4)),
                Parser.parseInfix("( 1 + 2 ) * ( 3 + 4 )"));

        assertEquals(new AST.Binop("+", n1, n2), Parser.parseInfix("( ( 1 + 2 ) )"));

        assertEquals(new AST.Binop("-", new AST.Binop("-", n8, n3), n2),
                Parser.parseInfix("8 - 3 - 2"));

        assertEquals(new AST.Binop("/", new AST.Binop("/", n16, n4), n2),
                Parser.parseInfix("16 / 4 / 2"));

        assertEquals(new AST.Binop("^", n2, new AST.Binop("^", n3, n2)),
                Parser.parseInfix("2 ^ 3 ^ 2"));

        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix(""));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("abc"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("+"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("1 +"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("1 + * 2"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( 1 + )"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( )"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("1 2"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("1 + 2 3"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( 1 + 2"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("1 + 2 )"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix(")"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( + )"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("+ +"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("1 + +"));
    }

    @Test
    void parsePostFix()
    {
        assertEquals(n5, Parser.parsePostFix("5"));

        assertEquals(new AST.Binop("+", n3, n4), Parser.parsePostFix("3 4 +"));

        assertEquals(new AST.Binop("-", n5, n3), Parser.parsePostFix("5 3 -"));

        assertEquals(new AST.Binop("+", n1, new AST.Binop("*", n2, n3)),
                Parser.parsePostFix("1 2 3 * +"));

        assertEquals(new AST.Binop("*", new AST.Binop("+", n1, n2), n3),
                Parser.parsePostFix("1 2 + 3 *"));

        assertEquals(new AST.Binop("^", n2, new AST.Binop("^", n3, n2)),
                Parser.parsePostFix("2 3 2 ^ ^"));

        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostFix(""));
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostFix("abc"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostFix("+"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostFix("1 +"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostFix("1 2"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parsePostFix("1 2 3 +"));
    }
}
