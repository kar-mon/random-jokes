package com.company;

import netscape.javascript.JSException;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JokeConnector connector = new JokeConnector();
        Scanner scan = new Scanner(System.in);

        List<Joke> jokeList = new LinkedList<Joke>();
        String JokeCategory;
        String download;
        System.out.println("Do you want to download jokes?");
        download = scan.nextLine();
        if (download.equals("yes")) {
            System.out.println("Joke categories available are: programming, misc, dark, pun, spooky, Christmas");
            System.out.println("Type the joke category to get random dad joke or exit");
            JokeCategory = scan.nextLine();
            System.out.println("You chose " + JokeCategory);

            try {
                Joke joke = connector.getJoke(JokeCategory);
                jokeList.add(joke);
            } catch (IOException e) {
                System.out.println("Something went wrong");
            } catch (JSONException e) {
                System.out.println("Something went wrong 2"); //yes, I know
            }
        } else {

            do {
                System.out.println("Joke categories available are: programming, misc, dark, pun, spooky, Christmas");
                System.out.println("Type the joke category to get random dad joke or exit");
                JokeCategory = scan.nextLine();
                System.out.println("You chose " + JokeCategory);

                try {
                    String joke = connector.getJokeString(JokeCategory);
                    System.out.println(joke);
                } catch (IOException e) {
                    System.out.println("Something went wrong");
                } catch (JSONException e) {
                    System.out.println("Something went wrong 2"); //yes, I know
                }
            } while (!JokeCategory.equals("exit"));
        }
    }
}


