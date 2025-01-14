package mate.academy.rickandmorty.service;

public class GenerateRandomDigitService {
    public static final int MAX_DIGIT = 827;

    public String getRandomDigit() {
        int random = (int) (Math.random() * MAX_DIGIT);
        return String.valueOf(random);
    }
}
