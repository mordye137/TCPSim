import java.net.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.io.*;

public class SimpleServer {

	static String data = "Look, I didn't want to be a half-blood.\n" +
			"If you're reading this because you think you might be one, my\n" +
			"advice is: close this book right now. Believe whatever lie your\n" +
			"mom or dad told you about your birth, and try to lead a normal\n" +
			"life.\n" +
			"Being a half-blood is dangerous. It's scary. Most of the time, it\n" +
			"gets you killed in painful, nasty ways.\n" +
			"If you're a normal kid, reading this because you think it's fiction,\n" +
			"great. Read on. I envy you for being able to believe that none of\n" +
			"this ever happened.\n" +
			"But if you recognize yourself in these pages-if you feel something\n" +
			"stirring inside-stop reading immediately. You might be one of us.\n" +
			"And once you know that, it's only a matter of time before they\n" +
			"sense it too, and they'll come for you.\n" +
			"Don't say I didn't warn you.\n" +
			"My name is Percy Jackson.";
	static PacketCreator packetCreator = new PacketCreator(data);
	static ArrayList<Packet> packetList;
	
	public static void main(String[] args) throws IOException {

		// Hard code in port number if necessary:
		args = new String[] { "30121" };
		
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
				Socket clientSocket1 = serverSocket.accept();
				PrintWriter outWriter = new PrintWriter(clientSocket1.getOutputStream(), true);
				BufferedReader inReader= new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
				) {

			String clientResponse;
			String finishedSending = "Packets sent.";
			String finishedReceiving = "All packets received.";
			packetList = packetCreator.createPackets();
			Packet lastPacket = new Packet(-1, "DONE", packetList.size());

			while (true) {

				System.out.println("Connected to client.");

				ArrayList<Packet> toSend = eightyPercentList(packetList);

				System.out.println("Sending packets");

				for (Packet p : toSend ) {
					outWriter.println(p.toStringIndex());
					outWriter.println(p.toStringData());
				}

				outWriter.println(lastPacket.toStringData());
				outWriter.println(lastPacket.toStringTotal());

				System.out.println("Packets Sent");

				if ((clientResponse = inReader.readLine()).equals("DONE")) {
					break;
				}

				System.out.println("Restarting");
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}

	}

	private static ArrayList<Packet> eightyPercentList(ArrayList<Packet> packets){
		Random rand = new Random();
		ArrayList<Packet> batch = new ArrayList<>();

 		for (Packet packet : packets) {
			if (rand.nextInt(10)<8)
				batch.add(packet);
		}

		return batch;
	}

}
