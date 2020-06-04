public class MainRunner {
    public static void main(String[] args) {
        // write your code here
        new Client();

//		      Thread thread = new Thread(new Server());
//		      thread.start();
//		      System.out.println("Client started ");

        new Server();

    }
}
