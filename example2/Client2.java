import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {

    public static void main(String[] args) {

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {

            socket = new Socket("localhost",1234);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            //here is where we need to customize to use GUI. maybe JTextField submit text.
            Scanner scanner = new Scanner(System.in);


            while (true) {

                //add GUI here
                //this sends a message to the server
                String msgToSend = scanner.nextLine(); //does not include newline '/n'
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine(); //adds a newline character. This is necessary for the readLine() function to know when the sentence ends.
                bufferedWriter.flush();

                //this reads message from server
                System.out.println("Server: " + bufferedReader.readLine());


                if (msgToSend.equalsIgnoreCase("BYE")){
                    break;
                }



            }




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if (socket != null){
                    socket.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if (bufferedReader != null){
                    bufferedReader.close();
                }
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }


    }

}















