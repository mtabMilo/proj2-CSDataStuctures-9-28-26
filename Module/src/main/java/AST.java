// AST

interface AST
{
    public double eval();

    public record Num(double num) implements AST
    {
        public double eval() {return num;}
    }

    public record Binop(String operator, AST left, AST right) implements AST
    {
        public double eval()
        {
            double l = left.eval();
            double r = right.eval();

            switch (operator)
            {
                case "+":
                    return l + r;
                case "-":
                    return l - r;
                case "*":
                    return l * r;
                case "/":
                    return l / r;
                case "^":
                    return Math.pow(l, r);
                default:
                    throw new IllegalArgumentException("Invalid Operator");
            }
        }
    }
}