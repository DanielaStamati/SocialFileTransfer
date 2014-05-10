package workers;

import models.CustomTableModel;
import models.FileModel;
import models.StringConstants;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client extends SwingWorker<Void, Void>{

    private static String IP;
    private static int PORT;
    private FileModel currentFile;
    private CustomTableModel model;
    static final Logger logger = Logger.getLogger(Client.class);


    public Client (String IP, int PORT, FileModel currentFile, CustomTableModel model) {

        this.IP = IP;
        this.PORT = PORT;
        this.currentFile = currentFile;
        this.model = model;
        
        addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // TODO Auto-generated method stub
                if (evt.getPropertyName().equals("progress")) {
                    Client.this.model.updateStatus(Client.this.currentFile, (Integer) evt.getNewValue());
                }
            }
        });


    }

    public static void connect(SelectionKey key) throws IOException {

        SocketChannel socketChannel = (SocketChannel)key.channel();
        socketChannel.configureBlocking(false);
        logger.info("Client: Trying to connect to server ...");
        
        System.out.println("muie");
        
        if (socketChannel.isConnectionPending()){

            socketChannel.finishConnect();
            logger.info("Clinet: Connection closed ...");
        }

        socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
        logger.info("Clinet: Connected to server");

    }

    public void read(SelectionKey key) throws IOException {

        System.out.println("READ CLIENT: ");
        //ByteBuffer buf = ByteBuffer.allocateDirect(StringConstants.BUF_SIZE);
        MappedByteBuffer buf = null;

        SocketChannel socketChannel	= (SocketChannel)key.channel();
        RandomAccessFile fromFile = new RandomAccessFile(currentFile.getName() + "XX", "rw");
        FileChannel fc = fromFile.getChannel();
        long bytesReaded;
        int progress = 0;
        int count = 0;
        long fileSize = 1;



//        Aici incerc sa primesc primul pachet care reprezinta lungimea fisierului da nu se poate ?? ->era
//          frumos pt progress bar da ....
//        buf.clear();
//        while (socketChannel.read(buf) < 0){
//        }
//
//        buf.flip();
//        fileSize = buf.getLong();

        logger.info("Client: Started getting data ...");

        buf = fc.map(FileChannel.MapMode.READ_WRITE, count, StringConstants.BUF_SIZE);
        while ((bytesReaded = socketChannel.read(buf)) > 0){

            count += bytesReaded;
            buf.flip();
            buf = fc.map(FileChannel.MapMode.READ_WRITE, count, bytesReaded);
            if (progress > 100)
                setProgress(100);
            else setProgress(progress);

            progress += Math.ceil((bytesReaded * 100)/fileSize) + 1;
            buf.clear();
        }

        setProgress(100);
        buf.clear();
        fc.close();
        fromFile.close();
        socketChannel.close();
        key.cancel();
        logger.info("Client: Finished getting data ...");
        System.out.println("done");


    }

    public void write(SelectionKey key) throws IOException {

        System.out.println("WRITE CLIENT: ");
        logger.info("Client: Sending request");

        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer buf = ByteBuffer.wrap(currentFile.getName().getBytes());

        while (socketChannel.write(buf) <= 0) {
        }
        socketChannel.register(key.selector(), SelectionKey.OP_READ);

    }

    public Void doInBackground() {

        Selector selector = null;
        SocketChannel socketChannel	= null;
   
        try {

            selector = Selector.open();

            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(IP, PORT));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);


            // main loop
            while (true) {
                // wait for something to happen

                selector.select();

                // iterate over the events
                for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                    // get current event and REMOVE it from the list!!!
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isConnectable())
                        connect(key);
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

            if (socketChannel != null)
                try {
                    socketChannel.close();
                } catch (IOException e) {}
        }
        return null;
    }

}
