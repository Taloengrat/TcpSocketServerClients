import java.io.IOException;

public class MainRunner {
    public static void main(String[] args) throws IOException {
        // write your code here
        new Client();

//		      Thread thread = new Thread(new Server());
//		      thread.start();
//		      System.out.println("Client started ");


        new Server();



    }

    public static class ThreadServerPage implements Runnable{

        @Override
        public void run() {
            System.out.println("Start ThreadsServerPage");
        }
    }
}
