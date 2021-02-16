package test.io.buffer;

import java.io.*;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/10 21:57
 */
public class BufferedOutputStreamTest {

    public static void main(String[] args) throws IOException {

        BufferedOutputStream buffOut = null;
        try {
            File file = new File("E:\\github\\jdk-src-demo\\README.md");
            OutputStream out = new FileOutputStream(file);

            buffOut = new BufferedOutputStream(out);
        } finally {
            assert buffOut != null;
            buffOut.close();
        }



    }
}
