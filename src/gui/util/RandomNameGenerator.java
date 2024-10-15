package gui.util;

import java.util.Random;

public class RandomNameGenerator {

    private static final String[] FIRST_NAMES = {
        "Carlos", "Maria", "João", "Ana", "Pedro", "Paula", "Bruno", "Fernanda", "Ricardo", "Luiza"
    };
    
    private static final String[] LAST_NAMES = {
        "Silva", "Santos", "Oliveira", "Souza", "Pereira", "Lima", "Costa", "Gomes", "Martins", "Almeida"
    };
    
    public static String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }
}
