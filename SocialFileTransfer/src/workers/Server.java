package workers;

import models.StringConstants;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import org.apache.log4j.Logger;



public class Server extends SwingWorker<Object, Object >{
	

	public static String IP	;	// server IP
	public static int PORT;		// server port
    static final Logger logger = Logger.getLogger(Server.class);


    public Server(String IP, int PORT) {

        this.IP = IP;
        this.PORT = PORT;
        logger.info("Server started: IP: " + IP + " PORT: " + PORT);
    }
	
	public static void accept(SelectionKey key) throws IOException {
		
		System.out.println("ACCEPT: ");

        ByteBuffer buf = ByteBuffer.allocateDirect(StringConstants.BUF_SIZE);
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();   // initialize from key

        SocketChannel socketChannel = serverSocketChannel.accept();				        // initialize from accept

        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);


        // display remote client address
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
        logger.info("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
	}
	
	public static void read(SelectionKey key) throws IOException {

        System.out.println("READ SERVER: ");

        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer buff = ByteBuffer.allocateDirect(StringConstants.BUF_SIZE);

        while(socketChannel.read(buff) <= 0){

        }
        logger.info("Server getting request ... ");
        socketChannel.register(key.selector(), SelectionKey.OP_WRITE, buff);


    }
	
	public static void write(SelectionKey key) throws IOException {


        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer)key.attachment();
        Writer wr = new Writer(socketChannel, buf);
        wr.run();

	}
	
	public Object doInBackground() {
		
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
					else if (key.isWritable())
						write(key);
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
        return null;
	}

}
