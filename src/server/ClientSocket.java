package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONObject;

import db.DBHelper;
import entities.*;

public class ClientSocket extends Thread {
	protected Socket socket;
	protected boolean keep;
	protected boolean closeSocket;
	protected BufferedReader in;
	protected PrintWriter out;
	protected ThreadPoolServer server;
	protected Player player;
	protected boolean inGame;

	public ClientSocket(Socket socket, ThreadPoolServer server) {
		this.server = server;
		server.stats_overAllConnections++;
		System.out.println("Connection recieved" + socket.getInetAddress());
		this.socket = socket;
		this.keep = true;
		closeSocket = true;
		inGame = false;
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			player = new Player(socket, in, out, this);

		} catch (IOException e) {
			e.printStackTrace();
			keep = false;
		}

	}

	@Override
	public void run() {
		server.stats_currentClientThreads++;
		while (keep) {
			try {
				while(inGame){
					//System.out.println("player in game");
					sleep(1000);
				}
				
				String req = null;
				JSONObject jres = new JSONObject();

				req = in.readLine();
				System.out.println("got " + req);
				JSONObject jobj = new JSONObject(req);
				String cmd = jobj.getString("cmd");
				// STATUS
				if (cmd.equals("status")) {
					jres.put("success", 1);
					if (Server.status)
						jres.put("status", "online");
					else {
						jres.put("status", "offline");
						keep = false;
					}
					out.println(jres.toString());
					out.flush();
				}
				// STATUS
				else if (cmd.equals("login")) {
					String userId = jobj.getString("userId");
					String userPassword = jobj.getString("pwd");

					User user = DBHelper.authenticate(userId, userPassword);
					jres.put("success", (user != null) ? 1 : 0);
					if (user != null) {
						server.stats_overAllLogins++;
						jres.put("user", user.toJSONObject());
						player.setUser(user);
					}

					out.println(jres.toString());
					out.flush();
				} 
				// MakeUser
				else if (cmd.equals("signup")) {
					User user = new User(jobj.getString("phone"),
							jobj.getString("userId"), jobj.getString("email"),
							jobj.getString("pwd"));

					/* Check add use result */
					if (DBHelper.addUser(user)) {
						server.stats_overAllRegisters++;
						jres.put("success", 1);
					} else {
						jres.put("success", 0);
						jres.put("errorId", 3);
					}
					out.println(jres.toString());
					out.flush();
				} 
				// JoinQueue
				else if (cmd.equals("joinQueue")) {

					/* did the user login before requesting to play */
					if (player.user != null) {
						server.gameQueue.put(player);
						out.println("{\"success\": 1}");

						inGame = true;
						closeSocket = false;
						
						if (server.gameQueue.size() >= 2)
							server.startDuel();
						
					} else {
						out.println("{\"success\": 0}");
					}
					out.flush();
				} else if (cmd.equals("listUsers")) {
					ArrayList<User> users = DBHelper.getAll();
					JSONObject juser = new JSONObject();
					System.out.println(users.size());
					for(int i = 0; i < users.size(); i++) {
						juser = users.get(i).toJSONObject();
						juser.put("success", 1);
						out.println(juser.toString());
						out.flush();
					}
					out.println("{\"success\": 0}");
					out.flush();
				} 
				
				/* monitoring requests */
				else if (cmd.equals("serverStats"))
				{
					if (jobj.getString("key").equals(
							"qskdqsd5qsd6q1sd561qs51qs1d5qsd")) {
						jres.put("success", 1);

						jres.put("currentClientThreads",
								server.stats_currentClientThreads);
						jres.put("overAllConnections",
								server.stats_overAllConnections);

						jres.put("overAllLogins", server.stats_overAllLogins);

						jres.put("currentGameThreads",
								server.stats_currentGameThreads);
						jres.put("overAllGames", server.stats_overAllGames);

						jres.put("overAllRegisters",
								server.stats_overAllRegisters);

						Runtime runtime = Runtime.getRuntime();

						long maxMemory = runtime.maxMemory();
						long allocatedMemory = runtime.totalMemory();
						long freeMemory = runtime.freeMemory();

						jres.put("maxMemory", maxMemory);
						jres.put("allocatedMemory", allocatedMemory);
						jres.put("freeMemory", freeMemory);

						// System.out.println("sending: "+jres.toString());

						out.println(jres.toString());
						out.flush();
					}
				}else if (cmd.equals("logout"))
				{
					keep = false;
				}
			} catch (IOException e) {
				keep = false;
				System.out.println("IOException.");
			} catch (InterruptedException e) {
				System.err.println("Interrupted exception "
						+ socket.getInetAddress() + " -> abording connection");
				keep = false;
			}
		}
		try {
			if(closeSocket)
				socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.stats_currentClientThreads--;
		System.out.println("A user is leaving");
	}
}

// Game,Space graphics by <a href="http://www.freepik.com/">Freepik</a> from <a
// href="http://www.flaticon.com/">Flaticon</a> are licensed under <a
// href="http://creativecommons.org/licenses/by/3.0/"
// title="Creative Commons BY 3.0">CC BY 3.0</a>. Made with <a
// href="http://logomakr.com" title="Logo Maker">Logo Maker</a>