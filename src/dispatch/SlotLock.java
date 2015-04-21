package dispatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aaron on 4/20/15.
 */

/**
 * A lock class that uses numbered slots to designate multiple lockable zones.
 * @param <T> Type of holder.
 */
public class SlotLock<T> {
    final Map<Integer, T> slots;

    public SlotLock() {
        slots = new HashMap<>();
    }

    /**
     * Acquires the lock for a range of time.
     * @param start First slot to lock.
     * @param slots Number of slots to lock.
     * @param t Lock holder.
     * @return Number of values changed.
     * @throws InaccessibleLockException
     */
    public int acquireLock(int start, int slots, T t) throws InaccessibleLockException {
        System.err.println(isLocked(start, slots));
        return setLock(start, slots, null, t);
    }

    /**
     * Releases the lock for a range of time.
     * @param start First slot to release.
     * @param slots Number of slots to release.
     * @param t Lock holder.
     * @return Number of values changed.
     * @throws InaccessibleLockException
     */
    public int releaseLock(int start, int slots, T t) throws InaccessibleLockException {
        return setLock(start, slots, t, null);
    }

    /**
     * Evicts all locks held by a specified holder.
     * @param holder
     * @return Number of slots evicted.
     */
    public int evictHolder(T holder) {
        int count = 0;
        try {
            for (int slot : slots.keySet()) {
                if (slots.get(slot) == holder) {
                    setLock(slot, 1, holder, null);
                    count++;
                }
            }
        }
        catch (InaccessibleLockException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Sets all the locks for the holder in a range to a specified value.
     * @param start First slot.
     * @param slots Number of slots.
     * @param holder
     * @param value
     * @return Number of values changed.
     * @throws InaccessibleLockException
     */
    private int setLock(int start, int slots, T holder, T value) throws InaccessibleLockException {
        int count = 0;
        for (int i = start; i < start + slots; i++) {
            if (this.slots.get(i) == holder) {
                count++;
                this.slots.put(i, value);
            }
            else { // Else if not already set.
                throw new InaccessibleLockException();
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
        for (int i = start; i <= start + slots; i++) {
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
     * Returns the next available unlocked range. Will always return a valid slot.
     * @param start Start of the lookup.
     * @param slots Number of slots.
     * @return Start of next available range.
     */
    public int nextOpen(int start, int slots) {
        int open = start;
        while (isLocked(open, slots)) {
            open++;
        }
        return open;
    }
}

class InaccessibleLockException extends Exception {
}
