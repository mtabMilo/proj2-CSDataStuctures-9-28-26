public record Num(double num) implements AST
{
    public double eval() {return num;}
}
