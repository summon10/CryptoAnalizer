package ru.javarush.kataev.cryptoanalizer.services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static ru.javarush.kataev.cryptoanalizer.constants.Const.*;
import static ru.javarush.kataev.cryptoanalizer.constants.Const.strPathToDecrypted;
import static ru.javarush.kataev.cryptoanalizer.files.FilesWorker.*;

public class Decryption {
    public static void decrypt()
    {
        try (Scanner kbd = new Scanner(System.in))
        {
            System.out.println("Enter File name you want to decrypt or press Enter for default"+DEFAULT_PATH_TO_ENCRYPTED+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter Decrypted File name or press Enter for default"+DEFAULT_PATH_TO_DECRYPTED+" : ");
            if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();
            System.out.println("Enter key for decryption: ");
            key = (kbd.nextInt())% ALPHABET.length;
        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }

        String content = fileReader(Path.of(strPathToEncrypted));
        ArrayList<Character> decrypted = decryptionProcessor(content);

        fileWriter(Path.of(strPathToDecrypted),decrypted);

    }
    public static ArrayList<Character> decryptionProcessor (String content)
    {
        char[] forDecryption = content.toCharArray();
        char[] decrypted = new char[content.length()];

        for (int i = 0; i < forDecryption.length; i++) {
            for (int j = 0; j < ALPHABET.length; j++) {

                if (forDecryption[i] == ALPHABET[j]) {
                    if (j - key < 0 ) {
                        decrypted[i] = ALPHABET[ALPHABET.length - (-1*(j - key))];

                    }
                    else {
                        decrypted[i] = ALPHABET[j - key];

                    }
                }

            }
        }
        Character[] decryptedArray = new Character[decrypted.length];
        for (int i = 0; i < decrypted.length; i++) {
            decryptedArray[i] = Character.valueOf(decrypted[i]);
        }
        ArrayList<Character> characterArrayList = new ArrayList<>(Arrays.asList(decryptedArray));
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
