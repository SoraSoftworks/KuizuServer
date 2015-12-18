package server;

/*
 * List of available ports:
 * http://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;

import kuizu.*;
import db.*;
import entities.*;

public class Server 
{
	/*
	private static final String dbUser = "root";
	private static final String dbPwd  = "pwd123ROOT++";
	*/
	
	public static final boolean status = true;
	
	public static String JSON_SUCCESS = "{\"success\": 1}"; 
	
	public static void main(String args[])
	{
		System.out.println("Starting server.");
		
		ThreadPoolServer tps = new ThreadPoolServer();
		tps.run();
		
//		Quizz q1 = Countries.genQuizz();
//		System.out.println("quizz: "+q1);
	}
}
