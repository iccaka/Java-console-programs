import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        Class blackBoxClass = BlackBoxInt.class;
        Constructor intConstructor = blackBoxClass.getDeclaredConstructor(int.class);
        intConstructor.setAccessible(true);
        BlackBoxInt blackBoxInt = (BlackBoxInt) intConstructor.newInstance(0);

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        while(!line.equals("END")){
            String[] tokens = line.split("_");

            Method method = blackBoxClass.getDeclaredMethod(tokens[0], int.class);
            doSomething(Integer.valueOf(tokens[1]), method, blackBoxInt);

            System.out.println(getInnerValue(blackBoxInt));

            line = sc.nextLine();
        }

    }

    static void doSomething(int value, Method method, BlackBoxInt blackBoxInt) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(blackBoxInt, value);
    }

    static int getInnerValue(BlackBoxInt blackBoxInt) throws IllegalAccessException, NoSuchFieldException {
        Field innerValue = blackBoxInt.getClass().getDeclaredField("innerValue");
        innerValue.setAccessible(true);
        return (int) innerValue.get(blackBoxInt);
    }
}

public class BlackBoxInt {

    private static final int DEFFAULT_VALUE = 0;

    private int innerValue;

    private BlackBoxInt(int innerValue) {
        this.innerValue = innerValue;
    }

    private BlackBoxInt() {
        this.innerValue = DEFFAULT_VALUE;
    }

    private void add(int addend) {
        this.innerValue += addend;
    }

    private void subtract(int subtrahend) {
        this.innerValue -= subtrahend;
    }

    private void multiply(int multiplier) {
        this.innerValue *= multiplier;
    }

    private void divide(int divider) {
        this.innerValue /= divider;
    }

    private void leftShift(int shifter) {
        this.innerValue <<= shifter;
    }

    private void rightShift(int shifter) {
        this.innerValue >>= shifter;
    }
}
