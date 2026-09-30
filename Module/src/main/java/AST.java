// AST

interface AST
{
    public double eval();

    public record num(double num) implements AST
    {
        public double eval() {return num;}
    }

    public record Binop(String operator, AST left, AST right) implements AST
    {
        public double eval()
        {
            return 0;
        }
    }
}