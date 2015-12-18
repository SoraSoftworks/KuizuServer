package db;

import entities.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.*;

public class DBHelper {
	/* DATABASE LOGIN INFO */
	private static final String DATABASE_NAME = "quizzduel";
	private static final String DATABASE_USER = "root";
	private static final String DATABASE_PASSWORD = "pwd123ROOT++";
//	private static final String DATABASE_PASSWORD = "";

	private static final String DATABASE_LOCATION = "localhost";

	/* TABLE USER */
	final private static String TABLE_USER = "user";
	final private static String USER_ID = "id";
	final private static String USER_PHONE_NUM = "phone_num";
	final private static String USER_USER_ID = "user_id";
	final private static String USER_EMAIL_ADDR = "email_addr";
	final private static String USER_PASSWORD = "password_";
	final private static String USER_JOIN_DATE = "join_date";
	final private static String USER_ELO = "elo";
	final private static String USER_LAST_LOGIN = "last_login";

	/* TABLE DUEL */
	final private static String TABLE_DUEL = "duel";
	final private static String DUEL_ID = "id";
	final private static String DUEL_USER_1 = "id_user1";
	final private static String DUEL_USER_2 = "id_user2";
	final private static String DUEL_PLAY_DATE = "play_date";
	final private static String DUEL_WINNER_ID = "winner_id";

	public static void setup() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Connection connection = null;
		Statement statment = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + DATABASE_NAME,
					DATABASE_USER, DATABASE_PASSWORD);
			statment = connection.createStatement();
			resultSet = statment.executeQuery("SELECT * FROM " + TABLE_USER);

			while (resultSet.next()) {
				User user = new User(resultSet.getString(USER_PHONE_NUM),
						resultSet.getString(USER_USER_ID),
						resultSet.getString(USER_EMAIL_ADDR),
						resultSet.getString(USER_PASSWORD));
				user.setId(resultSet.getInt(USER_ID));
				user.setElo(resultSet.getInt(USER_ELO));
				user.setJoinDate(resultSet.getTimestamp(USER_JOIN_DATE));
				user.setLastLogin(resultSet.getTimestamp(USER_LAST_LOGIN));
				System.out.println(user);
			}

			resultSet.close();
			resultSet = statment.executeQuery("SELECT * FROM " + TABLE_DUEL);

			while (resultSet.next()) {
				Duel duel = new Duel(resultSet.getInt(DUEL_USER_1),
						resultSet.getInt(DUEL_USER_2),
						resultSet.getInt(DUEL_WINNER_ID));
				duel.setId(resultSet.getInt(DUEL_ID));
				duel.setPlayDate(resultSet.getTimestamp(DUEL_PLAY_DATE));
				System.out.println(duel);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				resultSet.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				statment.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}
	}

	public static Connection connect() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/"
				+ DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
	}

	/* USER */

	public static boolean addUser(User user) {

		Connection connection = null;
		PreparedStatement statement = null;
		boolean success = false;

		try {
			connection = connect();
			statement = connection.prepareStatement("INSERT INTO " + TABLE_USER
					+ "(" + USER_PHONE_NUM + ", " + USER_USER_ID + ", "
					+ USER_EMAIL_ADDR + ", " + USER_PASSWORD
					+ ") values (?, ?, ?, ?);");
			statement.setString(1, user.getPhoneNumber());
			statement.setString(2, user.getUserId());
			statement.setString(3, user.getEmailAddress());
			statement.setString(4, user.getPassword());

			success = statement.executeUpdate() != 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}

		finally {
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

		return success;
	}

	public static User authenticate(String userId, String userPassword) {
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connect();
			statement = connection.prepareStatement("SELECT * FROM "
					+ TABLE_USER + " WHERE ((" + USER_USER_ID + " = ?) AND ("
					+ USER_PASSWORD + " = ?));");

			statement.setString(1, userId);
			statement.setString(2, userPassword);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				user = new User(resultSet.getString(USER_PHONE_NUM),
						resultSet.getString(USER_USER_ID),
						resultSet.getString(USER_EMAIL_ADDR),
						resultSet.getString(USER_PASSWORD));
				user.setId(resultSet.getInt(USER_ID));
				user.setElo(resultSet.getInt(USER_ELO));
				user.setJoinDate(resultSet.getTimestamp(USER_JOIN_DATE));
				user.setLastLogin(resultSet.getTimestamp(USER_LAST_LOGIN));
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				resultSet.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

		return user;
	}

	public static User findUserById(int id) {
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connect();
			statement = connection.prepareStatement("SELECT * FROM "
					+ TABLE_USER + " WHERE (" + USER_ID + " = ?);");

			statement.setInt(1, id);

			resultSet = statement.executeQuery();

			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

			if (resultSet.next()) {

				user = new User(resultSet.getString(USER_PHONE_NUM),
						resultSet.getString(USER_USER_ID),
						resultSet.getString(USER_EMAIL_ADDR),
						resultSet.getString(USER_PASSWORD));
				user.setId(resultSet.getInt(USER_ID));
				user.setElo(resultSet.getInt(USER_ELO));
				user.setJoinDate(resultSet.getTimestamp(USER_JOIN_DATE));
				user.setLastLogin(currentTimestamp);

				/* update the timestamp */
				updateUser(user);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				resultSet.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

		return user;
	}

	public static boolean updateUser(User user) {

		Connection connection = null;
		PreparedStatement statement = null;
		boolean success = false;

		try {
			connection = connect();
			statement = connection.prepareStatement("UPDATE " + TABLE_USER
					+ " SET " + USER_PHONE_NUM + " = ?, " + USER_EMAIL_ADDR
					+ " = ?, " + USER_PASSWORD + " = ?, " + USER_ELO + " = ?, "
					+ USER_LAST_LOGIN + " = ? WHERE " + USER_ID + " = ?;");
			statement.setString(1, user.getPhoneNumber());
			statement.setString(2, user.getEmailAddress());
			statement.setString(3, user.getPassword());
			statement.setInt(4, user.getElo());
			statement.setTimestamp(5, user.getLastLogin());
			statement.setInt(6, user.getId());

			success = statement.executeUpdate() != 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

		return success;
	}

	public static ArrayList<User> getAll() {
		ArrayList<User> users = new ArrayList<>();
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean success = false;

		try {
			connection = connect();
			statement = connection.prepareStatement("SELECT * FROM "
					+ TABLE_USER + " ORDER BY " + USER_ELO + " DESC;");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				user = new User(resultSet.getString(USER_PHONE_NUM),
						resultSet.getString(USER_USER_ID),
						resultSet.getString(USER_EMAIL_ADDR),
						resultSet.getString(USER_PASSWORD));
				user.setId(resultSet.getInt(USER_ID));
				user.setElo(resultSet.getInt(USER_ELO));
				user.setJoinDate(resultSet.getTimestamp(USER_JOIN_DATE));

				users.add(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				resultSet.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}
		return users;
	}

	public static boolean addDuel(Duel duel) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean success = false;
		try {
			connection = connect();
			statement = connection.prepareStatement("INSERT INTO " + TABLE_DUEL
					+ "(" + DUEL_USER_1 + ", " + DUEL_USER_2 + ", "
					+ DUEL_WINNER_ID + ") values (?, ?, ?);");
			statement.setInt(1, duel.getIdUser1());
			statement.setInt(2, duel.getIdUser2());
			statement.setInt(3, duel.getIdWinner());

			success = statement.executeUpdate() != 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

		return success;
	}

	public static ArrayList<Duel> duelsOf(int idUser) {
		ArrayList<Duel> duels = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connect();
			statement = connection.prepareStatement("SELECT * FROM "
					+ TABLE_DUEL + " WHERE (" + DUEL_USER_1 + " = ?) OR ("
					+ DUEL_USER_2 + " = ?);");
			statement.setInt(1, idUser);
			statement.setInt(2, idUser);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Duel duel = new Duel(resultSet.getInt(DUEL_USER_1),
						resultSet.getInt(DUEL_USER_2),
						resultSet.getInt(DUEL_WINNER_ID));
				duel.setId(resultSet.getInt(DUEL_ID));
				duel.setPlayDate(resultSet.getTimestamp(DUEL_PLAY_DATE));
				duels.add(duel);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				resultSet.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}
		}

		return duels;
	}

}
