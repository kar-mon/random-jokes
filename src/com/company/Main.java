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

import java.util.*;

public class Main {

/* Create a program in which user can:
- download a bunch of jokes (user should define how many)
- find shortest joke in this bunch
- print all downloaded religious jokes
- count all two-part jokes
- delete downloaded jokes
todo    - download more jokes and add it to existing list*/

    public static void main(String[] args) {
        JokeConnector connector = new JokeConnector();
        Scanner scanner = new Scanner(System.in);
        //creating a list of jokes
        List<Joke> jokes = new ArrayList<>();

        System.out.println("How many jokes do you want to download?");
        int size = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < size; i++) {
            try {
                jokes.add(connector.getJoke("Any"));
            } catch (Exception e) {
                System.out.println("Sorry, we have a problem");
                e.printStackTrace();
            }
        }
        System.out.println("You downloaded " + jokes.size() + " jokes");

        SortJokeListByLength(jokes);
        //System.out.println(jokes);
        System.out.println("Shortest downloaded joke is: ");
        System.out.println(jokes.get(0));

        String relig;
        List<Joke> religiousJokes = new ArrayList<>();
        for (var joke : jokes) {
            if (joke.religious) {
                religiousJokes.add(joke);
            }
        }
        if (religiousJokes.size() != 0) {
            System.out.println("Do you want to see downloaded religious jokes? yes / no");
            relig = scanner.nextLine();
            if (relig.equals("yes")) {
                System.out.println("Downloaded religious jokes: ");
                System.out.println(religiousJokes);
            }
        }


        String twoPart;
        System.out.println("Do you want to see how many two part jokes were downloaded? yes/no");
        twoPart = scanner.nextLine();
        if (twoPart.equals("yes")) {
            int twoPartJokesCounter = 0;
            for (var joke : jokes) {
                if (joke.setup != null) {
                    twoPartJokesCounter++;
                }
            }
            System.out.println("You downloaded " + twoPartJokesCounter + "two part jokes");
            System.out.println(jokes);

        }

        String delete;
        System.out.println("Do you want to delete downloaded jokes? yes/no");
        delete = scanner.nextLine();
        if (delete.equals("yes")) {
            // remove all elements using clear() method
            jokes.clear();
        }
    }


    private static void SortJokeListByLength(List<Joke> jokes) {
        Comparator<Joke> jokeComparator
                = Comparator.comparing(Joke::getJokeLength);

        Collections.sort(jokes, jokeComparator);

    }
}
