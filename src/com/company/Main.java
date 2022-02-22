package com.company;

import netscape.javascript.JSException;
import org.json.JSONException;

import javax.swing.*;
import java.io.*;
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
- download more jokes and add it to existing list*/

    public static void main(String[] args) {

        List<Joke> jokes = new ArrayList<>();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("How many jokes do you want to download?");
            int size = Integer.parseInt(scanner.nextLine());
            jokes.addAll(downloadJokes(size));
            System.out.println("You now have " + jokes.size() + " jokes at your list");

            SortJokeListByLength(jokes);
            System.out.println("Shortest joke is: ");
            System.out.println(jokes.get(0));

            System.out.println("Would you like to save downloaded jokes to a file? yes / no");
            String save = scanner.nextLine();
            if (save.equals("yes")) {
                saveJokesToFile(jokes);
            }

            var religiousJokes = getReligiousJokes(jokes);
            if (religiousJokes.size() != 0) {
                String relig;
                System.out.println("Do you want to see downloaded religious jokes? yes / no");
                relig = scanner.nextLine();
                if (relig.equals("yes")) {
                    System.out.println("Downloaded religious jokes: ");
                    System.out.println(religiousJokes);
                }
            }

            System.out.println("Do you want to see how many two part jokes were downloaded? yes / no");
            String twoPart;
            twoPart = scanner.nextLine();
            if (twoPart.equals("yes")) {
                var twoPartJokesCounter = countTwoPartJokes(jokes);
                System.out.println("You downloaded " + twoPartJokesCounter + "two part jokes");
                System.out.println(jokes);
            }

            String delete;
            System.out.println("Do you want to delete downloaded jokes from a list? yes / no");
            delete = scanner.nextLine();
            if (delete.equals("yes")) {
                // remove all elements using clear() method
                jokes.clear();
            }
            System.out.println("Do you want to download more jokes? yes / no");
            String downloadMore = scanner.nextLine();
            if (!downloadMore.equals("yes")) {
                break;
            }
        }
    }

    private static List<Joke> downloadJokes(int size) {
        JokeConnector connector = new JokeConnector();
        //creating a list of jokes
        List<Joke> jokes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                jokes.add(connector.getJoke("Any"));
            } catch (Exception e) {
                System.out.println("Sorry, we have a problem");
                e.printStackTrace();
            }
        }
        return jokes;
    }

    private static void SortJokeListByLength(List<Joke> jokes) {
        Comparator<Joke> jokeComparator = Comparator.comparing(Joke::getJokeLength);
        Collections.sort(jokes, jokeComparator);
    }

    private static void saveJokesToFile(List<Joke> jokes) {
        try {
            FileWriter fw = new FileWriter("jokes.ser");
            Writer output = new BufferedWriter(fw);
            int numEntries = jokes.size();
            for (int i = 0; i < numEntries; i++) {
                output.write(jokes.get(i).toString() + "\n");
            }
            output.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File cannot be created");
        }
        System.out.println("Successfully saved downloaded jokes");
    }

    private static List<Joke> getReligiousJokes(List<Joke> jokes) {
        List<Joke> religiousJokes = new ArrayList<>();
        for (var joke : jokes) {
            if (joke.religious) {
                religiousJokes.add(joke);
            }
        }
        return religiousJokes;
    }

    private static int countTwoPartJokes(List<Joke> jokes) {
        int twoPartJokesCounter = 0;
        for (var joke : jokes) {
            if (joke.setup != null) {
                twoPartJokesCounter++;
            }
        }
        return twoPartJokesCounter;
    }
}
