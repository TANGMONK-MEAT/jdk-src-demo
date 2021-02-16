package test.io.buffer;

import java.io.*;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/10 22:14
 */
public class BufferedInputStreamTest {

    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        BufferedInputStream BuffIn = null;
        try {
            File file = new File("E:\\github\\jdk-src-demo\\README.md");

            in = new FileInputStream(file);

            BuffIn = new BufferedInputStream(in);
        } finally {
            assert in != null;
            BuffIn.close();

        }
    }
}
