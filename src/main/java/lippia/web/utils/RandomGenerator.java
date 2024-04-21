package lippia.web.utils;

import java.util.Random;

public class RandomGenerator {

    private static final String[] FIRST_NAMES = {"Juan", "Ana", "Pedro", "Maria", "Diego", "Sofia", "Carlos", "Luisa", "Alejandro", "Valentina"};
    private static final String[] LAST_NAMES = {"Garcia", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Perez", "Sanchez", "Ramirez", "Torres"};
    private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "icloud.com"};

    public static String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
    public static String generateRandomEmail() {
        Random random = new Random();
        String firstName = RandomGenerator.generateRandomName().split(" ")[0].toLowerCase();
        String lastName = RandomGenerator.generateRandomName().split(" ")[1].toLowerCase();
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];
        int randomNumber = random.nextInt(1000);
        return firstName + "." + lastName + randomNumber + "@" + domain;
    }

}
