package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JokeConnector {
    public static final String API_URL = "https://v2.jokeapi.dev/joke/";

    public String getJokeData(String JokeCategory) throws IOException {

        URL url = new URL(API_URL + JokeCategory + "?format=json");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        System.out.println("Connected");

        int status = con.getResponseCode();
        System.out.println("Status: " + status);

        BufferedReader in = new BufferedReader( //for efficiency
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    public String getJokeString(String JokeCategory) throws IOException {
        String data = getJokeData(JokeCategory);
        JSONTokener tokener = new JSONTokener(data);
        JSONObject currencyObject = new JSONObject(tokener);

        String type = currencyObject.getString("type");
        if (type.equals("twopart")) {
            String setup = currencyObject.getString("setup");
            String delivery = currencyObject.getString("delivery");
            return setup +" "+ delivery;

        } else {
            String joke = currencyObject.getString("joke");
            return joke;
        }
    }

    public Joke getJoke(String JokeCatgory) throws IOException {
        String jokeData = getJokeData(JokeCatgory);
        JSONTokener tokener = new JSONTokener(jokeData);
        JSONObject jokeObject = new JSONObject(tokener);

        Joke joke = new Joke();
        joke.category = jokeObject.getString("category");
        joke.type = jokeObject.getString("type");
        joke.joke = jokeObject.has("joke") ? jokeObject.getString("joke") : null;
        joke.setup = jokeObject.has("setup") ? jokeObject.getString("setup") : null;
        joke.delivery = jokeObject.has("delivery") ? jokeObject.getString("delivery") : null;
        // this is the same as: if(jokeObject.has("delivery")){joke.delivery=jokeObject.getString("delivery");} else {joke.delivery = null;}
        joke.id = jokeObject.getInt("id");
        joke.safe = jokeObject.getBoolean("safe");
        joke.language = jokeObject.getString("lang");
        joke.nsfw = jokeObject.getJSONObject("flags").getBoolean("nsfw");
        joke.religious = jokeObject.getJSONObject("flags").getBoolean("religious");
        joke.political = jokeObject.getJSONObject("flags").getBoolean("political");
        joke.racist = jokeObject.getJSONObject("flags").getBoolean("racist");
        joke.sexist = jokeObject.getJSONObject("flags").getBoolean("sexist");
        joke.explicit = jokeObject.getJSONObject("flags").getBoolean("explicit");

        return joke;

    }
}
