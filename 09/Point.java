public record Point(int x, int y) {

    @Override
    public String toString() {
        return x + ":" + y;
    }

    @Override
    public int hashCode() { return toString().hashCode(); }
}
