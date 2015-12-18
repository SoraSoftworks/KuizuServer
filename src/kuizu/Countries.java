package kuizu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

public class Countries {

	public static String[] attributes = new String[] { 
		"name",
		"capital",
		"region", 
		"subregion", 
		"population", 
		"alpha2Code" };

	public static String formulateQuestion(String toFind, String criteria,
			String criteriaValue) {
		if (toFind.equals("name")) {
			if (criteria.equals("capital")) {
				return "What's the country which capital is " + criteriaValue
						+ "?";
			}
			if (criteria.equals("region")) {
				return "What's the country located in the " + criteriaValue
						+ " region ?";
			}
			if (criteria.equals("subregion")) {
				return "What country is located in the " + criteriaValue
						+ " subregion ?";
			}
			if (criteria.equals("population")) {
				return "What country does have a population of "
						+ criteriaValue + "?";
			}
			if (criteria.equals("alpha2Code")) {
				return "What country does have the code " + criteriaValue
						+ "?";
			}
		}
		if (toFind.equals("capital")) {
			if (criteria.equals("name")) {
				return "What's the capital of " + criteriaValue + "?";
			}
			if (criteria.equals("region")) {
				return "Which of the following capitals is located in "
						+ criteriaValue + "?";
			}
			if (criteria.equals("subregion")) {
				return "Which of the follwing capitals is located in  "
						+ criteriaValue + "?";
			}
			if (criteria.equals("population")) {
				return "What's the capital that its country has a population of "
						+ criteriaValue + "?";
			}
			if (criteria.equals("alpha2Code")) {
				return "What's the capital that its country has the code "
						+ criteriaValue + "?";
			}
		}
		if (toFind.equals("region")) {
			if (criteria.equals("name")) {
				return "In which region is the country " + criteriaValue
						+ " located?";
			}
			if (criteria.equals("capital")) {
				return "Which one of the regions contains the capital "
						+ criteriaValue + "?";
			}
			if (criteria.equals("subregion")) {
				return "Where is the subregion " + criteriaValue + " located?";
			}
			if (criteria.equals("population")) {
				return null;
			}
			if (criteria.equals("alpha2Code")) {
				return "What's region is having a county of code "
						+ criteriaValue + "?";
			}
		}
		if (toFind.equals("subregion")) {
			if (criteria.equals("name")) {
				return "In which subregion is the country " + criteriaValue
						+ " located?";
			}
			if (criteria.equals("capital")) {
				return "Which one of the capitals is located in the subregion "
						+ criteriaValue + "?";
			}
			if (criteria.equals("region")) {
				return "Which of the following subregions is located in region "
						+ criteriaValue + "?";
			}
			if (criteria.equals("population")) {
				return null;
			}
			if (criteria.equals("alpha2Code")) {
				return "Which of the following subregions does have a county of code "
						+ criteriaValue + "?";
			}
		}
		if (toFind.equals("population")) {
			if (criteria.equals("name")) {
				return "Which of the following countries has a population of "
						+ criteriaValue + "?";
			}
			if (criteria.equals("capital")) {
				return "From the following capitals, which one's country has a population of "
						+ criteriaValue + "?";
			}
			if (criteria.equals("region")) {
				return null;
			}
			if (criteria.equals("subregion")) {
				return null;
			}
			if (criteria.equals("alpha2Code")) {
				return "What's the country's population that has the code "
						+ criteriaValue + " has ?";
			}
		}
		if (toFind.equals("alpha2Code")) {
			if (criteria.equals("name")) {
				return "What's the code of " + criteriaValue + "?";
			}
			if (criteria.equals("capital")) {
				return "What is the code of the country having the capital "
						+ criteriaValue + "?";
			}
			if (criteria.equals("region")) {
				return "What's the code of the country located in "
						+ criteriaValue + "?";
			}
			if (criteria.equals("subregion")) {
				return "What's the code of the country located in "
						+ criteriaValue + "?";
			}
			if (criteria.equals("population")) {
				return null;
			}
		}

		return null;
	}

	public static Quiz genQuizz() {
		Quiz q = null;
		String u = "http://restcountries.eu/rest/v1/all";
		String json = "";
		JSONArray jArr = null;

		HttpURLConnection conn = null;

		try {
			URL url = new URL(u);
			conn = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			json = sb.toString();

			jArr = new JSONArray(json);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* selecting random fields */
		int random1 = 0;
		int random2 = 0;
		String toFind;
		String criteria;
		String question;

		do {
			random1 = (int) (Math.random() * 6);
			do {
				random2 = (int) (Math.random() * 6);
			} while (random2 == random1);

			toFind = attributes[random1];
			criteria = attributes[random2];
			question = formulateQuestion(toFind, criteria, "");
		} while (question == null);
		
		
		/* selecting random country */
		int maxE = jArr.length();
		int randomCountry = (int) (Math.random() * maxE);
		String correct = "";
		String criteriaValue = "";
		JSONObject jObj = jArr.getJSONObject(randomCountry);

		correct = get(jObj, toFind);
		criteriaValue = get(jObj, criteria);

		/* False Response 1 */
		String falseResponse2 = "";
		String criteriaValue2 = "";
		int randomCountry2 = 0;
		do {
			randomCountry2 = (int) (Math.random() * maxE);
			JSONObject jObj2 = jArr.getJSONObject(randomCountry2);
			falseResponse2 = get(jObj2, toFind);
			criteriaValue2 = get(jObj2, criteria);

		} while (criteriaValue.equals(criteriaValue2));

		/* False Response 2 */
		String falseResponse3 = "";
		String criteriaValue3 = "";
		int randomCountry3 = 0;
		do {
			randomCountry3 = (int) (Math.random() * maxE);
			JSONObject jObj3 = jArr.getJSONObject(randomCountry3);
			falseResponse3 = get(jObj3, toFind);
			criteriaValue3 = get(jObj3, criteria);

		} while (criteriaValue.equals(criteriaValue3)
				|| criteriaValue2.equals(criteriaValue3));

		
		/* registering result details */
		JSONObject resultJSONDetails = new JSONObject();
		for(String e: attributes)
		{
			resultJSONDetails.put(e, jObj.get(e));
		}
		
		/* creating quiz */
		q = new Quiz(formulateQuestion(toFind, criteria, criteriaValue),
				correct, falseResponse2, falseResponse3, resultJSONDetails);

		return q;
	}

	static String get(JSONObject jObj, String key) {
		String value = null;
		try {
			value = jObj.getString(key);
		} catch (JSONException e) {
			value = "" + jObj.getInt(key);
			System.out.println("Exception: "+e.getMessage());
		}

		return value;
	}
}
