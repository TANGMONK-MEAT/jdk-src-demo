package test.socket.socketPackage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/10 20:44
 */
public class SocketServer{

    // 模拟TCP半包和粘包的问题

    public static void main(String[] args) {

        new SocketServer().start();

    }


    ServerSocket serverSocket;

    public SocketServer() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8089));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        int count;
        Socket socket;
        try {
            socket = serverSocket.accept();
            byte[] buf = new byte[20];
            InputStream in = socket.getInputStream();
            while ((count = in.read(buf,0,buf.length)) != -1) {
                System.out.println("+[" + count + "] byte: " + new String(buf, 0, count));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

