package ru.javarush.kataev.cryptoanalizer.constants;

public class Const {
     public static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'й','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю','я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я','.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
   public static final char[] ALPHABET_TAIL = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю','я', ' '};
    public static final String DEFAULT_PATH_TO_DECRYPTED = "D:\\crypto\\unencrypted.txt";
    public static final String DEFAULT_PATH_TO_ENCRYPTED = "D:\\crypto\\encrypted.txt";
    public static final String DEFAULT_PATH_TO_DICTIONARY = "D:\\crypto\\dictionary.txt";
    public static final String DEFAULT_PATH_TO_DECRYPTED_BY_STAT = "D:\\crypto\\decrypted_by_stat.txt";
    public static String strPathToDecrypted = DEFAULT_PATH_TO_DECRYPTED;
    public static String strPathToEncrypted = DEFAULT_PATH_TO_ENCRYPTED;
    public static String strPathToDictionary = DEFAULT_PATH_TO_DICTIONARY;
    public static String strPathToDecryptedByStat = DEFAULT_PATH_TO_DECRYPTED_BY_STAT;
    public static int key = 0 ;

}
