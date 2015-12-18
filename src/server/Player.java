package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import entities.User;

public class Player {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	User user;
	ClientSocket clientSocketHandler;
	public Player(Socket socket, BufferedReader in, PrintWriter out, ClientSocket clientSocketHandler) {
		super();
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.clientSocketHandler = clientSocketHandler;
		this.user = null;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
