package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db.DBHelper;

public class ThreadPoolServer implements Runnable {

	protected static final int PORT = 2194;
	protected static final int ATTEMPT_COUNT = 5;
	// protected static final int TIMEOUT = 5*1000;
	protected static final int CLIENTS_COUNT = 50;
	protected static final int GAMES_COUNT = 20;

	/* statistics */
	public int stats_currentClientThreads = 0;
	public int stats_currentGameThreads = 0;
	public int stats_overAllConnections = 0;
	public int stats_overAllLogins = 0;
	public int stats_overAllGames = 0;
	public int stats_overAllRegisters= 0;
	
	protected ExecutorService clientPool;
	protected ExecutorService gamePool;
	protected BlockingQueue<Player> gameQueue;

	public ThreadPoolServer() {
		gameQueue = new ArrayBlockingQueue<Player>(GAMES_COUNT * 2);

		/* creating game pools */
		clientPool = Executors.newFixedThreadPool(CLIENTS_COUNT);
		gamePool = Executors.newFixedThreadPool(GAMES_COUNT);
	}

	public void startDuel() {
		try {
			Player p1 = gameQueue.take();
			Player p2 = gameQueue.take();
			
			gamePool.submit(new GameHandler(p1, p2, this));
		} catch (InterruptedException e) {
			System.err.println("Failed to start duel.");
		}
	}

	@Override
	public void run() {
		InetAddress localAddress;

		try {
			localAddress = InetAddress.getLocalHost();
			System.out.println("local address is " + localAddress);
			ServerSocket serverSocket = new ServerSocket(PORT, ATTEMPT_COUNT);
			// serverSocket.setSoTimeout(TIMEOUT);
			boolean keep = true;
			while (keep) {
				Socket socket = serverSocket.accept();
				clientPool.submit(new ClientSocket(socket, this));
			}

			serverSocket.close();
			DBHelper.setup();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
