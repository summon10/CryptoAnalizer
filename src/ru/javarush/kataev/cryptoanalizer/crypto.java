package ru.javarush.kataev.cryptoanalizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class crypto {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'й','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю','я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я','.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private static final char[] ALPHABET_TAIL = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю','я', ' '};
    private static final String DEFAULT_PATH_TO_DECRYPTED = "D:\\crypto\\unencrypted.txt";
    private static final String DEFAULT_PATH_TO_ENCRYPTED = "D:\\crypto\\encrypted.txt";
    private static final String DEFAULT_PATH_TO_DICTIONARY = "D:\\crypto\\dictionary.txt";
    private static String strPathToDecrypted = DEFAULT_PATH_TO_DECRYPTED;
    private static String strPathToEncrypted = DEFAULT_PATH_TO_ENCRYPTED;
    private static String strPathToDictionary = DEFAULT_PATH_TO_DICTIONARY;
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
    {   String content = "";
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
        content = fileReader(Path.of(strPathToDecrypted));
        ArrayList<Character> encrypted = encryptProcessor(content);
        fileWriter(Path.of(strPathToEncrypted), encrypted);
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

        String content = fileReader(Path.of(strPathToEncrypted));
        ArrayList<Character> decrypted = decryptionProcessor(content);

        fileWriter(Path.of(strPathToDecrypted),decrypted);

    }
    private static void bruteForce()
    {
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
        content = fileReader(Path.of(strPathToEncrypted));
        analyze(content);
        ArrayList<Character> decrypted = decryptionProcessor(content);
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
    private static void stat()
    {   HashMap<Character, Integer> CharsInDictionary = new HashMap<>();
        HashMap<Character, Integer> CharsInEncrypted = new HashMap<>();
        String content="";
        String dictionary="";
        try (Scanner kbd = new Scanner(System.in)) {
            System.out.println("Enter File name you want to decrypt or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToEncrypted = kbd.nextLine();
            System.out.println("Enter Dictionary File name or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToDictionary = kbd.nextLine();
            System.out.println("Enter Decrypted File name or press Enter: ");
            if (!kbd.nextLine().isEmpty()) strPathToDecrypted = kbd.nextLine();

        }
        catch (NoSuchElementException io)
        {
            io.printStackTrace();
        }

        content = fileReader(Path.of(strPathToDecrypted));
        dictionary = fileReader(Path.of(strPathToDictionary));
        content = content.toLowerCase();
        dictionary = dictionary.toLowerCase();
        for (int i = 0; i < ALPHABET_TAIL.length; i++) {
            Character currentSymbol = ALPHABET_TAIL[i];
            Integer countInEncrypted = content.length() - content.replace(String.valueOf(currentSymbol), "").length();
            CharsInEncrypted.put(currentSymbol, countInEncrypted);
            Integer countInDictionary = dictionary.length() - dictionary.replace(String.valueOf(currentSymbol), "").length();
            CharsInDictionary.put(currentSymbol,countInDictionary);
        }
        List<Map.Entry<Character, Integer>> referenceList = new ArrayList<>(CharsInDictionary.entrySet());
        referenceList.sort(Map.Entry.comparingByValue());
        List<Map.Entry<Character, Integer>> encryptedList = new ArrayList<>(CharsInEncrypted.entrySet());
        encryptedList.sort(Map.Entry.comparingByValue());
        Map<Character, Character> charMapping = new HashMap<>();
        System.out.println(encryptedList);
        System.out.println(referenceList);
        for (int i = 0; i < encryptedList.size() && i < referenceList.size(); i++) {
            charMapping.put(encryptedList.get(i).getKey(), referenceList.get(i).getKey());
        }
        char mostCommonReferenceChar = referenceList.get(referenceList.size() - 1).getKey();
        System.out.println(charMapping);
        StringBuilder decryptedText = new StringBuilder();
        for (char c : content.toCharArray()) {
            decryptedText.append(charMapping.getOrDefault(c, mostCommonReferenceChar));
        }

        System.out.println(decryptedText.toString());
       // fileWriter(Path.of(strPathToEncrypted), decryptedText.);

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
    private static String fileReader (Path pathToRead)
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
    private static void fileWriter (Path pathToWrite, ArrayList<Character> text)
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