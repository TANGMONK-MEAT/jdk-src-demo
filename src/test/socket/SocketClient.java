package test.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/8 17:43
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        Socket client;
        OutputStream out = null;
        InputStream in = null;

        try {
            client = new Socket("127.0.0.1",8888);
            out = client.getOutputStream();
            in = client.getInputStream();

            // client.shutdownInput();
            // 发送
            out.write("0".getBytes());
            out.flush();

            client.close();

            // 缓冲区
            // 缓冲区满，不是end of stream
            byte[] buf = new byte[5];
            int size;
            // 读取
            while ((size = in.read(buf,0,buf.length)) != -1) {
                System.out.println("[" + size + "] byte: " + new String(buf));
                // 相当于 自己强制 end if stream，自己的 read 方法会返回 -1
                // 相当于，我不想在读了，返回 -1 给我结束吧
                // 对 服务端 没有任何影响
                client.shutdownInput();
            }
            System.out.println(size);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert out != null;
            out.close();
            assert in != null;
            in.close();
        }
    }
}
