package com.gregzanin;

import java.util.ArrayList;

/**
 * Created by GR389658 on 05/10/2017.
 */
public class Album {
    private String name;
    private ArrayList<Song> songList = new ArrayList<Song>();

    public Album(String name, Song firstSong) {
        this.name = name;
        this.songList.add(firstSong);
    }

    public boolean addSong(String title, double duration){
        if (findSong(title) == -1){
            this.songList.add(new Song(title,duration));
            return true;
        }
        return false;
    }

    public boolean deleteSong(String title){
        if (findSong(title) >= -1){
            this.songList.remove(findSong(title));
        return true;
        }
        return false;
    }

    private int findSong(String title){
        for (Song object: songList) {
            if (object.getTitle().equalsIgnoreCase(title)) {
                return songList.indexOf(object);
            }
        }
        return -1;
    }

    public Song getSong(String title) {
        int position = findSong(title);
        if (position > -1){
            return songList.get(position);
        }
        return null;
    }

    public String getName() {
        return name;
    }

}
