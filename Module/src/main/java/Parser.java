// Parsers for postfix and infix expressions
public class Parser
{
    public static AST parsePostFix(String val)
    {
        ArrayStack<AST> stack = new ArrayStack<AST>();
        String[] tokens = val.split("\\s+");

        for (String s : tokens)
        {
            if (!isOperator(s))
            {
                stack.push(new AST.num(Double.parseDouble(s)));
            }
            else
            {
                stack.push(new AST.Binop(s, stack.pop(), stack.pop()));
            }
        }
        return stack.pop();
    }

    private static boolean isOperator(String thing)
    {

        return thing.matches("[+\\-*/^]");
    }
}