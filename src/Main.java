import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

    // Start receiving messages - ready to receive messages!
    try (ServerSocket serverSocket = new ServerSocket(8080)) {
        System.out.println("Server started. In listening for messages.");

        while(true) {
            // Handle a new incoming message

            try (Socket client = serverSocket.accept()) {
                // client <- messages queued up in it!!
                System.out.println("Debug: got new message " + client.toString());

                // Read the request - listen to the message
                InputStreamReader isr = new InputStreamReader(client.getInputStream());

                BufferedReader br = new BufferedReader(isr);

                StringBuilder request = new StringBuilder();

                String line; // Temp variable called line that holds one line at a time of our message
                line = br.readLine();
                while (!line.isEmpty()) {
                    request.append(line + "\r\n");
                    line = br.readLine();
                }

                System.out.println("--REQUEST--");
                System.out.println(request);

                // Decide how we'd like to respond
                // Just send back a simple "Hello World"
                OutputStream clientOutput = client.getOutputStream();
                clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                clientOutput.write(("\r\n").getBytes());
                clientOutput.write(("Hello World").getBytes());
                clientOutput.flush();

                // Send a response - send our reply
                //Get ready for the next message
                client.close();

            } catch(Exception e){
                System.out.println("Exception occurred:"+e);
            }
        }
    } catch(Exception e){
        System.out.println("Exception occurred:"+e);
    }
}
}