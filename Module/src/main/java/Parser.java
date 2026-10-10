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
                stack.push(new Binop(s, left, right));
            }
            else if (isNum(s))
            {
                stack.push(new Num(Double.parseDouble(s)));
            }
            else
            {
                throw new IllegalArgumentException("invalid token");
            }
        }

        AST returns = stack.pop();
        if (!stack.isEmpty())
        {
            throw new IllegalArgumentException("too many operands");
        }
        return returns;
    }

    private static int precedence(String operator)
    {
        if (operator.equals("^")) {return 3;}
        if (operator.equals("*") || operator.equals("/")) {return 2;}
        return 1;
    }

    public static AST parseInfix(String val)
    {
        ArrayStack<AST> operandStack = new ArrayStack<AST>();
        ArrayStack<String> operatorStack = new ArrayStack<String>();
        AST right;
        AST left;
        int parenOpen = 0;
        String[] tokens = val.split("\\s+");


        for (int i = 0; i < tokens.length; i++)
        {
            String s = tokens[i];
            if (isNum(s))
            {
                operandStack.push(new Num(Double.parseDouble(s)));
            }
            else if (s.equals("("))
            {
                operatorStack.push(s);
                parenOpen++;
            }
            else if (s.equals(")"))
            {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("("))
                {
                    if (operandStack.isEmpty())
                    {
                        throw new IllegalArgumentException("insufficient operands");
                    }

                    right = operandStack.pop();

                    if (operandStack.isEmpty())
                    {
                        throw new IllegalArgumentException("insufficient operands");
                    }

                    left = operandStack.pop();
                    operandStack.push(new Binop(operatorStack.pop(), left, right));
                }
                if (operatorStack.isEmpty())
                {
                    throw new IllegalArgumentException("mismatched close paren");
                }
                operatorStack.pop();
                parenOpen--;
            }
            else if (isOperator(s))
            {
                while (!operatorStack.isEmpty()
                        && !operatorStack.peek().equals("(")
                        && (precedence(operatorStack.peek()) > precedence(s)
                        || (precedence(operatorStack.peek()) == precedence(s) && !s.equals("^"))))
                {
                    if (operandStack.isEmpty())
                    {
                        throw new IllegalArgumentException("insufficient operands");
                    }

                    right = operandStack.pop();

                    if (operandStack.isEmpty())
                    {
                        throw new IllegalArgumentException("insufficient operands");
                    }

                    left = operandStack.pop();
                    operandStack.push(new Binop(operatorStack.pop(), left, right));
                }
                operatorStack.push(s);
            }
            else if(s.isEmpty())
            {
                throw new IllegalArgumentException("empty input");
            }
            else
            {
                throw new IllegalArgumentException("invalid token");
            }
        }

        if (parenOpen > 0)
        {
            throw new IllegalArgumentException("mismatched open paren");
        }

        while (!operatorStack.isEmpty())
        {
            if (operandStack.isEmpty())
            {
                throw new IllegalArgumentException("insufficient operands");
            }

            right = operandStack.pop();

            if (operandStack.isEmpty())
            {
                throw new IllegalArgumentException("insufficient operands");
            }

            left = operandStack.pop();
            operandStack.push(new Binop(operatorStack.pop(), left, right));
        }

        if (operandStack.isEmpty())
        {
            throw new IllegalArgumentException("insufficient operands");
        }

        AST returns = operandStack.pop();

        if (!operandStack.isEmpty())
        {
            throw new IllegalArgumentException("too many operands");
        }

        return returns;
    }

    private static boolean isOperator(String operands)
    {

        return operands.matches("[+\\-*/^]");
    }

    private static boolean isNum(String operands)
    {

        return operands.matches("-?[0-9]+");
    }
}