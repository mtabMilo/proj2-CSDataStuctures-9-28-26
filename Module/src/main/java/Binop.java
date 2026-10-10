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
