package com.plottwist.solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.DataFormat.URL;

/**
 * Single Responsibility Principle -> A class should have one reason to change.
 * If you end up with a lot of responsibilities,
 * you end up with a God object, the Antipattern.
 */

public class SingleResponsibility {

}

/**
 * Journal.class handles entries and that is its
 * only reason to change.
 */
class Journal {
    private static int count = 0;
    private final List<String> entries = new ArrayList<>();

    public void addEntry(String text) {
        entries.add("" + (++count) + ": " + text);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }
    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    /**
     * This part of the code will break the Single Responsibility Principle -
     * The Journal.class is taking on a new concern -> persistence
     *                  *Separation of concerns*
     */
    public void save(String filename) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(filename)) {
            out.println(toString());
        }
    }
    public void load(String filename){}
    public void load(java.net.URL url){}
}
/**
 *  Persistence could be achieved as follows:
 */
class Persistence{
    public void saveToFile(Journal journal,
                           String filename, boolean overwrite) throws Exception{
        if(overwrite||new File(filename).exists()){
            try (PrintStream out = new PrintStream(filename)) {
                out.println(journal.toString());
            }
        }
    }
}

class Demo {
    public static void main(String[] args) throws Exception{
        Journal j = new Journal();
        Persistence p = new Persistence();
        //C:\Users\Julian.Irigoyen\Desktop\DesignPatterns
        String filename = "C:\\Users\\Julian.Irigoyen\\Desktop\\DesignPatterns\\journal.txt";

        j.addEntry("Hi");
        j.addEntry("This is awesome");
        System.out.println(j);

        p.saveToFile(j,filename, true);
        Runtime.getRuntime().exec("notepad.exe " + filename);
    }
}
