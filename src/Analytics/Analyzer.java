package Analytics;

import Hero.Hero;
import Hero.HeroFileLoader;
import Hero.WeaponTypeException;

import java.util.ArrayList;
import java.util.List;
import Hero.HeroRoleException;
import Hero.HeroLoadException;


public class Analyzer {

    private List<String> listOfSelectedHeroesAsStrings;

    // constructor
    public Analyzer(List<String> listOfSelectedHeroesAsStrings) {

        this.listOfSelectedHeroesAsStrings = listOfSelectedHeroesAsStrings;
    }


    // a team analysis method that fires only if allHeroSelectionsAreAcceptable method passes
    public String analyzeTeamComposition() throws HeroLoadException, WeaponTypeException {

        String analysis = "";

        HeroFileLoader heroFileLoader = new HeroFileLoader();
        List<Hero> listOfAllHeroObjects;

        try {

            listOfAllHeroObjects = heroFileLoader.load();

        } catch (HeroLoadException e) {

            System.out.println("Unable to identify Hero role!");
            throw new HeroLoadException("Unable to load Hero file!", e);

        } catch (WeaponTypeException e) {

            System.out.println("Unable to identify a Hero's weapon type!");
            throw new WeaponTypeException("Unable to identify a Hero's weapon!");
        }

        List<Hero> listOfSelectedHeroesAsObjects = getListOfSelectedHeroObjects(listOfSelectedHeroesAsStrings, listOfAllHeroObjects);

        int numberOfSnipers = checkNumberOfSnipers(listOfSelectedHeroesAsObjects);
        int numberOfSniperCounters = checkNumberOfSniperCounters(listOfSelectedHeroesAsObjects);
        int numberOfPharahCounters = checkNumberOfPharahCounters(listOfSelectedHeroesAsObjects);
        int numberOfShieldBreakers = checkNumberOfShieldBreakers(listOfSelectedHeroesAsObjects);
        int numberOfFlankCounters = checkNumberOfFlankCounters(listOfSelectedHeroesAsObjects);
        int numberOfCloseRangeDps = checkNumberOfCloseRangeDps(listOfSelectedHeroesAsObjects);
        int numberOfShieldTanks = checkNumberOfShieldTanks(listOfSelectedHeroesAsObjects);
        int overallTeamDpsPower = calculateOverallDpsPower(listOfSelectedHeroesAsObjects);
        int overallTeamHealerPower = calculateOverallHealerPower(listOfSelectedHeroesAsObjects);


        // be sure to set String = String + "/n..."

        analysis = analyzeTeamTankComposition(analysis, numberOfShieldTanks);
        analysis = analyzeTeamDpsComposition(analysis, numberOfSnipers, numberOfSniperCounters,
                                             numberOfPharahCounters, numberOfShieldBreakers,
                                             numberOfFlankCounters, numberOfCloseRangeDps,
                                             overallTeamDpsPower);
        analysis = analyzeTeamHealerComposition(analysis, overallTeamHealerPower);

        if (analysis.isEmpty()) {

            analysis = "Nice composition! This team should be able to handle most threats.";
        }

        return analysis;
    }


    // A method that returns a list of Hero objects to represent the user's selections
    private List<Hero> getListOfSelectedHeroObjects(List<String> listOfSelectedHeroes, List<Hero> listOfAllHeroObjects) {

        List<Hero> listOfSelectedHeroObjects = new ArrayList<>();

        for (String selectedHeroName : listOfSelectedHeroes) {

            for (Hero heroObject : listOfAllHeroObjects) {

                if (selectedHeroName.equalsIgnoreCase(heroObject.getHeroName())) {

                    listOfSelectedHeroObjects.add(heroObject);
                }
            }
        }
        return listOfSelectedHeroObjects;
    }


    //a method that checks for Pharah counters
    private int checkNumberOfPharahCounters(List<Hero>listOfSelectedHeroesAsObjects) {

        int numberOfPharahCounters = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isPharahCounter()) {

                numberOfPharahCounters++;
            }
        }

        return numberOfPharahCounters;
    }


    // a method that checks for sniper counters
    private int checkNumberOfSniperCounters(List<Hero>listOfSelectedHeroesAsObjects) {

        int numberOfSniperCounters = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isSniperCounter()) {

                numberOfSniperCounters++;
            }
        }

        return numberOfSniperCounters;
    }


    // a method that checks for the number of DPS who are snipers
    private int checkNumberOfSnipers(List<Hero>listOfSelectedHeroesAsObjects) {

        int numberOfSnipers = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isSniper()) {

                numberOfSnipers++;
            }
        }

        return numberOfSnipers;
    }


    // a method that checks for the number of shield breakers
    private int checkNumberOfShieldBreakers(List<Hero>listOfSelectedHeroesAsObjects) {

        int numberOfShieldBreakers = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isShieldBreaker()) {

                numberOfShieldBreakers++;
            }
        }

        return numberOfShieldBreakers;
    }


    // a method that checks for the number of flank counters
    private int checkNumberOfFlankCounters(List<Hero> listOfSelectedHeroesAsObjects) {

        int numberOfFlankCounters = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isFlankCounter()) {

                numberOfFlankCounters++;
            }
        }

        return numberOfFlankCounters;
    }


    // a method that checks for the number of close-range dps
    private int checkNumberOfCloseRangeDps(List<Hero>listOfSelectedHeroesAsObjects) {

        int numberOfCloseRangeDps = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isCloseRange()) {

                numberOfCloseRangeDps++;
            }
        }

        return numberOfCloseRangeDps;
    }


    // a method that checks overall DPS power. This combines Tank and DPS power
    private int calculateOverallDpsPower(List<Hero>listOfSelectedHeroesAsObjects) {

        int overallDpsPower = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (!hero.isHealerRole()) {

                overallDpsPower +=  hero.getHeroPower();
            }
        }

        return overallDpsPower;
    }


    // a method that checks for number of shield tanks
    private int checkNumberOfShieldTanks(List<Hero>listOfSelectedHeroesAsObjects) {

        int numberOfShieldTanks = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isShieldTank()) {

                    numberOfShieldTanks++;
                }
            }

        return numberOfShieldTanks;
    }


    // a method that checks for overall healer power
    private int calculateOverallHealerPower(List<Hero>listOfSelectedHeroesAsObjects) {

        int overallHealerPower = 0;

        for (Hero hero : listOfSelectedHeroesAsObjects) {

            if (hero.isHealerRole()) {

                overallHealerPower += hero.getHeroPower();
            }
        }
        return overallHealerPower;
    }


    /* * * * *
     * ANALYSIS METHODS
     * These methods interpret the data retrieved from the Hero objects
     * and appends Strings of analysis to the "analysis" variable
     * * * * */

    private String analyzeTeamDpsComposition(String analysis, int numberOfSnipers, int numberOfSniperCounters,
                                             int numberOfPharahCounters, int numberOfShieldBreakers, int numberOfFlankCounters,
                                             int numberOfCloseRangeDps, int overallDpsPower) {

        if (numberOfSnipers > 1) {

            analysis += "\n-This team has more than one sniper. It may be difficult to deal " +
                        "with enemy shields and flankers.";
        }

        if (numberOfSniperCounters < 1) {

            analysis += "\n-This team might struggle to counter snipers.";
        }

        if (numberOfPharahCounters < 1 || numberOfCloseRangeDps > 1) {

            analysis += "\n-This team might struggle to counter Pharah.";
        }

        if (numberOfShieldBreakers < 1) {

            analysis += "\n-This team might struggle to break enemy shields.";
        }

        if (numberOfFlankCounters < 1) {

            analysis += "\n-This team might struggle to counter flanking DPS heroes like Tracer.";
        }

        if (overallDpsPower < 5) {

            analysis += "\n-This team has low overall DPS. It might struggle against an enemy team" +
                    " with strong healers.";
        }

        return analysis;
    }


    private String analyzeTeamTankComposition(String analysis, int numberOfShieldTanks) {

        if (numberOfShieldTanks < 1) {

            analysis += "\n-This team has no shield tank. It may be difficult " +
                        "to defend points or assault chokes.";
        }

        return analysis;
    }


    private String analyzeTeamHealerComposition(String analysis, int overallTeamHealerPower) {

        if (overallTeamHealerPower < 3) {

            analysis += "\n-This team has low overall healing power. It might struggle against an" +
                        " enemy team with a high-DPS composition.";
        }

        return analysis;
    }

}
