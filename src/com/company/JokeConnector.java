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
            return setup + " " + delivery;

        } else {
            String joke = currencyObject.getString("joke");
            return joke;
        }
    }

    public Joke getJoke(String JokeCatgory) throws IOException {
        String jokeData = getJokeData(JokeCatgory);
        JSONTokener tokener = new JSONTokener(jokeData);
        JSONObject jokeObject = new JSONObject(tokener);

        Joke joke = new Joke(jokeObject);

        return joke;

    }
}
