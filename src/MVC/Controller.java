package MVC;

import Analytics.Analyzer;
import Hero.Hero;
import Hero.HeroList;
import Hero.HeroLoadException;
import Hero.WeaponTypeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.util.*;


public class Controller {

    @FXML
    AnchorPane parentNode;
    @FXML
    TextField tankOneField;
    @FXML
    TextField tankTwoField;
    @FXML
    TextField dpsOneField;
    @FXML
    TextField dpsTwoField;
    @FXML
    TextField healerOneField;
    @FXML
    TextField healerTwoField;
    @FXML
    Button analyzeButton;
    @FXML
    Button resetButton;
    @FXML
    Button autoGenButton;
    @FXML
    Label analysisLabel;


    // A method that auto-generates a team and assign it to the textFields
    @FXML
    public void autoGenButtonPressed(ActionEvent event) throws HeroLoadException {

        // generate Lists of Hero Objects
        HeroList heroList = new HeroList();
        List<Hero> listOfTankHeroObjects = new ArrayList<>();
        List<Hero> listOfDpsHeroObjects = new ArrayList<>();
        List<Hero> listOfHealerHeroObjects = new ArrayList<>();


        try {
            listOfTankHeroObjects = heroList.generateListOfTankHeroObjects();
            listOfDpsHeroObjects = heroList.generateListOfDpsHeroObjects();
            listOfHealerHeroObjects = heroList.generateListOfHealerHeroObjects();

        } catch (HeroLoadException e) {

            System.out.println("A HeroLoadException or WeaponTypeException has occurred in HeroFileLoader.load()!");
            analysisLabel.setText("Sorry, something went wrong when generating the Hero lists.");
        }

        // convert the List<Hero>s into List<String>s
        List<String> listOfTankHeroStrings = heroList.generateListOfTankHeroStrings(listOfTankHeroObjects);
        List<String> listOfDpsHeroStrings = heroList.generateListOfDpsHeroStrings(listOfDpsHeroObjects);
        List<String> listOfHealerHeroStrings = heroList.generateListOfHealerHeroStrings(listOfHealerHeroObjects);


        // generate a team composition as strings and populate the corresponding text fields
        randomlySelectTankHeroes(listOfTankHeroStrings);
        randomlySelectDpsHeroes(listOfDpsHeroStrings);
        randomlySelectHealerHeroes(listOfHealerHeroStrings);

    }


    @FXML
    public void analyzeButtonPressed(ActionEvent event) {

        boolean allHeroesAccepted;
        String analysis;

        allHeroesAccepted = checkHeroSelectionsForProblems();

        if (!allHeroesAccepted) {

            analysisLabel.setText("Please review your selections and try again.");
            return;

        } else {

            List<String> listOfSelectedHeroes = fetchListOfSelectedHeroes();

            Analyzer Analyzer = new Analyzer(listOfSelectedHeroes);

            try {

                analysis = Analyzer.analyzeTeamComposition();

            } catch (HeroLoadException e) {

                analysis = "Sorry, something went wrong!";
                System.out.println("HeroLoadException: unable to load a Hero file!");

            } catch (WeaponTypeException e) {

                analysis = "Sorry, something went wrong!";
                System.out.println("WeaponTypeException: unable to identify a Hero's weapon!");
            }
        }

        analysisLabel.setText(analysis);
    }


    // A method that controls the Reset button
    @FXML
    public void resetButtonPressed(ActionEvent event) {

        tankOneField.setText("");
        tankTwoField.setText("");
        dpsOneField.setText("");
        dpsTwoField.setText("");
        healerOneField.setText("");
        healerTwoField.setText("");

        analysisLabel.setText("");
    }


    // A method that checks the user's input for issues before analysis
    private boolean checkHeroSelectionsForProblems() {

        boolean tankHeroesApproved;
        boolean dpsHeroesApproved;
        boolean healerHeroesApproved;
        boolean allHeroSelectionsApproved = false;

        // create a String array for each role and dynamically generate the role rosters
        HeroList heroList = new HeroList();
        List<Hero> listOfTankHeroObjects;
        List<Hero> listOfDpsHeroObjects;
        List<Hero> listOfHealerHeroObjects;


        try {

            listOfTankHeroObjects = heroList.generateListOfTankHeroObjects();
            listOfDpsHeroObjects = heroList.generateListOfDpsHeroObjects();
            listOfHealerHeroObjects = heroList.generateListOfHealerHeroObjects();

        } catch (HeroLoadException e) {

            System.out.println("A HeroLoadException or WeaponTypeException has occurred in HeroFileLoader.load()!");
            return allHeroSelectionsApproved;
        }


        // get the user-provided Strings from each field and convert to lowerCase
        String tankOneSelection = tankOneField.getText();
        String tankTwoSelection = tankTwoField.getText();
        String dpsOneSelection = dpsOneField.getText();
        String dpsTwoSelection = dpsTwoField.getText();
        String healerOneSelection = healerOneField.getText();
        String healerTwoSelection = healerTwoField.getText();

        String lowerCaseTankOneSelection = tankOneSelection.toLowerCase();
        String lowerCaseTankTwoSelection = tankTwoSelection.toLowerCase();
        String lowerCaseDpsOneSelection = dpsOneSelection.toLowerCase();
        String lowerCaseDpsTwoSelection = dpsTwoSelection.toLowerCase();
        String lowerCaseHealerOneSelection = healerOneSelection.toLowerCase();
        String lowerCaseHealerTwoSelection = healerTwoSelection.toLowerCase();


        // check Strings to ensure each is unique; if not analysis.setText("You have selected a Hero more than once);
        // this will require a Set
        Set<String> setOfUserSelections = new HashSet<>();

        setOfUserSelections.add(lowerCaseTankOneSelection);
        setOfUserSelections.add(lowerCaseTankTwoSelection);
        setOfUserSelections.add(lowerCaseDpsOneSelection);
        setOfUserSelections.add(lowerCaseDpsTwoSelection);
        setOfUserSelections.add(lowerCaseHealerOneSelection);
        setOfUserSelections.add(lowerCaseHealerTwoSelection);

        if (setOfUserSelections.size() < 6) {

            analysisLabel.setText("You have selected the same Hero more than once!");
            return allHeroSelectionsApproved;
        }

        tankHeroesApproved = checkUserTankSelections(lowerCaseTankOneSelection, lowerCaseTankTwoSelection,
                listOfTankHeroObjects);


        dpsHeroesApproved = checkUserDpsSelections(lowerCaseDpsOneSelection, lowerCaseDpsTwoSelection,
                listOfDpsHeroObjects);


        healerHeroesApproved = checkUserHealerSelections(lowerCaseHealerOneSelection, lowerCaseHealerTwoSelection,
                listOfHealerHeroObjects);


        return (tankHeroesApproved && dpsHeroesApproved && healerHeroesApproved);
    }


    // Checker methods for each role group
    private boolean checkUserTankSelections(String lowerCaseTankOneSelection, String lowerCaseTankTwoSelection,
                                            List<Hero> listOfTankHeroObjects) {

        boolean tankHeroesApproved = false;

        List<String> listOfUserSelectedTanks = new ArrayList<>();
        listOfUserSelectedTanks.add(lowerCaseTankOneSelection);
        listOfUserSelectedTanks.add(lowerCaseTankTwoSelection);

        HeroList heroList = new HeroList();
        List<String> listOfTankHeroStrings = heroList.generateListOfTankHeroStrings(listOfTankHeroObjects);

        for (String tankHero : listOfUserSelectedTanks) {

            if (!listOfTankHeroStrings.contains(tankHero)) {

                return tankHeroesApproved;
            }
        }

        tankHeroesApproved = true;
        return tankHeroesApproved;
    }


    private boolean checkUserDpsSelections(String lowerCaseDpsOneSelection, String lowerCaseDpsTwoSelection,
                                           List<Hero> listOfDpsHeroObjects) {

        boolean dpsHeroesApproved = false;

        List<String> listOfUserSelectedDps = new ArrayList<>();
        listOfUserSelectedDps.add(lowerCaseDpsOneSelection);
        listOfUserSelectedDps.add(lowerCaseDpsTwoSelection);

        HeroList heroList = new HeroList();
        List<String> listOfDpsHeroStrings = heroList.generateListOfDpsHeroStrings(listOfDpsHeroObjects);

        for (String dpsHero : listOfUserSelectedDps) {

            if (!listOfDpsHeroStrings.contains(dpsHero)) {

                return dpsHeroesApproved;
            }
        }

        dpsHeroesApproved = true;
        return dpsHeroesApproved;
    }


    private boolean checkUserHealerSelections(String lowerCaseHealerOneSelection, String lowerCaseHealerTwoSelection,
                                              List<Hero> listOfHealerHeroObjects) {

        boolean healerHeroesApproved = false;

        List<String> listOfUserSelectedHealers = new ArrayList<>();
        listOfUserSelectedHealers.add(lowerCaseHealerOneSelection);
        listOfUserSelectedHealers.add(lowerCaseHealerTwoSelection);

        HeroList heroList = new HeroList();
        List<String> listOfHealerHeroStrings = heroList.generateListOfHealerHeroStrings(listOfHealerHeroObjects);

        for (String healerHero : listOfUserSelectedHealers) {

            if (!listOfHealerHeroStrings.contains(healerHero)) {

                return healerHeroesApproved;
            }
        }

        healerHeroesApproved = true;
        return healerHeroesApproved;
    }


    // a method that grabs the names of the selected Heroes for passage to analysis methods
    private List<String> fetchListOfSelectedHeroes() {

        List<String> listOfUserSelectedHeroes = new ArrayList<>();

        listOfUserSelectedHeroes.add(tankOneField.getText());
        listOfUserSelectedHeroes.add(tankTwoField.getText());

        listOfUserSelectedHeroes.add(dpsOneField.getText());
        listOfUserSelectedHeroes.add(dpsTwoField.getText());

        listOfUserSelectedHeroes.add(healerOneField.getText());
        listOfUserSelectedHeroes.add(healerTwoField.getText());

        return listOfUserSelectedHeroes;
    }


    // a method that randomly generates two Tank Heroes and sets their textFields
    private void randomlySelectTankHeroes(List<String> listOfTankHeroStrings) {

        String tankOne = selectRandomTankHero(listOfTankHeroStrings);
        String tankTwo = selectRandomTankHero(listOfTankHeroStrings);

        // recurse if the selections are duplicates
        if (tankOne == tankTwo) {

            randomlySelectTankHeroes(listOfTankHeroStrings);

        } else {

            tankOneField.setText(tankOne);
            tankTwoField.setText(tankTwo);
        }
    }


    // a method that randomly generates two DPS Heroes and sets their textFields
    private void randomlySelectDpsHeroes(List<String> listOfDpsHeroStrings) {

        String dpsOne = selectRandomDpsHero(listOfDpsHeroStrings);
        String dpsTwo = selectRandomDpsHero(listOfDpsHeroStrings);

        // recurse if the selections are duplicates
        if (dpsOne == dpsTwo) {

            randomlySelectDpsHeroes(listOfDpsHeroStrings);

        } else {

            dpsOneField.setText(dpsOne);
            dpsTwoField.setText(dpsTwo);
        }
    }


    // a method that randomly generates two Healer Heroes and sets their textFields
    private void randomlySelectHealerHeroes(List<String> listOfHealerHeroStrings) {

        String healerOne = selectRandomHealerHero(listOfHealerHeroStrings);
        String healerTwo = selectRandomHealerHero(listOfHealerHeroStrings);

        // recurse if the selections are duplicates
        if (healerOne == healerTwo) {

            randomlySelectHealerHeroes(listOfHealerHeroStrings);

        } else {

            healerOneField.setText(healerOne);
            healerTwoField.setText(healerTwo);
        }
    }


    private String selectRandomTankHero(List<String> listOfTankHeroStrings) {

        Random randomizer = new Random();
        String randomTankString;

        randomTankString = listOfTankHeroStrings.get(randomizer.nextInt(listOfTankHeroStrings.size()));

        return randomTankString;
    }


    private String selectRandomDpsHero(List<String> listOfDpsHeroStrings) {

        Random randomizer = new Random();
        String randomDpsString;

        randomDpsString = listOfDpsHeroStrings.get(randomizer.nextInt(listOfDpsHeroStrings.size()));

        return randomDpsString;
    }


    private String selectRandomHealerHero(List<String> listOfHealerHeroStrings) {

        Random randomizer = new Random();
        String randomHealerString;

        randomHealerString = listOfHealerHeroStrings.get(randomizer.nextInt(listOfHealerHeroStrings.size()));

        return randomHealerString;
    }

}



