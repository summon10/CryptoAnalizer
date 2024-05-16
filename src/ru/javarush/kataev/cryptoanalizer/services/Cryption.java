package ru.javarush.kataev.cryptoanalizer.services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static ru.javarush.kataev.cryptoanalizer.constants.Const.*;
import static ru.javarush.kataev.cryptoanalizer.constants.Const.strPathToEncrypted;
import static ru.javarush.kataev.cryptoanalizer.files.FilesWorker.*;

public class Cryption {

    public static void encryptor ()
    {   String content = "";
        try (Scanner kbd = new Scanner(System.in))
        {
            System.out.println("Enter File name you want to encrypt or press Enter for default "+ DEFAULT_PATH_TO_DECRYPTED+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();
            System.out.println("Enter Encrypted File name or press Enter for default " + DEFAULT_PATH_TO_ENCRYPTED+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter key for encryption: ");
            key = (kbd.nextInt())% ALPHABET.length;
        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }
        content = fileReader(Path.of(strPathToDecrypted));
        ArrayList<Character> encrypted = encryptProcessor(content);
        fileWriter(Path.of(strPathToEncrypted), encrypted);
    }

    private static ArrayList<Character> encryptProcessor (String content)
    {
        char[] forEncryption = content.toCharArray();
        char[] encrypted = new char[content.length()];

        for (int i = 0; i < forEncryption.length; i++) {
            for (int j = 0; j < ALPHABET.length; j++) {

                if (forEncryption[i] == ALPHABET[j]) {
                    if (j + key >= ALPHABET.length) {
                        encrypted[i] = ALPHABET[(j + key) % (ALPHABET.length)];

                    }
                    else {
                        encrypted[i] = ALPHABET[j + key];

                    }
                }

            }
        }
        Character[] encryptedArray = new Character[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            encryptedArray[i] = Character.valueOf(encrypted[i]);
        }
        ArrayList<Character> characterArrayList = new ArrayList<>(Arrays.asList(encryptedArray));
        for (int i = 0; i < characterArrayList.size(); i++) {
            if (characterArrayList.get(i)=='\u0000')
            {
                characterArrayList.remove(i);
                i--;
            }
        }
        return characterArrayList;
    }
}

