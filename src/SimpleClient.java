import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleClient {
    public static void main(String[] args) throws IOException {

    	args = new String[] {"127.0.0.1", "30121"};

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        Set<Integer> indexesReceived = new HashSet<>();

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inReader= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

			String serverResponse;
            ArrayList<String> message = new ArrayList<>();

            while (true) {

                //Gets the first response index from the server
				serverResponse = inReader.readLine();

                //Checks to see if the index is -1, which indicates the last packet
                if (Integer.parseInt(serverResponse) < 0) {
                    //Checks if the set of indexes is equal to the target size
                    if (indexesReceived.size() >= Integer.parseInt(inReader.readLine())) {
                        //If message is complete the client tells the server it is done and prints the message
                        System.out.println("All packets have been received. Here is your message:");
                        outWriter.println("FINISHED");
                        System.out.println(String.join(" ", message));
                        break;
                    }
                    else {
                        //If message is not complete the client requests more packets
                        outWriter.println("Need more packets");
                    }
                }
                else
                {
                    int index = Integer.parseInt(serverResponse);
                    String packetData = inReader.readLine();
                    //Checks to see if the index for the string exists yet. If not, the client calls the grow method
                    //which grows the list to fit the index
                    if (index >= message.size())
                        growList(message, index);
                    message.set(index, packetData);
                    //Adds the index received to a set to track all received packets
                    indexesReceived.add(index);
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

    //Method to grow the list to fit the index
    private static void growList(ArrayList<String> list, int index){
        int sizeToGrow = index - list.size();
        for (int i = 0; i <= sizeToGrow; i++) {
            list.add(" ");
        }
    }

}
