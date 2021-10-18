public class Packet {
    public int index, total;
    public String data;

    public Packet(int index, String data) {
        this.index = index;
        this.data = data;
    }

    public Packet(int index, String data, int total) {
        this.index = index;
        this.data = data;
        this.total = total;
    }

    public int toStringIndex() {
        return index;
    }

    public String toStringData() {
        return data;
    }

    public int toStringTotal(){return total;}


}
