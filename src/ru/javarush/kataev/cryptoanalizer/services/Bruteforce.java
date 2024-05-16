package ru.javarush.kataev.cryptoanalizer.services;

import java.nio.file.Path;
import java.util.*;

import static ru.javarush.kataev.cryptoanalizer.constants.Const.*;
import static ru.javarush.kataev.cryptoanalizer.constants.Const.key;
import static ru.javarush.kataev.cryptoanalizer.files.FilesWorker.*;

public class Bruteforce {
    public static void bruteForce()
    {
        String content = "";
        try (Scanner kbd = new Scanner(System.in)) {
            System.out.println("Enter File name you want to decrypt or press Enter for default"+DEFAULT_PATH_TO_ENCRYPTED+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter Decrypted File name or press Enter for default"+DEFAULT_PATH_TO_DECRYPTED+ " : ");
            if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();
        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }
        content = fileReader(Path.of(strPathToEncrypted));
        analyze(content);
        ArrayList<Character> decrypted = Decryption.decryptionProcessor(content);
        fileWriter(Path.of(strPathToDecrypted),decrypted);

    }

    private static void analyze(String content) {
        HashMap<Character, Integer> countOfSymbols = new HashMap<>();
        Integer maxCount = 0;
        Character mostFrequencySymbol=null;
        int posOfMostFrequencySymbol = 0;
        int posOfSpace = 0;
        for (int i = 0; i < ALPHABET.length; i++) {
            Character currentSymbol = ALPHABET[i];
            Integer count = content.length() - content.replace(String.valueOf(currentSymbol), "").length();
            countOfSymbols.put(currentSymbol, count);
            if (count > maxCount) maxCount = count;

        }

        Collection<Character> count = countOfSymbols.keySet();
        for (Character temp : count)
        {
            Integer value = countOfSymbols.get(temp);
            if (maxCount.equals(value)) mostFrequencySymbol = temp;
        }

        for (int i = 0; i < ALPHABET.length; i++) {
            if (mostFrequencySymbol.equals(ALPHABET[i])) posOfMostFrequencySymbol = i;
            if (ALPHABET[i]== ' ') posOfSpace = i;
        }
        if (posOfMostFrequencySymbol - posOfSpace>= 0) key = posOfMostFrequencySymbol - posOfSpace;
        if (posOfMostFrequencySymbol - posOfSpace < 0) key = (ALPHABET.length - (-1*(posOfMostFrequencySymbol - posOfSpace)));

        System.out.println(key);

    }
}
