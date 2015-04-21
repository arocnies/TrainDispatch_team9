package dispatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aaron on 4/20/15.
 */

public class TimeLock <T> {
    final Map<Integer, T> slots;

    public TimeLock() {
        slots = new HashMap<>();
    }

    /**
     * Acquires the lock for a range of time.
     * @param start First slot to lock.
     * @param slots Number of slots to lock.
     * @param t Lock holder.
     * @return Number of values changed.
     * @throws dispatch.TimeLock.InexcusableLockException
     */
    public int acquireLock(int start, int slots, T t) throws InexcusableLockException {
        return setLock(start, slots, null, t);
    }

    /**
     * Releases the lock for a range of time.
     * @param start First slot to release.
     * @param slots Number of slots to release.
     * @param t Lock holder.
     * @return Number of values changed.
     * @throws InexcusableLockException
     */
    public int releaseLock(int start, int slots, T t) throws InexcusableLockException {
        return setLock(start, slots, t, null);
    }

    /**
     * Sets all the locks for the holder in a range to a specified value.
     * @param start First slot.
     * @param slots Number of slots.
     * @param holder
     * @param value
     * @return Number of values changed.
     * @throws InexcusableLockException
     */
    private int setLock(int start, int slots, T holder, T value) throws InexcusableLockException {
        prepLock(start + slots);
        int count = 0;
        for (int i = start; i < start + slots; i++) {
            if (this.slots.get(i) == holder) {
                count++;
                this.slots.put(i, value);
            }
            else {
                throw new InexcusableLockException();
            }
        }
        return count;
    }

    /**
     * Returns if the lock is free in a specified range.
     * @param start First slot.
     * @param slots Number of slots.
     * @return True if already lock, false if lock is free.
     */
    public boolean isLocked(int start, int slots) {
        prepLock(start + slots);
        for (int i = start; i < start + slots; i++) {
            if (this.slots.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the holder of the lock for a specified slot.
     * @param slot
     * @return Holder for a slot.
     */
    public T getHolder(int slot) {
        T retVal = null;
        if (slots.size() > slot) {
            retVal = slots.get(slot);
        }
        return retVal;
    }

    /**
     * Returns the next available unlocked range.
     * @param start Start of the lookup.
     * @param slots Number of slots.
     * @return Next available range.
     */
    public int nextOpen(int start, int slots) {
        int open = -1;
        for (int i = start; i < this.slots.size(); i++) {
            if (!isLocked(i, slots)) {
                open = i;
            }
        }
        return open;
    }

    /**
     * Prepares the lock for operations to a specified limit.
     * @param limit Slot limit.
     */
    private void prepLock(int limit) {
        while (slots.size() < limit) {
            slots.put(slots.size(), null);
        }
    }

    private class InexcusableLockException extends Exception {
    }
}
