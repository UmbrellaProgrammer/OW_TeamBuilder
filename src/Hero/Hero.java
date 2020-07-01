package Hero;

import java.util.ArrayList;
import java.util.List;

public class Hero {

    // these are the data that are necessary to construct a Hero object
    // the Analyzer methods will work on this data
    private final String heroName;
    private HeroRole heroRole;
    private List<WeaponType> weaponType;
    private List<RoleSpecialty> roleSpecialtyList;
    private int heroPower;


    // constructor
    Hero(String heroName, HeroRole heroRole, List<WeaponType> weaponType,
                List<RoleSpecialty> roleSpecialtyList, int power) {

        this.heroName = heroName;
        this.heroRole = heroRole;
        this.roleSpecialtyList = roleSpecialtyList;
        this.weaponType = weaponType;
        this.heroPower = power;
    }

    public String getHeroName() {

        return heroName;
    }

    public boolean isSniper() {

        return roleSpecialtyList.contains(RoleSpecialty.SNIPER);
    }


    public boolean isPharahCounter() {

        return roleSpecialtyList.contains(RoleSpecialty.COUNTERPHARAH);
    }


    public boolean isSniperCounter() {

        return roleSpecialtyList.contains(RoleSpecialty.COUNTERSNIPER);
    }


    public boolean isShieldBreaker() {

        return roleSpecialtyList.contains(RoleSpecialty.SHIELDBREAKER);
    }


    public boolean isFlankCounter() {

        return roleSpecialtyList.contains(RoleSpecialty.COUNTERFLANK);
    }


    public boolean isShieldTank() {

        return weaponType.contains(WeaponType.SHIELD);
    }


    public boolean isCloseRange() {

        return weaponType.contains(WeaponType.CLOSERANGE);
    }


    // these might not be used?
    public boolean isTankRole() {

        return heroRole.equals(HeroRole.TANK);
    }


    public boolean isDpsRole() {

        return  heroRole.equals(HeroRole.DPS);
    }


    public boolean isHealerRole() {

        return heroRole.equals(HeroRole.HEALER);
    }


    // this will have to be used carefully to correctly calculate healer/dps/tank power individually
    public int getHeroPower() {

        return heroPower;
    }


}