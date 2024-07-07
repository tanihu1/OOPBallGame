package game;

public class Counter {
    private int count = 0;

    // add number to current count.
    public void increase(int number) {
        count += number;
    }

    // subtract number from current count.
    public void decrease(int number) {
        if (isEmpty()) {
            return;
        }
        count -= number;
    }

    // get current count.
    public int getValue() {
        return count;
    }

    public Boolean isEmpty() {
        return count == 0;
    }
}
