package gui.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class RandomGenerator {

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
    
    public static String generateRandomBirthDate() {
        Random random = new Random();
        
        int minAge = 16;
        int maxAge = 90;
        
        int randomAge = minAge + random.nextInt(maxAge - minAge + 1);
        
        LocalDate today = LocalDate.now();
        LocalDate birthDate = today.minus(randomAge, ChronoUnit.YEARS);
        
        int randomDayOfYear = random.nextInt(birthDate.lengthOfYear());
        birthDate = birthDate.withDayOfYear(Math.min(randomDayOfYear + 1, birthDate.lengthOfYear()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return birthDate.format(formatter);
    }
    

    public static String generateRandomRegistrationNumber() {
        Random random = new Random();
        StringBuilder registrationNumber = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                registrationNumber.append(" ");
            }
            for (int j = 0; j < 4; j++) {
                int digit = random.nextInt(10);
                registrationNumber.append(digit);
            }
        }
        return registrationNumber.toString();
    }
    
    public static String generateRandomZoneAndSectionNumber() {
    	Random random = new Random();
    	StringBuilder zoneSectionNumber = new StringBuilder();
    	
    	for(int i =0; i < 4; i++) {
    		int digit = random.nextInt(10);
    		zoneSectionNumber.append(digit);
    	}
    	return zoneSectionNumber.toString();
    }
}
