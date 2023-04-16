package org.academiadecodigo.powrangers;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static final int SERVER_PORT = 7777;
    public static int gameIsStarted = 0;

    public int s;

    ExecutorService fixedPool = Executors.newFixedThreadPool(15);


    LinkedList<Socket> socketsPlayers = new LinkedList<>();
    TreeSet<Logo> logoForGame;

    int a;
    int e;
    int b;
    int c;
    int d;


    public static void main(String[] args) {

        Server server = new Server();
        try {
            server.connectServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void connectServer() throws IOException {

        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Waiting for a connection....");

        logoForGame = new TreeSet<>();

        while (logoForGame.size() != 10) {
            logoForGame.add(Logo.logoRandom());
            System.out.println(logoForGame.toString());
        }
        //Logo[] logos = logoForGame.toArray();

        while (true) {

            try {

                Socket clientConnection = serverSocket.accept();
                System.out.println("Connection accepted");

                socketsPlayers.add(clientConnection);

                BufferedWriter writeWelcome = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream()));
                BufferedReader readWelcome = new BufferedReader(new FileReader("resources/welcome.txt"));

                String reading = readWelcome.readLine();

                while (reading != null) {
                    writeWelcome.write(reading + "\n");
                    writeWelcome.flush();
                    reading = readWelcome.readLine();
                }

                fixedPool.submit(new MultipleServers(clientConnection, logoForGame));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public class MultipleServers implements Runnable {

        Socket request;

        public MultipleServers(Socket request, Set logo) {
            this.request = request;
        }

        @Override
        public void run() {
            playerInteraction(request);
        }

        public void playerInteraction(Socket clientConnection) {

            try {
                gameIsStarted++;

                PrintStream printStream = new PrintStream(clientConnection.getOutputStream(), true);

                Prompt prompt = new Prompt(clientConnection.getInputStream(), printStream);
                StringInputScanner askName = new StringInputScanner();
                askName.setMessage("Give us your username" + "\n");
                String name = prompt.getUserInput(askName);

                System.out.println("new player: " + " " + name);

                if (socketsPlayers.size() == 5) {
                    startingGame();
                    gameIsStarted = 0;
                }


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public synchronized void startingGame() throws InterruptedException {


            while (!logoForGame.isEmpty()) {
                try {
                    s = 0;
                    BufferedWriter writeLogo = new BufferedWriter(new OutputStreamWriter(socketsPlayers.get(s).getOutputStream()));
                    BufferedWriter writeLogo1 = new BufferedWriter(new OutputStreamWriter(socketsPlayers.get(s + 1).getOutputStream()));
                    BufferedWriter writeLogo2 = new BufferedWriter(new OutputStreamWriter(socketsPlayers.get(s + 2).getOutputStream()));
                    BufferedWriter writeLogo3 = new BufferedWriter(new OutputStreamWriter(socketsPlayers.get(s + 3).getOutputStream()));
                    BufferedWriter writeLogo4 = new BufferedWriter(new OutputStreamWriter(socketsPlayers.get(s + 4).getOutputStream()));


                    BufferedReader readLogo = new BufferedReader(new FileReader("resources/" + logoForGame.first() + ".txt"));

                    String readingLogo = readLogo.readLine();


                    while (readingLogo != null) {

                        try {
                            Thread.sleep(800);
                            writeLogo.write(readingLogo + "\n");
                            writeLogo.flush();
                            writeLogo1.write(readingLogo + "\n");
                            writeLogo1.flush();
                            writeLogo2.write(readingLogo + "\n");
                            writeLogo2.flush();
                            writeLogo3.write(readingLogo + "\n");
                            writeLogo3.flush();
                            writeLogo4.write(readingLogo + "\n");
                            writeLogo4.flush();
                            readingLogo = readLogo.readLine();


                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Prompt prompt = new Prompt(socketsPlayers.get(s).getInputStream(), System.out);
                    Prompt prompt1 = new Prompt(socketsPlayers.get(s + 1).getInputStream(), System.out);
                    Prompt prompt2 = new Prompt(socketsPlayers.get(s + 2).getInputStream(), System.out);
                    Prompt prompt3 = new Prompt(socketsPlayers.get(s + 3).getInputStream(), System.out);
                    Prompt prompt4 = new Prompt(socketsPlayers.get(s + 4).getInputStream(), System.out);


                    StringInputScanner question1p1 = new StringInputScanner();
                    question1p1.setMessage("Player 1");


                    StringInputScanner question1p2 = new StringInputScanner();
                    question1p2.setMessage("Player 2");


                    StringInputScanner question1p3 = new StringInputScanner();
                    question1p3.setMessage("Player 3");


                    StringInputScanner question1p4 = new StringInputScanner();
                    question1p4.setMessage("Player 4");


                    StringInputScanner question1p5 = new StringInputScanner();
                    question1p5.setMessage("Player 5" + "\n");

                    String answer1p1 = prompt.getUserInput(question1p1);
                    String answer1p2 = prompt1.getUserInput(question1p2);
                    String answer1p3 = prompt2.getUserInput(question1p3);
                    String answer1p4 = prompt3.getUserInput(question1p4);
                    String answer1p5 = prompt4.getUserInput(question1p5);


                    if (answer1p1.equals(logoForGame.first().name())) {
                        a++;

                    }
                    if (answer1p2.equals(logoForGame.first().name())) {
                        b++;

                    }
                    if (answer1p3.equals(logoForGame.first().name())) {
                        c++;
                    }
                    if (answer1p4.equals(logoForGame.first().name())) {
                        d++;
                    }
                    if (answer1p5.equals(logoForGame.first().name())) {
                        e++;
                    }
                    System.out.println(a);
                    System.out.println(b);
                    System.out.println(c);
                    System.out.println(d);
                    System.out.println(e);


                    Thread.sleep(
                            500);

                    logoForGame.pollFirst();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
            try {
                PrintStream printStream1 = new PrintStream(socketsPlayers.get(s).getOutputStream());
                PrintStream printStream2 = new PrintStream(socketsPlayers.get(s + 1).getOutputStream());
                PrintStream printStream3 = new PrintStream(socketsPlayers.get(s + 2).getOutputStream());
                PrintStream printStream4 = new PrintStream(socketsPlayers.get(s + 3).getOutputStream());
                PrintStream printStream5 = new PrintStream(socketsPlayers.get(s + 4).getOutputStream());

                printStream1.println("You got " + a + " points!");
                printStream2.println("You got " + b + " points!");
                printStream3.println("You got " + c + " points!");
                printStream4.println("You got " + d + " points!");
                printStream5.println("You got " + e + " points!");

                if(a > b && a > c && a > d && a > e) {
                    printStream1.println("You WON!!!!");
                }
                if(b > a && b > c && b > d && b > e) {
                    printStream2.println("You WON!!!!");
                }
                if(c > a && c > b && c > d && d > e) {
                    printStream3.println("You WON!!!!");
                }
                if(d > a && d > b && d > c && d > e) {
                    printStream4.println("You WON!!!!");
                }
                if(e > a && e > b && e > c && e > d) {
                    printStream5.println("You WON!!!!");
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}






