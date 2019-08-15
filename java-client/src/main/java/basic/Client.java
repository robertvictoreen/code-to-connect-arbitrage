package basic;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

public class Client {

    public static void main(String args[]) throws URISyntaxException {

        Arbitrage client = new Arbitrage();
        JSONObject[] data = new JSONObject[3];

        final Socket socket = IO.socket("http://localhost:3000");
        final Socket socketEbs = IO.socket("http://localhost:4000");
        final Socket socketReu = IO.socket("http://localhost:5000");

        socket.on(Socket.EVENT_CONNECT, args1 -> System.out.println("Socket BBG connected!"))
        .on("bbg", bbgObj -> {
            JSONObject bbg = (JSONObject) bbgObj[0];
            System.out.println(bbg);
            client.handleBbg(bbg);


        })
        .on(Socket.EVENT_DISCONNECT, args13 -> {});
        socket.connect();


        socketEbs.on(Socket.EVENT_CONNECT, args1 -> System.out.println("Socket EBS connected!"))
        .on("ebs", ebsObj -> {
            JSONObject ebs = (JSONObject) ebsObj[0];
            System.out.println(ebs);
            client.handleEbs(ebs);

        });
        socketEbs.connect();

        socketReu.on(Socket.EVENT_CONNECT, args1 -> System.out.println("Socket REUTERS connected!"))
        .on("reu", reuObj -> {
            JSONObject reu = (JSONObject) reuObj[0];
            System.out.println(reu);


            client.handleReu(reu);
        });
        socketReu.connect();


    }

}