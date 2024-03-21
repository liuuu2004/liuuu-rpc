package liuuu.extension;

public class Holder<T> {
    private volatile T value;
    public T get() {
        return value;
    }
    public void set(Object instance) {
        this.value = value;
    }
}
