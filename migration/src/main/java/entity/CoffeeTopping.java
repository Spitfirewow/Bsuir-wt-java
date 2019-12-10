package entity;

public class CoffeeTopping extends Entity {
    private int toppingId;
    private int coffeeId;

    public CoffeeTopping() {
        super();
    }
    public CoffeeTopping(int id, int toppingId, int coffeeId) {
        super(id);
        this.toppingId = toppingId;
        this.coffeeId = coffeeId;
    }

    public int getToppingId() {
        return toppingId;
    }
    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }
    public int getCoffeeId() {
        return coffeeId;
    }
    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s@%h {super:%s, toppingId:%d, coffeeId:%d}",
                getClass().getSimpleName(),
                this,
                super.toString(),
                toppingId,
                coffeeId
        );
    }

    @Override
    public int hashCode() {
        final int PRIME_NUMBER = 31;
        final int SHIFT_BITS_COUNT = 16;
        int hashCode = super.hashCode();
        hashCode = PRIME_NUMBER * hashCode + (toppingId ^ toppingId >>> SHIFT_BITS_COUNT);
        hashCode = PRIME_NUMBER * hashCode + (coffeeId ^ coffeeId >>> SHIFT_BITS_COUNT);
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CoffeeTopping other = (CoffeeTopping) o;
        return Integer.compare(toppingId, other.toppingId) == 0
                && Integer.compare(coffeeId, other.coffeeId) == 0;
    }

    public int compareTo(CoffeeTopping o) {
        return Integer.compare(coffeeId, o.coffeeId);
    }
}
