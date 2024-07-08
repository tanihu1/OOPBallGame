package game;

/**
 * Simple counter class.
 */
public class Counter {
    private int count = 0;

    /**
     * Increases counter by given number.
     *
     * @param number number to increase counter by.
     */
    public void increase(int number) {
        count += number;
    }

    /**
     * Decreases counter by given number.
     *
     * @param number number to decrease counter by.
     */
    public void decrease(int number) {
        if (isEmpty()) {
            return;
        }
        count -= number;
    }

    /**
     * Returns the current value.
     *
     * @return current value.
     */
    public int getValue() {
        return count;
    }

    /**
     * Returns true if counter is 0, false otherwise.
     *
     * @return true if counter is 0, false otherwise.
     */
    public Boolean isEmpty() {
        return count == 0;
    }
}
