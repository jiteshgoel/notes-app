package index1.developer.acadview.com.mynotesapp;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by user on 30-May-18.
 */

public class Utilities {

    public static final String EXTRAS_NOTE_FILENAME = "EXTRAS_NOTE_FILENAME";
    public static final String FILE_EXTENSION = ".bin";

    public static boolean saveNote(Context context, Note note) {
        String filename = String.valueOf(note.getmDateTime()) + FILE_EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(filename, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            //tell user the note was saved!
            return false;
        }

        return true;
    }

    public static ArrayList<Note> getAllSavedNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        //add .bin files to the noteFiles list
        for (String file : filesDir.list()) {
            if (file.endsWith(FILE_EXTENSION)) {
                noteFiles.add(file);
            }
        }

        //read objects and add to list of notes
        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < noteFiles.size(); i++) {
            try {
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);

                notes.add((Note) ois.readObject());
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return notes;
    }

    public static Note getNoteByFileName(Context context, String fileName) {

        File file = new File(context.getFilesDir(), fileName);
        if (file.exists() && !file.isDirectory()) { //check if file actually exist

            Log.v("UTILITIES", "File exist = " + fileName);

            FileInputStream fis;
            ObjectInputStream ois;

            try { //load the file
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                Note note = (Note) ois.readObject();
                fis.close();
                ois.close();

                return note;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

    }

    public static void deleteNote(Context context, String filename) {
        File dir = context.getFilesDir();
        File f = new File(dir, filename);

        if(f.exists())
        {
            f.delete();
        }

    }
}
