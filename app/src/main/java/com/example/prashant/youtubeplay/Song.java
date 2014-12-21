package com.example.prashant.youtubeplay;

/**
 * Created by prashant on 21/12/14.
 */
public class Song {
    String name,id;

    public Song(String id, String name) {
        this.name = name;
        this.id = id;
    }
    public Song()
    {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
