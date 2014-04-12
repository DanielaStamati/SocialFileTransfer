package models;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{


    public static  String IP;	// server IP
    public static  int PORT;		// server port
    public final static int BUF_SIZE = 4;


    public Server (String IP, int PORT) {

        this.IP = IP;
        this.PORT = PORT;
    }

    public static void accept(SelectionKey key) throws IOException {

        System.out.print("ACCEPT: ");
        ByteBuffer buf = ByteBuffer.allocateDirect(BUF_SIZE);
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();   // initialize from key

        SocketChannel socketChannel = serverSocketChannel.accept();				        // initialize from accept

        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);

        System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());

    }

    public static void read(SelectionKey key) throws IOException {
        System.out.print("READ: ");

        int bytes;
        ByteBuffer buf				= (ByteBuffer)key.attachment();
        SocketChannel socketChannel	= (SocketChannel) key.channel();


        buf.clear();
        while (socketChannel.read(buf) != -1){

            if (!buf.hasRemaining())
                break;
        }

        buf.flip();
        Channels.newChannel(System.out).write(buf);
        write(buf);
    }

    public static void write(ByteBuffer buf) throws IOException {
        System.out.println("WRITE: ");

        RandomAccessFile raf = new RandomAccessFile("out.txt", "rw");

        buf.flip();
        byte[] data = new byte[buf.limit()];
        buf.get(data);
        raf.write(data);
        raf.close();
        System.exit(1);
    }

    public void run() {

        Selector selector						= null;
        ServerSocketChannel serverSocketChannel	= null;

        try {

            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(IP, PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            // main loop
            while (true) {
                // wait for something to happen

                selector.select();

                // iterate over the events
                for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                    // get current event and REMOVE it from the list!!!
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isAcceptable())
                        accept(key);
                    else if (key.isReadable())
                        read(key);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // cleanup

            if (selector != null)
                try {
                    selector.close();
                } catch (IOException e) {}

            if (serverSocketChannel != null)
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {}
        }

    }

}
