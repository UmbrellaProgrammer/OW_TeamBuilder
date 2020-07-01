package Hero;

import java.util.ArrayList;
import java.util.List;

public class HeroList {

    // A private method for generating a List of all Hero objects
    private List<Hero> generateListOfAllHeroObjects() throws HeroLoadException {

        HeroFileLoader heroFileLoader = new HeroFileLoader();
        List<Hero> listOfAllHeroObjects;

        try {

            listOfAllHeroObjects = heroFileLoader.load();

        } catch (HeroLoadException | WeaponTypeException e) {

            System.out.println("Either a HeroLoadException or a WeaponTypeException was thrown by HeroFileLoader.load()");
            e.printStackTrace();

            throw new HeroLoadException("A HeroLoadException or WeaponTypeException has occurred!");
        }

        return listOfAllHeroObjects;
    }


    // A method that generates a List of Tank Hero Objects
    public List<Hero> generateListOfTankHeroObjects() throws HeroLoadException {

        List<Hero> listOfAllHeroObjects;
        List<Hero> listOfTankObjects = new ArrayList<>();

        try {

            listOfAllHeroObjects = generateListOfAllHeroObjects();

        } catch (HeroLoadException e) {

            throw new HeroLoadException("A HeroLoadException or WeaponTypeException has occurred!");
        }

        for (Hero hero : listOfAllHeroObjects) {

            if (hero.isTankRole()) {

                listOfTankObjects.add(hero);
            }
        }

        return listOfTankObjects;
    }


    // A method for generating a List of DPS Hero Objects
    public List<Hero> generateListOfDpsHeroObjects() throws HeroLoadException {

        List<Hero> listOfAllHeroObjects;
        List<Hero> listOfDpsObjects = new ArrayList<>();

        try {

            listOfAllHeroObjects = generateListOfAllHeroObjects();

        } catch (HeroLoadException e) {

            throw new HeroLoadException("A HeroLoadException or WeaponTypeException has occurred!");
        }

        for (Hero hero : listOfAllHeroObjects) {

            if (hero.isDpsRole()) {

                listOfDpsObjects.add(hero);
            }
        }

        return listOfDpsObjects;
    }


    // A method for generating a List of Healer Hero Objects
    public List<Hero> generateListOfHealerHeroObjects() throws HeroLoadException {

        List<Hero> listOfAllHeroObjects;
        List<Hero> listOfHealerObjects = new ArrayList<>();

        try {

            listOfAllHeroObjects = generateListOfAllHeroObjects();

        } catch (HeroLoadException e) {

            throw new HeroLoadException("A HeroLoadException or WeaponTypeException has occurred!");
        }

        for (Hero hero : listOfAllHeroObjects) {

            if (hero.isHealerRole()) {

                listOfHealerObjects.add(hero);
            }
        }

        return listOfHealerObjects;
    }


    public List<String> generateListOfTankHeroStrings(List<Hero> listOfTankHeroObjects) {

        List<String> listOfTankHeroStrings = new ArrayList<>();

        for (Hero hero : listOfTankHeroObjects) {

            String tankName = hero.getHeroName();
            String lowerCaseTankHeroName = tankName.toLowerCase();

            listOfTankHeroStrings.add(lowerCaseTankHeroName);
        }

        return listOfTankHeroStrings;
    }


    public List<String> generateListOfDpsHeroStrings(List<Hero> listOfDpsHeroObjects) {

        List<String> listOfDpsHeroStrings = new ArrayList<>();

        for (Hero hero : listOfDpsHeroObjects) {

            String dpsName = hero.getHeroName();
            String lowerCaseDpsHeroName = dpsName.toLowerCase();

            listOfDpsHeroStrings.add(lowerCaseDpsHeroName);
        }

        return listOfDpsHeroStrings;
    }


    public List<String> generateListOfHealerHeroStrings(List<Hero> listOfHealerHeroObjects) {

        List<String> listOfHealerHeroStrings = new ArrayList<>();

        for (Hero hero : listOfHealerHeroObjects) {

            String healerName = hero.getHeroName();
            String lowerCaseHealerHeroName = healerName.toLowerCase();

            listOfHealerHeroStrings.add(lowerCaseHealerHeroName);
        }

        return listOfHealerHeroStrings;
    }

}
