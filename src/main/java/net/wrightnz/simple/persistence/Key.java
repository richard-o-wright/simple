package net.wrightnz.simple.persistence;

/**
 *
 * @author Richard Wright
 * @param <T>
 */
public class Key<T> {

    private final T value;

    public Key(T value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }
}
