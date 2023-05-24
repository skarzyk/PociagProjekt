package Exceptions;

public class SameThing extends Exception{
    public SameThing() {
        super("Nie mozna dodac dwa razy tej samej rzeczy");
    }
}
