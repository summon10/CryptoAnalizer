package ru.javarush.kataev.cryptoanalizer.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static ru.javarush.kataev.cryptoanalizer.constants.Const.*;
import static ru.javarush.kataev.cryptoanalizer.files.FilesWorker.*;

public class StatisticalAnalysis {
    private static char mostCommonReferenceChar;
    public static void stat()
    {
        String content="";
        String dictionary="";
        try (Scanner kbd = new Scanner(System.in)) {
            System.out.println("Enter File name you want to decrypt or press Enter for default "+ DEFAULT_PATH_TO_ENCRYPTED+ " : ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter Dictionary File name or press Enter for default"+ DEFAULT_PATH_TO_DICTIONARY+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToDictionary = kbd.nextLine();
            System.out.println("Enter Decrypted File name or press Enter for default"+ DEFAULT_PATH_TO_DECRYPTED_BY_STAT+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToDecryptedByStat = kbd.nextLine();

        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }

        content = fileReader(Path.of(strPathToDecrypted));
        dictionary = fileReader(Path.of(strPathToDictionary));
        HashMap<Character,Character> charMapping= statisticalProcessor (content,dictionary);
        StringBuilder decryptedText = new StringBuilder();
        for (char c : content.toCharArray()) {
            decryptedText.append(charMapping.getOrDefault(c,mostCommonReferenceChar));
        }
        System.out.println(decryptedText);
        try {
            Files.writeString(Path.of(strPathToDecryptedByStat), decryptedText.toString());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }


    }

    private static HashMap<Character,Character> statisticalProcessor (String content, String dictionary)
    {
        HashMap <Character, Integer> CharsInDictionary = new HashMap<>();
        HashMap<Character, Integer> CharsInEncrypted = new HashMap<>();
        content = content.toLowerCase();
        dictionary = dictionary.toLowerCase();
        for (Character currentSymbol : ALPHABET_TAIL) {
            Integer countInEncrypted = content.length() - content.replace(String.valueOf(currentSymbol), "").length();
            CharsInEncrypted.put(currentSymbol, countInEncrypted);
            Integer countInDictionary = dictionary.length() - dictionary.replace(String.valueOf(currentSymbol), "").length();
            CharsInDictionary.put(currentSymbol, countInDictionary);
        }
        List<Map.Entry<Character, Integer>> referenceList = new ArrayList<>(CharsInDictionary.entrySet());
        referenceList.sort(Map.Entry.comparingByValue());
        List<Map.Entry<Character, Integer>> encryptedList = new ArrayList<>(CharsInEncrypted.entrySet());
        encryptedList.sort(Map.Entry.comparingByValue());
        HashMap<Character, Character> charMapping = new HashMap<>();
        for (int i = 0; i < encryptedList.size() && i < referenceList.size(); i++) {
            charMapping.put(encryptedList.get(i).getKey(), referenceList.get(i).getKey());
        }
        mostCommonReferenceChar = referenceList.get(referenceList.size() - 1).getKey();
        return charMapping;
    }
}
