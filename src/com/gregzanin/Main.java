package com.gregzanin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Album> albumList = new ArrayList<>();
        LinkedList<Song> playList = new LinkedList<>();

        //First Album Instancing
        albumList.add(new Album("Eletro Music Album", new Song("Eletro Music 1", 4.80)));
        //First Album Musics addition
        albumList.get(0).addSong("Eletro Music Two", 6.87);
        albumList.get(0).addSong("Eletro Music number 3", 5.31);

        //Second Album Instancing
        albumList.add(new Album("Gospel Music Album", new Song("Gospel Music One", 2.89)));
        //Second Album Musics addition
        albumList.get(1).addSong("Gospel Music Number 2", 1.70);
        albumList.get(1).addSong("Gospel Music nº3", 10.7);


        //Temporary music filling on the Playlist for fast testing purposes
        playList.add(albumList.get(1).getSong("Gospel Music nº3"));
        playList.add(albumList.get(0).getSong("Eletro Music number 3"));
        playList.add(albumList.get(0).getSong("Eletro Music 1"));
        playList.add(albumList.get(1).getSong("Gospel Music Number 2"));
        //**********************************************************

        Scanner sc = new Scanner(System.in);
        int menuOption;

        boolean playing = false;
        showMenu();
        do {
            menuOption = sc.nextInt();
            switch (menuOption) {
                case 1:
                    playing = true;
                    playNext(playList);
                    break;
                case 2:
                    if (!playPrevious(playing,playList)) {
                        System.out.println("You need to start playing a song before trying to play the previous!");
                    }
                    break;
                case 3:
                    if (!replayCurrent(playing,playList)){
                        System.out.println("You need to start playing a song before trying to replay it!");
                    }
                    break;
                case 4:
                    System.out.println("What is the name of the song you want to add to the playlist? (It needs to exist and be part of an album)");
                    sc.nextLine();
                    String songName = sc.nextLine();
                    System.out.println("What is the name of the album of that song?");
                    String songAlbum = sc.nextLine();
                    addSong(songName,songAlbum,playList, albumList);
                    break;
                case 5:
                    if (!removeCurrent(playing,playList)){
                        System.out.println("You need to start playing a song before trying to remove it!");
                    }
                    break;
                case 6:
                    showPlaylistSongs(playList);
                    break;
                case 7:
                    showMenu();
                    break;
                case 0:
                    System.out.println("Turning the music player off");
                    try {
                        Thread.sleep(700);
                        System.out.println("...");
                        Thread.sleep(700);
                        System.out.println("..");
                        Thread.sleep(700);
                        System.out.println(".");
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    break;
                default:
                    System.out.println("Invalid option, please select a number according to the menu!");
                    showMenu();
                    break;
            }
        } while (menuOption != 0);
    }

    private static void showMenu() {
        System.out.println("*************************************************");
        System.out.println("*     What you want to do on the playlist?      *");
        System.out.println("* 1 - Start playing/ Skip Forward to Next Song  *");
        System.out.println("* 2 - Skip Backwards to Previous Song           *");
        System.out.println("* 3 - Replay Current Song                       *");
        System.out.println("* 4 - Add a New Song                            *");
        System.out.println("* 5 - Remove Current song                       *");
        System.out.println("* 6 - Show Playlist Songs                       *");
        System.out.println("* 7 - Show Menu                                 *");
        System.out.println("* 0 - Quit                                      *");
        System.out.println("*************************************************");
    }

    private static void showPlaylistSongs(LinkedList<Song> playList) {
        ListIterator<Song> songListIterator = playList.listIterator();
        if (songListIterator.hasNext()){
            while (songListIterator.hasNext()) {
                System.out.println("Music " + (songListIterator.nextIndex() + 1) + ": " + songListIterator.next().getTitle() + ", song duration: " + songListIterator.previous().getDuration() + " Minutes");
                songListIterator.next();
            }
        }
        else {
            System.out.println("The playlist have no sounds added to it! Please add a song to it!");
        }
    }

    private static void playNext(LinkedList<Song> playList) {
        ListIterator<Song> songListIterator = playList.listIterator();
        System.out.println("Now playing: " + songListIterator.next().getTitle());
    }

    private static boolean playPrevious(boolean playing, LinkedList<Song> playList) {
        ListIterator<Song> songListIterator = playList.listIterator();
        if (playing) {
            System.out.println("Now playing: " + songListIterator.previous().getTitle());
            return true;
        }
        return false;
    }

    private static boolean replayCurrent(boolean playing, LinkedList<Song> playList) {
        ListIterator<Song> songListIterator = playList.listIterator();
        if (playing) {
            songListIterator.previous();
            System.out.println("Replaying: " + songListIterator.next().getTitle());
            return true;
        }
        return false;
    }

    private static boolean removeCurrent(boolean playing, LinkedList<Song> playList) {
        ListIterator<Song> songListIterator = playList.listIterator();
        if (playing) {
            songListIterator.previous();
            System.out.println("Song: " + songListIterator.next().getTitle() + " removed from the playlist.");
            songListIterator.remove();
            return true;
        }
        return false;
    }

    private static void addSong(String songTitle, String albumName, LinkedList<Song> playList, ArrayList<Album> albumList) {
        int position = findAlbum(albumName, albumList);
        if (position != -1) {
            for (Song object : playList) {
                if (playList.get(playList.indexOf(object)).getTitle().equalsIgnoreCase(songTitle)) {
                    System.out.println("The song " + songTitle + "have been already add to the playlist!");
                } else {
                    playList.add(new Song(songTitle, albumList.get(position).getSong(songTitle).getDuration()));
                    System.out.println("The song " + songTitle + "have been added to the playlist with success!");
                }
            }
        }
        else {
            System.out.println("That album does not exist!");
        }
    }

    private static int findAlbum(String albumName, ArrayList<Album> albumList) {
        for (Album object: albumList) {
            if (object.getName().equalsIgnoreCase(albumName)) {
                return albumList.indexOf(object);
            }
        }
        return -1;
    }
}
