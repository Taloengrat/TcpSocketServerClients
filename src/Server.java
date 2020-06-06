import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Server {

    public ServerSocket serverSocket;
    public Socket socket;
    public DataInputStream din;
    public DataOutputStream dout;

    public TextField txfIp, txfPort, txfMes; // Textfield
    public JFrame f = new JFrame("Server"); // Frame

    public JPanel panelstart = new JPanel();
    public JPanel panelCenter = new JPanel();
    public JPanel panelPageEnd = new JPanel();
    public TextArea textArea = new TextArea(10, 60);
    public Label labelIp, labelPort, labelMes; // Label
    public Button btConnect = new Button("Connect");
    public Button btSend = new Button("Send");
    public TextField txCountClient = new TextField();
    public String msgIn = "";
    public static int Countclient = 0;

    ArrayList<Socket> socketArrayList = new ArrayList<>();



    public Server() throws IOException {

        System.out.println("Server running");



        try {
            System.out.println("Waiting for client...");
            serverSocket = new ServerSocket(2712);
            socket = serverSocket.accept();

            System.out.println("Connected");

            init(); /// Initial

            textArea.setText("\t...... Client Connected......");



            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            dout.writeUTF("\t......Server Connected......");

            while (!msgIn.endsWith("exit")){



                UpCountClient();

                msgIn = din.readUTF();
                System.out.println(msgIn);
                textArea.setText(textArea.getText().trim() + "\n" + msgIn );
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        dout.writeUTF("exit");
        f.setVisible(false);
        JOptionPane.showMessageDialog(panelCenter, "Client disconnect");
        socket.close();


    }

    public static class ServerThreads implements Runnable{
        Server server=null;
        Socket client=null;
        BufferedReader cin;
        PrintStream cout;
        TextArea textArea;
        int id;
        String s;

        public ServerThreads(){

        }
        @Override
        public void run() {

        }
    }

    private void init() {
        textArea.setEditable(false);

        btSend.setBackground(Color.blue);
        btSend.setForeground(Color.white);

        f.setSize(397, 364);
        f.getContentPane().setLayout(new BorderLayout(10,10));


        txfIp = new TextField(10);
        txfPort = new TextField(10);
        txfMes = new TextField(20);



        txfMes.addActionListener(SendDataAction);

        labelIp = new Label("IP");
        labelIp.setSize(18, 18);
        labelPort = new Label("PORT");
        labelPort.setSize(18, 18);
        labelMes = new Label("Message");
        labelMes.setSize(18, 18);
        labelMes.setForeground(Color.white);


        panelPageEnd.setBackground(Color.GREEN);

        panelCenter.setSize(1000, 500); // Set panel center
        panelCenter.setBackground(Color.black);

        panelPageEnd.setSize(1000, 500);
        panelPageEnd.setBackground(Color.BLACK);




        btConnect.setBackground(Color.GREEN);






        txfIp.setBounds(130, 100, 100, 20);

        txfPort.setBounds(130, 100, 100, 20);


        // Panel Start add componant
        panelstart.add(new Label("Client"));

        txCountClient.setText(String.valueOf(Countclient));
        txCountClient.setEditable(false);
        txCountClient.setFocusable(false);
        panelstart.add(txCountClient);

        // Panel Center add componant

        panelCenter.add(textArea);


        // Panel PageEnd add componant

        panelPageEnd.add(labelMes);
        panelPageEnd.add(txfMes);
        panelPageEnd.add(btSend);


        f.getContentPane().add(panelstart, BorderLayout.PAGE_START);
        f.getContentPane().add(panelCenter, BorderLayout.CENTER);
        f.getContentPane().add(panelPageEnd, BorderLayout.PAGE_END);



        f.setLocation(300, 300);
        f.pack();


        f.setVisible(true);

        /////////////////////////////////////////////////////////////////
        //////////////////// Event anoter in app ////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////

        btSend.addActionListener(SendDataAction);


    }

    private void SendData(String data) throws IOException {
//	        System.out.println("Name : " + name + "\nMessa ge : "+ data);
        //////////////////////////////////////////////////////////



        dout.writeUTF("Server : " + data);
        textArea.setText(textArea.getText().trim() + "\n" +"Server : " + data);

        txfMes.setText(null);


    }
    AbstractAction SendDataAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (txfMes.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please put Message!!!");
            }else {
                try {
                    SendData(txfMes.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        }
    };

    public void UpCountClient(){
        Countclient++;
        txCountClient.setText(String.valueOf(Countclient));
        panelstart .add(new Button("Client" + Countclient +":"));

    }

    public void DownCountClient(){
        Countclient--;
        txCountClient.setText(String.valueOf(Countclient));
    }
}
