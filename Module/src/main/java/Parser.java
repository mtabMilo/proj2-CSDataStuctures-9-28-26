// Parsers for postfix and infix expressions
public class Parser
{
    public static AST parsePostFix(String val)
    {
        if (val.isEmpty())
        {
            throw new IllegalArgumentException("empty input");
        }
        ArrayStack<AST> stack = new ArrayStack<AST>();
        String[] tokens = val.split("\\s+");
        int countNum = 0;
        int countOp = 0;


        for (int i = 0; i < tokens.length; i++)
        {
            String s = tokens[i];
            if (isOperator(s))
            {
                countOp++;
                stack.push(new AST.Binop(s, stack.pop(), stack.pop()));
                if (i+1 < tokens.length && !isOperator(tokens[i+1]))
                {
                    if (countNum-1 != countOp)
                    {
                        throw new IllegalArgumentException("too many operands");
                    }
                    else
                    {
                        countOp = 0;
                        countNum = 0;
                    }
                }
                else if (i == tokens.length-1)
                {
                    if (countNum-1 != countOp)
                    {
                        throw new IllegalArgumentException("too many operands");
                    }
                }
            }
            else if (isNum(s))
            {
                countNum++;
                stack.push(new AST.num(Double.parseDouble(s)));
            }
            else
            {
                throw new IllegalArgumentException("invalid token");
            }
        }
        return stack.pop();
    }

    private static boolean isOperator(String operands)
    {

        return operands.matches("[+\\-*/^]");
    }

    private static boolean isNum(String operands)
    {

        return operands.matches("[0-9]+");
    }
}