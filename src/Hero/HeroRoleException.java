package Hero;

public class HeroRoleException extends Exception {

    public HeroRoleException(String message) {
        super(message);
    }

    public HeroRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}