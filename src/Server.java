import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Server {

    public static ServerSocket serverSocket;
    public static Socket socket;
    public static DataInputStream din;
    public static DataOutputStream dout;

    public static TextField txfIp, txfPort, txfMes; // Textfield
    public static JFrame f = new JFrame("Server"); // Frame

    public static JPanel panelCenter = new JPanel();
    public static JPanel panelPageEnd = new JPanel();
    public static TextArea textArea = new TextArea(10, 60);
    public static Label labelIp, labelPort, labelMes; // Label
    public static Button btConnect = new Button("Connect");
    public static Button btSend = new Button("Send");
    public static String msgIn = "";

    public Server(){

        System.out.println("Server running");

        init();

        try {
            System.out.println("Waiting for client...");
            serverSocket = new ServerSocket(2712);
            socket = serverSocket.accept();

            System.out.println("Connected");

            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            dout.writeUTF("\tServer Connected . . .");

            while (!msgIn.equals("exit")){

                msgIn = din.readUTF();
                textArea.setText(textArea.getText().trim() + "\n" + msgIn );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void init() {
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




        panelCenter.setSize(1000, 500); // Set panel center
        panelCenter.setBackground(Color.black);

        panelPageEnd.setSize(1000, 500);
        panelPageEnd.setBackground(Color.BLACK);




        btConnect.setBackground(Color.GREEN);






        txfIp.setBounds(130, 100, 100, 20);

        txfPort.setBounds(130, 100, 100, 20);



        // Panel Center add componant

        panelCenter.add(textArea);


        // Panel PageEnd add componant

        panelPageEnd.add(labelMes);
        panelPageEnd.add(txfMes);
        panelPageEnd.add(btSend);


        f.getContentPane().add(panelCenter, BorderLayout.CENTER);
        f.getContentPane().add(panelPageEnd, BorderLayout.PAGE_END);



        f.setLocation(500, 300);
        f.pack();


        f.setVisible(true);

        /////////////////////////////////////////////////////////////////
        //////////////////// Event anoter in app ////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////

        btSend.addActionListener(SendDataAction);


    }

    private static void SendData(String data) throws IOException {
//	        System.out.println("Name : " + name + "\nMessa ge : "+ data);
        //////////////////////////////////////////////////////////



        dout.writeUTF("Server : " + data);
        textArea.setText(textArea.getText().trim() + "\n" +"Server : " + data);


    }
    static AbstractAction SendDataAction = new AbstractAction() {

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

}
