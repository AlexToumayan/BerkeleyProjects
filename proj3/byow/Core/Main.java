package byow.Core;


public class Main {
    public static void main (String[] args) {
        Engine engine = new Engine();

        if (args.length > 2) {
            System.out.println("Can only have two arguments - the flag and input string");
            System.exit(0);
        } else if (args.length == 2 && args[0].equals("-s")) {
            engine.interactWithInputString(args[1]);
            System.out.println(engine.toString());
        } else {
            engine.interactWithKeyboard();
        }
    }
}
