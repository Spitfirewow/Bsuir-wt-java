package entity;

public class OrderedExtra extends Entity {
    private int count;
    private int extraId;
    private int orderId;

    public OrderedExtra() {
        super();
    }
    public OrderedExtra(int id, int count, int extraId, int orderId) {
        super(id);
        this.count = count;
        this.extraId = extraId;
        this.orderId = orderId;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getExtraId() {
        return extraId;
    }
    public void setExtraId(int extraId) {
        this.extraId = extraId;
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
                "%s@%h {super:%s, count:%d, extraId:%d, orderId:%d}",
                getClass().getSimpleName(),
                this,
                super.toString(),
                count,
                extraId,
                orderId
        );
    }

    @Override
    public int hashCode() {
        final int PRIME_NUMBER = 31;
        final int SHIFT_BITS_COUNT = 16;
        int hashCode = super.hashCode();
        hashCode = PRIME_NUMBER * hashCode + (count ^ count >>> SHIFT_BITS_COUNT);
        hashCode = PRIME_NUMBER * hashCode + (extraId ^ extraId >>> SHIFT_BITS_COUNT);
        hashCode = PRIME_NUMBER * hashCode + (orderId ^ orderId >>> SHIFT_BITS_COUNT);
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderedExtra other = (OrderedExtra) o;
        return Integer.compare(count, other.count) == 0
                && Integer.compare(extraId, other.extraId) == 0
                && Integer.compare(orderId, other.orderId) == 0;
    }

    public int compareTo(OrderedExtra o) {
        return Integer.compare(orderId, o.orderId);
    }
}
