package entity;

public class OrderedCoffee extends Entity {
    private Size size;
    private int count;
    private int coffeeId;
    private int orderId;

    public OrderedCoffee() {
        super();
    }
    public OrderedCoffee(int id, Size size, int count, int coffeeId, int orderId) {
        super(id);
        this.size = size;
        this.count = count;
        this.coffeeId = coffeeId;
        this.orderId = orderId;
    }

    public Size getSize() {
        return size;
    }
    public void setSize(Size size) {
        this.size = size;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCoffeeId() {
        return coffeeId;
    }
    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s@%h {super:%s, size:%s, count:%d, coffeeId:%d, orderId:%d}",
                getClass().getSimpleName(),
                this,
                super.toString(),
                size,
                count,
                coffeeId,
                orderId
        );
    }

    @Override
    public int hashCode() {
        final int PRIME_NUMBER = 31;
        final int SHIFT_BITS_COUNT = 16;
        int hashCode = super.hashCode();
        hashCode = PRIME_NUMBER * hashCode + size.hashCode();
        hashCode = PRIME_NUMBER * hashCode + (count ^ count >>> SHIFT_BITS_COUNT);
        hashCode = PRIME_NUMBER * hashCode + (coffeeId ^ coffeeId >>> SHIFT_BITS_COUNT);
        hashCode = PRIME_NUMBER * hashCode + (orderId ^ orderId >>> SHIFT_BITS_COUNT);
        return hashCode;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderedCoffee other = (OrderedCoffee) o;
        return size.compareTo(other.size) == 0
                && Integer.compare(count, other.count) == 0
                && Integer.compare(coffeeId, other.coffeeId) == 0
                && Integer.compare(orderId, other.orderId) == 0;
    }

    public int compareTo(OrderedCoffee o) {
        return Integer.compare(orderId, o.orderId);
    }
}
