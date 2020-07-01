package Hero;

public class WeaponTypeException extends Exception {

    public WeaponTypeException(String message) {

        super(message);
    }

    public WeaponTypeException(String message, Throwable cause) {

        super(message, cause);
    }

}