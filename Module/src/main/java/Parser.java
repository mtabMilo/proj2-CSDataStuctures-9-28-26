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


        for (int i = 0; i < tokens.length; i++)
        {
            String s = tokens[i];
            if (isOperator(s))
            {
                if (stack.isEmpty())
                {
                    throw new IllegalArgumentException("insufficient operands");
                }

                AST right = stack.pop();

                if (stack.isEmpty())
                {
                    throw new IllegalArgumentException("insufficient operands");
                }

                AST left = stack.pop();
                stack.push(new AST.Binop(s, left, right));
            }
            else if (isNum(s))
            {
                stack.push(new AST.num(Double.parseDouble(s)));
            }
            else
            {
                throw new IllegalArgumentException("invalid token");
            }
        }

        if (stack.isEmpty())
        {
            throw new IllegalArgumentException("insufficient operands");
        }

        AST returns = stack.pop();
        if (!stack.isEmpty())
        {
            throw new IllegalArgumentException("too many operands");
        }
        return returns;
    }

    public static AST parseInfix(String val)
    {
        ArrayStack<AST> operandStack = new ArrayStack<AST>();
        ArrayStack<String> operatorStack = new ArrayStack<String>();
        String[] tokens = val.split("\\s+");


        for (int i = 0; i < tokens.length; i++)
        {
            String s = tokens[i];
            if (isNum(s))
            {
                operandStack.push(new AST.num(Double.parseDouble(s)));
            }
            else if (s.equals("("))
            {
                operatorStack.push(s);
            }
            else if (s.equals(")"))
            {
                String change = tokens[i];
                AST right;
                AST left;
                while (!change.equals("("))
                {
                    right = operandStack.pop();
                    left = operandStack.pop();
                    operandStack.push(new AST.Binop(operatorStack.pop(), left, right));
                    change = tokens[i-1];
                    operatorStack.pop();
                }
            }
            else if (isOperator(s))
            {

            }
        }

        return;
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