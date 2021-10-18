import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        
		// Hardcode in IP and Port here if required
    	args = new String[] {"127.0.0.1", "30121"};
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inReader= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String userInput;
			String serverResponse;
            ArrayList<String> message = new ArrayList<>();

            while (true) {


				serverResponse = inReader.readLine();

                if (serverResponse.equals("DONE")) {
                    if (stringComplete(Integer.parseInt(serverResponse = inReader.readLine()), message.toArray().length)) {
                        System.out.println("I AM DONE");
                        outWriter.println("FINISHED");
                        System.out.println(message);
                        break;
                    }

                    outWriter.println("Need more packets");

                }
                else{
                    int index = Integer.parseInt(serverResponse);
                    String packetData = inReader.readLine();
                    if (index > message.size())
                        growList(message, index);
                    message.set(index, packetData);

                    System.out.println(message);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }

    private static boolean stringComplete(int finalPacketData, int actualSize){
        return finalPacketData == actualSize;
    }

    private static void growList(ArrayList<String> list, int index){
        int sizeToGrow = index - list.size();
        for (int i = list.size() - 1; i <= index + 1 ; i++) {
            list.add(" ");
        }

    }

}
