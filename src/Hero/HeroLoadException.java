package Hero;

public class HeroLoadException extends Exception {

    public HeroLoadException(String message) {
        super(message);
    }

    public HeroLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
