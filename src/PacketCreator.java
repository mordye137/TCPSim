import java.util.ArrayList;
import java.util.Collections;

public class PacketCreator {

    private final String data;


    public PacketCreator(String data) {
        this.data = data;
    }

    public ArrayList<Packet> createPackets() {
        ArrayList<Packet> packetList = new ArrayList<>();

        String[] dataList = data.split("\\s+");
        int index = 0;

        for (String data : dataList) {
            packetList.add(new Packet(index, data));
            index++;
        }

        Collections.shuffle(packetList);
        return packetList;
    }
}
