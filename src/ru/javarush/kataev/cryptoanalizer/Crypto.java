package ru.javarush.kataev.cryptoanalizer;

import ru.javarush.kataev.cryptoanalizer.services.Bruteforce;
import ru.javarush.kataev.cryptoanalizer.services.Cryption;
import ru.javarush.kataev.cryptoanalizer.services.Decryption;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static ru.javarush.kataev.cryptoanalizer.constants.Const.*;
import static ru.javarush.kataev.cryptoanalizer.services.StatisticalAnalysis.stat;

public class Crypto {

    public static void main(String[] args) {
        try (Scanner kbd = new Scanner(System.in)) {
           int choice;
            System.out.println("\n Welcome! Enter your choice \n1 - encryption\n2 - decryption\n3 - brute force\n4 - stat");
                choice = kbd.nextInt();
                switch (choice) {
                    case 1 ->
                        Cryption.encryptor();
                    case 2 ->
                        Decryption.decrypt();
                    case 3 ->
                        Bruteforce.bruteForce();
                    case 4 ->
                        stat();
                    default -> System.out.println("Enter the number form 1 to 4!");

                }

        }
    }
}



