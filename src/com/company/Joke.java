package com.company;

import org.json.JSONObject;

import java.io.Serializable;

public class Joke implements Serializable {
    public String category;
    public String type;
    public String joke;
    public String setup;
    public String delivery;
    public Integer id;
    public Boolean safe;
    public String language;
    public Boolean nsfw;
    public Boolean religious;
    public Boolean political;
    public Boolean racist;
    public Boolean sexist;
    public Boolean explicit;

    public Integer getJokeLength() { //for sorting by length
        if (this.joke != null) {
            return this.joke.length();
        } else {
            return this.setup.length() + delivery.length();
        }
    }

    public String toString() {
        if (this.joke != null) {
            return this.joke;
        } else {
            return this.setup + " " + delivery;
        }
    }

    public Joke (JSONObject jokeObject){
        category = jokeObject.getString("category");
        type = jokeObject.getString("type");
        joke = jokeObject.has("joke") ? jokeObject.getString("joke") : null;
        setup = jokeObject.has("setup") ? jokeObject.getString("setup") : null;
        delivery = jokeObject.has("delivery") ? jokeObject.getString("delivery") : null;
        // this is the same as: if(jokeObject.has("delivery")){joke.delivery=jokeObject.getString("delivery");} else {joke.delivery = null;}
        id = jokeObject.getInt("id");
        safe = jokeObject.getBoolean("safe");
        language = jokeObject.getString("lang");
        nsfw = jokeObject.getJSONObject("flags").getBoolean("nsfw");
        religious = jokeObject.getJSONObject("flags").getBoolean("religious");
        political = jokeObject.getJSONObject("flags").getBoolean("political");
        racist = jokeObject.getJSONObject("flags").getBoolean("racist");
        sexist = jokeObject.getJSONObject("flags").getBoolean("sexist");
        explicit = jokeObject.getJSONObject("flags").getBoolean("explicit");
    }

}
