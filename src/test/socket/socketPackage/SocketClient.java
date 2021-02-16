package test.socket.socketPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/10 20:43
 */
public class SocketClient{

    public static void main(String[] args) {
        new SocketClient().start();
    }

    Socket clientSocket = null;

    public SocketClient(){
        try {
            clientSocket = new Socket("127.0.0.1",8089);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // 8 个字节
        byte[] msg = "Hi,Java.".getBytes();
        try {
            OutputStream os = clientSocket.getOutputStream();

            // 这里假设有10个请求，公用一个连接
            // 每个线程向服务端都发送 msg
            for (int i = 0; i < 10; i++) {
                os.write(msg,0,msg.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert clientSocket != null;
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
