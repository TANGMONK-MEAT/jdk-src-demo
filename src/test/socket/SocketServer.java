package test.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/8 17:44
 */
public class SocketServer {

    public static void main(String[] args) {
        SocketServer ss = new SocketServer();
        int port = 8888;
        try {
            ss.startServer(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket server;
            System.out.println("server socket is start……");
            while (true) {
                server = serverSocket.accept();
                try {
                    InputStream in = server.getInputStream();
                    // BufferedOutputStream out = new BufferedOutputStream(server.getOutputStream(),9);
                    OutputStream out = server.getOutputStream();

                    byte[] buf = new byte[1];
                    int size;
                    while ((size = in.read(buf,0,buf.length)) != -1) {
                        System.out.println("[" + size + "] byte: " + new String(buf));


                        // out.write("11111".getBytes());

                        // out.write("111".getBytes());
                        // out.flush();

                        // 每 1 秒，发送 一个 1
                        for (int i = 0;i < 5;i++){
                            out.write("111".getBytes());
                            // 这里不刷新 缓存，那么客户端 的read 会一直阻塞
                            // 直到我这里 flush
                            // out.flush();

                            // 休眠 1 秒
                            TimeUnit.SECONDS.sleep(1);
                        }
                        //
                        // out.flush();
                        // 关闭输出流，告诉 客户端的 read 方法，end of tream
                        // 因此，客户端的 read 方法 会 返回 -1
                        // server.shutdownOutput();
                    }
                    System.out.println(size);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                } finally {
                    System.out.println("close client socket");
                    server.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
