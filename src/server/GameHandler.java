package server;

import java.io.IOException;

import kuizu.Countries;
import kuizu.Quiz;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBHelper;
import entities.Duel;

public class GameHandler extends Thread {
	Player p1, p2;
	boolean keep = true;
	ThreadPoolServer tps;
	JSONObject p1SuccessMsg;
	JSONObject p2SuccessMsg;
	ThreadPoolServer server;
	JSONArray result;

	double p1Score = 0, p2Score = 0;

	public GameHandler(Player p1, Player p2, ThreadPoolServer server) {
		super();
		//System.out.println("starting duel");
		this.server = server;
		this.p1 = p1;
		this.p2 = p2;
		p1.clientSocketHandler.inGame = true;
		p2.clientSocketHandler.inGame = true;
	}

	@Override
	public void run() {
		server.stats_currentGameThreads++;
		server.stats_overAllGames++;
		if (keep) {
			try {
				result = new JSONArray();
				
				p1SuccessMsg = new JSONObject();
				p2SuccessMsg = new JSONObject();
				JSONObject o1, o2;
				
				// FindDuel

				/* message for player 1 */
				p1SuccessMsg.put("success", 1);
				p1SuccessMsg.put("player", p2.user.toJSONObject());

				/* message for player 2 */
				p2SuccessMsg.put("success", 1);
				p2SuccessMsg.put("player", p1.user.toJSONObject());

				/*
				 * PRE DUEL Sending and accepting confirmations
				 */

				// ConfirmDuel
				p1.out.println(p1SuccessMsg.toString());
				p1.out.flush();
				p2.out.println(p2SuccessMsg.toString());
				p2.out.flush();

				System.out.println("reading ready messages");

				/* Accepting ready confirmations to start duel */
				JSONObject p1Ready = new JSONObject(p1.in.readLine());

				System.out.println("1");

				JSONObject p2Ready = new JSONObject(p2.in.readLine());

				System.out.println("All");

				assert (p1Ready.getInt("ready") == 1);
				assert (p2Ready.getInt("ready") == 1);

				p1SuccessMsg.remove("player");
				p2SuccessMsg.remove("player");

				p1.out.println(p1SuccessMsg.toString());
				p1.out.flush();
				p2.out.println(p2SuccessMsg.toString());
				p2.out.flush();

				System.out.println("Duel 1");
				duel();
				duel();
				duel();

				/* res for p1 */
				JSONObject p1Res = new JSONObject();
				p1Res.put("status", 1);
				p1Res.put("result", p1Score > p2Score);
				p1Res.put("player_score", p2Score);
				p1Res.put("answers", result);
				p1.out.println(p1Res.toString());
				p1.out.flush();

				/* res for p2 */
				JSONObject p2Res = new JSONObject();
				p2Res.put("status", 1);
				p2Res.put("result", p2Score > p1Score);
				p2Res.put("player_score", p1Score);
				p2Res.put("answers", result);
				p2.out.println(p2Res.toString());
				p2.out.flush();
				
				DBHelper.addDuel(new Duel(p1.user.getId(), p2.user.getId(),
						(p1Score > p2Score) ? p1.user.getId() : p2.user.getId()));

				p1.user.setElo(elo.EloRatingSystem.newRating(p1.user.getElo(),
						p2.user.getElo(),
						(p1Score > p2Score) ? (elo.EloRatingSystem.SCORE_WIN)
								: (elo.EloRatingSystem.SCORE_LOSS)));
				p2.user.setElo(elo.EloRatingSystem.newRating(p2.user.getElo(),
						p1.user.getElo(),
						(p2Score > p1Score) ? (elo.EloRatingSystem.SCORE_WIN)
								: (elo.EloRatingSystem.SCORE_LOSS)));

				DBHelper.updateUser(p1.user);
				DBHelper.updateUser(p2.user);

				System.out.println("end of game");

			} catch (Exception e) {

			}

			p1.clientSocketHandler.inGame = false;
			p2.clientSocketHandler.inGame = false;

		}
		server.stats_currentGameThreads--;
	}

	protected void duel() {
		JSONObject o1, o2;

		JSONObject q1 = new JSONObject();
		q1.put("status", 1);
		
		System.out.println("generating quiz");

		Quiz quizz1 = Countries.genQuizz();
		
		System.out.println("saving result");

		result.put(quizz1.getDetails());
		
		System.out.println("Start!");

		try {
			q1.put("question", quizz1.getQuestion());
			q1.put("c_answer", quizz1.getCorrect());
			q1.put("answer1", quizz1.getFalseAnswer1());
			q1.put("answer2", quizz1.getFalseAnswer2());

			System.out.println("Sending data 1");
			
			p1.out.println(q1.toString());
			p1.out.flush();
			p2.out.println(q1.toString());
			p2.out.flush();

			System.out.println("Reading data");
			
			o1 = new JSONObject(p1.in.readLine());
			o2 = new JSONObject(p2.in.readLine());

			/* send success msg */
			p1.out.println(p1SuccessMsg.toString());
			p1.out.flush();
			p2.out.println(p2SuccessMsg.toString());
			p2.out.flush();

			if (!o1.isNull("answer")) {
				String res = o1.getString("answer");
				if (res.equals(quizz1.getCorrect())) {
					p1Score += (o1.getDouble("time")) * 50;
				}
			}

			if (!o2.isNull("answer")) {
				String res = o2.getString("answer");
				if (res.equals(quizz1.getCorrect())) {
					p2Score += (o2.getDouble("time")) * 50;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
