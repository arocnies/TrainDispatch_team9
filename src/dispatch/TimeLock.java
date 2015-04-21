package dispatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aaron on 4/20/15.
 */

public class TimeLock <T extends Train> {
    final Map<Integer, T> slots;

    public TimeLock() {
        slots = new HashMap<>();
    }

    /**
     * Acquires the lock for a range of time.
     * @param start First slot to lock.
     * @param amount Number of slots to lock.
     * @param t Lock holder.
     * @return Number of values changed.
     * @throws dispatch.TimeLock.InexcusableLockException
     */
    public int acquireLock(int start, int amount, T t) throws InexcusableLockException {
        return setLock(start, amount, null, t);
    }

    /**
     * Releases the lock for a range of time.
     * @param start First slot to release.
     * @param amount Number of slots to release.
     * @param t Lock holder.
     * @return Number of values changed.
     * @throws InexcusableLockException
     */
    public int releaseLock(int start, int amount, T t) throws InexcusableLockException {
        return setLock(start, amount, t, null);
    }

    /**
     * Returns if the lock is free in a specific range.
     * @param start First slot.
     * @param amount Number of slots.
     * @return True if already lock, false if lock is free.
     */
    public boolean isLocked(int start, int amount) {
        for (int i = start; i < start + amount; i++) {
            if (slots.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets all the locks for the holder in a range to a specific value.
     * @param start First slot.
     * @param amount Number of slots.
     * @param holder
     * @param value
     * @return Number of values changed.
     * @throws InexcusableLockException
     */
    private int setLock(int start, int amount, T holder, T value) throws InexcusableLockException {
        prepLock(start + amount);
        int count = 0;
        for (int i = start; i < start + amount; i++) {
            if (slots.get(i) == holder) {
                count++;
                slots.put(i, value);
            }
            else {
                throw new InexcusableLockException();
            }
        }
        return count;
    }

    /**
     * Prepares the lock for operations to a specific limit.
     * @param limit
     */
    private void prepLock(int limit) {
        while (slots.size() < limit) {
            slots.put(slots.size(), null);
        }
    }

    private class InexcusableLockException extends Exception {
    }
}
