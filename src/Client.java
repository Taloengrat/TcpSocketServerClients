import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

public class Client {
    public static TextField txfIp, txfPort, txfName, txfMes; // Textfield
    public JFrame f;
    public JPanel panelPageStart;
    public JPanel panelCenter;
    public JPanel panelPageEnd;
    public TextArea textArea;
    public Label labelIp, labelPort, labelName, labelMes;
    public Button btConnect;
    public Button btSend;
    public Socket socket;
    public String msg;


    public static DataOutputStream dOut ;
    public static DataInputStream dIn;

    public Client () {
        f = new JFrame("Client");
        panelPageStart = new JPanel(); // Panel
        panelCenter = new JPanel();
        panelPageEnd = new JPanel();
        textArea = new TextArea(10, 60);
        btConnect = new Button("Connect");
        btSend = new Button("Send");
        msg = "";
        init();

    }

    class RunConect extends Thread {

        public  RunConect() {

        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(!msg.equals("exit")) {

                try {
                    msg = dIn.readUTF();
                    textArea.setText(textArea.getText() +"\n" +msg);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }

    }

    private void ConnectServer(String ip, String port) {
//      System.out.println("IP : "+ ip + "\nPort : " + port);
        //////////////////////////////////////////////////////////

        try {


            socket = new Socket(ip,Integer.parseInt(port));

            dIn = new DataInputStream(socket.getInputStream());
            dOut = new DataOutputStream(socket.getOutputStream());

            RunConect con = new RunConect();
            con.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SendData(String name, String data)  {
//      System.out.println("Name : " + name + "\nMessage : "+ data);
        //////////////////////////////////////////////////////////

        try {
            dOut.writeUTF(name + " : "+data);
            textArea.setText(textArea.getText() +"\n" +name + " : " + data);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private AbstractAction SendDataAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(txfName.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Please put Name!!!");
            }else if (txfMes.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please put Message!!!");
            }else {
                SendData(txfName.getText(), txfMes.getText());
            }

        }
    };

    private AbstractAction ConnectServerAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub

            if(txfIp.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Please put IP!!!");
            }else if (txfPort.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please put PORT!!!");
            }else { ///// IP and Port not empty

                ConnectServer(txfIp.getText(), txfPort.getText());

            }
        }
    };

    public void init() {

        textArea.setEditable(false);

        btSend.setBackground(Color.blue);
        btSend.setForeground(Color.white);

        f.setSize(397, 364);
        f.getContentPane().setLayout(new BorderLayout(10,10));


        txfIp = new TextField(10);
        txfPort = new TextField(10);

        txfName = new TextField(5);
        txfMes = new TextField(20);


//      txfPort.addActionListener(ConnectServerAction);
//      txfMes.addActionListener(SendDataAction);

        labelIp = new Label("IP");
        labelIp.setSize(18, 18);
        labelPort = new Label("PORT");
        labelPort.setSize(18, 18);
        labelName = new Label("Name");
        labelName.setSize(18, 18);
        labelMes = new Label("Message");
        labelMes.setSize(18, 18);


        panelPageStart.setLayout(new FlowLayout()); //Set panel start
        panelPageStart.setSize(1000, 500);

        panelCenter.setSize(1000, 500); // Set panel center
        panelCenter.setBackground(Color.blue);

        panelPageEnd.setSize(1000, 500);
        panelPageEnd.setBackground(Color.white);




        btConnect.setBackground(Color.GREEN);






        txfIp.setBounds(130, 100, 100, 20);

        txfPort.setBounds(130, 100, 100, 20);




        // Panel Start add componant
        panelPageStart.add(labelIp);
        panelPageStart.add(txfIp);
        panelPageStart.add(labelPort);
        panelPageStart.add(txfPort);
        panelPageStart.add(btConnect);

        // Panel Center add componant

        panelCenter.add(textArea);

        // Panel PageEnd add componant
        panelPageEnd.add(labelName);
        panelPageEnd.add(txfName);
        panelPageEnd.add(labelMes);
        panelPageEnd.add(txfMes);
        panelPageEnd.add(btSend);

        f.getContentPane().add(panelPageStart, BorderLayout.PAGE_START);
        f.getContentPane().add(panelCenter, BorderLayout.CENTER);
        f.getContentPane().add(panelPageEnd, BorderLayout.PAGE_END);

        f.setLocation(500, 300);
        f.pack();

        f.setVisible(true);

        /////////////////////////////////////////////////////////////////
        //////////////////// Event anoter in app ////////////////////////
////////////////////////////////////////////////////////////////////////////////////

        txfPort.addActionListener(ConnectServerAction);


        txfMes.addActionListener(SendDataAction);
        btSend.addActionListener(SendDataAction);
        btConnect.addActionListener(ConnectServerAction);

    }

}
