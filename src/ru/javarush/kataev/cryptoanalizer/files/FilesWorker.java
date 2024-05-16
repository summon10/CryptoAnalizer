package ru.javarush.kataev.cryptoanalizer.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FilesWorker {
    public static String fileReader (Path pathToRead)
    {
        String content="";
        try (BufferedReader reader = Files.newBufferedReader(pathToRead))
        {
            String tempLine;
            while((tempLine = reader.readLine()) != null){
                content = content + tempLine;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }
    public static void fileWriter (Path pathToWrite, ArrayList<Character> text)
    {
        try (BufferedWriter writer = Files.newBufferedWriter(pathToWrite))
        {
            for (Character s : text) {
                writer.write(String.valueOf(s));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


