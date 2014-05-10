package workers;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by MAX on 4/13/2014.
 */
public class Writer implements Runnable{

    SocketChannel socketChannel;
    ByteBuffer buf;
    static final Logger logger = Logger.getLogger(Server.class);

    public Writer(SocketChannel socketChannel, ByteBuffer buf) {
        this.socketChannel = socketChannel;
        this.buf = buf;
    }

    @Override
    public void run() {


        System.out.println("WRITE SERVER-WRITER: ");
        String filename = "";
        int bytesWritten = 0;

        buf.flip();
        String encoding = System.getProperty("file.encoding");
        filename = Charset.forName(encoding).decode(buf).toString();

        logger.info("Server: sending file " + filename);
        logger.info("Server started sending data ...");


        RandomAccessFile fromFile = null;
        int count = 0;

        try {
            fromFile = new RandomAccessFile(filename, "rw");
            FileChannel fc = fromFile.getChannel();

//              Daca se trimite primul pachet cu lungimea fisierului din nush ce motive mistice se pierde
//           primul pachet din fisier...

//            buf.clear();
//            buf.putLong(fromFile.length());
//            buf.flip();
//            while (socketChannel.write(buf) < 0){
//
//            }

            buf.clear();
            while (fc.read(buf) >= 0 || buf.position() > 0) {

                buf.flip();

                bytesWritten = socketChannel.write(buf);
                count += bytesWritten;
                buf.clear();

            }

            logger.info("Server done sending data");

            fc.close();
            fromFile.close();
            socketChannel.close();
            buf.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
