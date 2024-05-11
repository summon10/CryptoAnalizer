package ru.javarush.kataev.cryptoanalizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class crypto {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Я','.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private static final char[] ALPHABET_TAIL = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', ' '};
    private static final String DEFAULT_PATH_TO_DECRYPTED = "D:\\crypto\\unencrypted.txt";
    private static final String DEFAULT_PATH_TO_ENCRYPTED = "D:\\crypto\\encrypted.txt";
    private static String strPathToDecrypted = DEFAULT_PATH_TO_DECRYPTED;
    private static String strPathToEncrypted = DEFAULT_PATH_TO_ENCRYPTED;
    public static int key = 0 ;

    public static void main(String[] args) {

        try (Scanner kbd = new Scanner(System.in)) {

            int choice;

            System.out.println("\n Welcome! Enter your choice \n1 - encryption\n2 - decryption\n3 - brute force\n4 - stat");

                choice = kbd.nextInt();
                switch (choice) {
                    case 1 ->
                        encryptor();

                    case 2 ->
                        decrypt();

                    case 3 ->
                        bruteForce();

                    case 4 ->
                        stat();
                    default -> System.out.println("Enter the number form 1 to 4!");

                }

        }
    }
    private static void encryptor ()
    {
        try (Scanner kbd = new Scanner(System.in))
        {
        System.out.println("Enter File name you want to encrypt or press Enter: ");
        if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();
        System.out.println("Enter Encrypted File name or press Enter: ");
        if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
        System.out.println("Enter key for encryption: ");
        key = (kbd.nextInt())% ALPHABET.length;
    }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }

        Path pathToInputUnencrypted = Path.of(strPathToDecrypted);
        Path pathToOutputEncrypted = Path.of(strPathToEncrypted);
        if (!(Files.exists(pathToInputUnencrypted)))
        {
            System.out.println("Unencrypted file doesn't exists");
        }
        String content = null;
        try (BufferedReader reader = Files.newBufferedReader(pathToInputUnencrypted))
        {
            String tempLine;
            while((tempLine = reader.readLine()) != null){
                content = content + tempLine;
            }

        } catch (IOException e) {
            e.getMessage();
        }
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
        try (BufferedWriter writer = Files.newBufferedWriter(pathToOutputEncrypted))
        {
            for (Character s : characterArrayList) {
                writer.write(String.valueOf(s));
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }


    private static void decrypt()
    {
        try (Scanner kbd = new Scanner(System.in))
        {
            System.out.println("Enter File name you want to decrypt or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter Decrypted File name or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();
            System.out.println("Enter key for decryption: ");
            key = (kbd.nextInt())% ALPHABET.length;
        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }
        String content = "";
        Path pathToInputEncrypted = Path.of(strPathToEncrypted);
        Path pathToOutputDecrypted = Path.of(strPathToDecrypted);
        try (BufferedReader reader = Files.newBufferedReader(pathToInputEncrypted))
        {
            String tempLine;
            while((tempLine = reader.readLine()) != null){
                content = content + tempLine;
            }

        } catch (IOException e) {
            e.getMessage();
        }
        ArrayList<Character> decrypted = decryptionProcessor(content);

        try (BufferedWriter writer = Files.newBufferedWriter(pathToOutputDecrypted))
        {
            for (Character s : decrypted) {
                writer.write(String.valueOf(s));
            }

        } catch (IOException e) {
            e.getMessage();
        }



    }
    private static void bruteForce()
    {
        char space = ' ';
        String content = "";
        try (Scanner kbd = new Scanner(System.in)) {
            System.out.println("Enter File name you want to decrypt or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter Decrypted File name or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();
        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }
        Path pathToInputEncrypted = Path.of(strPathToEncrypted);
        Path pathToOutputDecrypted = Path.of(strPathToDecrypted);
        try (BufferedReader reader = Files.newBufferedReader(pathToInputEncrypted))
        {
            String tempLine;
            while((tempLine = reader.readLine()) != null){
                content = content + tempLine;
            }

        } catch (IOException e) {
            e.getMessage();
        }

        analyze(content);
        ArrayList<Character> decrypted = decryptionProcessor(content);

        try (BufferedWriter writer = Files.newBufferedWriter(pathToOutputDecrypted))
        {
            for (Character s : decrypted) {
                writer.write(String.valueOf(s));
            }

        } catch (IOException e) {
            e.getMessage();
        }
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
    private static void stat()
    {

    }
    private static ArrayList<Character> decryptionProcessor (String content)
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