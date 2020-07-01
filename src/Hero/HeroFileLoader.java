package Hero;

/* * * * *
 * This loader reads the text file in the following format:
 * HeroName,HeroRole,WeaponType,Sniper?,CounterSniper?,CounterPharah?,CounterFlank?,ShieldBreaker?,Power
 * Example:
 * Winston,Tank,NoShield,No,Yes,No,Yes,1
 * Widowmaker,DPS,Hitscan,Yes,Yes,Yes,No,1
 * Note: For tanks, WeaponType is replaced by ShieldType, which can be either Shield or NoShield
 * * * * */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HeroFileLoader {


    /* * * * * *
     * the Public method that Analyzer uses to fetch a List of Hero objects.
     * A method that parses each line in the String list and creates a Hero object
     * then loads that Hero object into a List<Hero>
     * * * * * */
    // A method that reads the data file line-by-line into a list of strings

    /**
     * Loads a list of heroes from a file.
     * @return a list of heroes
     * @throws HeroLoadException if the file could not be loaded
     */
    public List<Hero> load() throws HeroLoadException, WeaponTypeException {

        List<Hero> listOfHeroObjects = new ArrayList<>();
        List<String> listOfDataStrings;

        try {

            listOfDataStrings = readTextStringsFromFile();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            throw new HeroLoadException("Unable to load hero file!", e);
        }


        for (String string : listOfDataStrings) {
            // split the strings into a temporary list of substrings that vanishes after each loop
            String[] listOfSubstringsOfTheDataStrings = string.split(",");

            // give a name to each substring in the String Array
            String heroNameAsString = listOfSubstringsOfTheDataStrings[0];
            String heroRoleAsString = listOfSubstringsOfTheDataStrings[1];
            String weaponTypeAsString = listOfSubstringsOfTheDataStrings[2];
            String isSniperAsString = listOfSubstringsOfTheDataStrings[3];
            String isSniperCounterAsString = listOfSubstringsOfTheDataStrings[4];
            String isPharahCounterAsString = listOfSubstringsOfTheDataStrings[5];
            String isFlankCounterAsString = listOfSubstringsOfTheDataStrings[6];
            String isShieldBreakerAsString = listOfSubstringsOfTheDataStrings[7];
            String powerAsString = listOfSubstringsOfTheDataStrings[8];
            int powerAsInt = Integer.parseInt(powerAsString);

            HeroRole heroRole;

            // construct the Enum lists
            try {

                heroRole = constructHeroRole(heroRoleAsString);

            } catch (HeroRoleException e) {

                e.printStackTrace();

                throw new HeroLoadException("Unable to determine Hero's role!", e);
            }

            List<WeaponType> weaponTypeList;

            try {

                 weaponTypeList = constructWeaponType(weaponTypeAsString);

            } catch (WeaponTypeException e) {

                System.out.println("WeaponTypeException thrown. Unable to identify a Hero's weapon type!");
                throw new WeaponTypeException("Unable to identify a Hero's weapon type!");
            }

            List<RoleSpecialty> roleSpecialtyList = constructRoleSpecialty(isSniperAsString, isSniperCounterAsString,
                    isPharahCounterAsString, isFlankCounterAsString, isShieldBreakerAsString);

            // Feed enum Lists into Hero constructor to get Hero object
            Hero heroObject = new Hero(heroNameAsString, heroRole, weaponTypeList,
                    roleSpecialtyList, powerAsInt);

            // add this Hero object to the List<Hero>
            listOfHeroObjects.add(heroObject);
        }

        return listOfHeroObjects;
    }


    private List<String> readTextStringsFromFile() throws FileNotFoundException {

        List<String> listOfDataStrings = new ArrayList<>();

        String databaseFilePath = "C:\\Users\\scien\\IdeaProjects\\teambuilder\\src\\Database\\TeamBuilder_Database.txt";

        Scanner scanner = new Scanner(new File(databaseFilePath));

        while (scanner.hasNextLine()) {

            listOfDataStrings.add(scanner.nextLine());
        }

        return listOfDataStrings;
    }


    // These methods use Strings to construct Enums
    private HeroRole constructHeroRole(String heroRoleAsString) throws HeroRoleException {

        HeroRole heroRole;

        if (heroRoleAsString.contains("tank")) {

            heroRole = HeroRole.TANK;

        } else if (heroRoleAsString.contains("dps")) {

            heroRole = HeroRole.DPS;

        } else if (heroRoleAsString.contains("healer")) {

            heroRole = HeroRole.HEALER;

        } else {

            System.out.println("If this happens, check the DB file, and also check to see if heroRoleAsString was instantiated correctly.");

            throw new HeroRoleException("Unable to determine the Hero's role!");
        }

        return heroRole;
    }


    private List<WeaponType> constructWeaponType(String weaponTypeAsString) throws WeaponTypeException {

        List<WeaponType> weaponTypeList = new ArrayList<>();

        if (weaponTypeAsString.contains("noshield")) {

            weaponTypeList.add(WeaponType.NOSHIELD);

        } else if (weaponTypeAsString.contains("shield")) {

            weaponTypeList.add(WeaponType.SHIELD);

        } else if (weaponTypeAsString.contains("aoe")) {

            weaponTypeList.add(WeaponType.AOE);

        } else if (weaponTypeAsString.contains("single-target")) {

            weaponTypeList.add(WeaponType.SINGLETARGET);

        } else if (weaponTypeAsString.contains("close-range")) {

            weaponTypeList.add(WeaponType.CLOSERANGE);

        } else if (weaponTypeAsString.contains("projectile")) {

            weaponTypeList.add(WeaponType.PROJECTILE);

        } else if (weaponTypeAsString.contains("hitscan")) {

            weaponTypeList.add(WeaponType.HITSCAN);

        } else {

            System.out.println("If WeaponTypeException occurs, check the DB file, and also check to see if weaponTypeAsString was instantiated correctly.");
            throw new WeaponTypeException("Unable to identify a Hero's weapon type!");
        }

        return weaponTypeList;
    }


    private List<RoleSpecialty> constructRoleSpecialty(String isSniperAsString, String isSniperCounterAsString,
                                                       String isPharahCounterAsString, String isFlankCounterAsString,
                                                       String isShieldBreakerAsString) {

        List<RoleSpecialty> roleSpecialtyList = new ArrayList<>();

        if (isSniperAsString.contains("yes")) {

            roleSpecialtyList.add(RoleSpecialty.SNIPER);
        }

        if (isSniperCounterAsString.contains("yes")) {

            roleSpecialtyList.add(RoleSpecialty.COUNTERSNIPER);
        }

        if (isPharahCounterAsString.contains("yes")) {

            roleSpecialtyList.add(RoleSpecialty.COUNTERPHARAH);
        }

        if (isFlankCounterAsString.contains("yes")) {

            roleSpecialtyList.add(RoleSpecialty.COUNTERFLANK);
        }

        if (isShieldBreakerAsString.contains("yes")) {

            roleSpecialtyList.add(RoleSpecialty.SHIELDBREAKER);
        }

        return roleSpecialtyList;
    }
}
