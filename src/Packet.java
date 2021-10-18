public class Packet {
    public int index;
    public String data;

    public Packet(int index, String data) {
        this.index = index;
        this.data = data;
    }

    public int toStringIndex() {
        return index;
    }

    public String toStringData() {
        return data;
    }

}
