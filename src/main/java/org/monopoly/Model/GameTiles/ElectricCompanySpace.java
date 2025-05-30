package org.monopoly.Model.GameTiles;

import org.monopoly.Exceptions.InsufficientFundsException;
import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Dice;
import org.monopoly.Model.Players.Player;

import java.util.ArrayList;

/**
 * Represents the Electric Company Space element on the Game Board's Tiles.
 *
 * @author shifmans
 */
public class ElectricCompanySpace extends GameTile {
    private int price;
    private ArrayList<Integer> rentPriceMultiplier;
    private ColorGroup colorGroup;
    private int mortgageValue;
    private int unmortgageValue;
    private boolean isMortgaged;
    private String owner;

    /**
     * Constructor to initialize a ElectricCompanySpace with all information.
     * @param actions Actions for a ElectricCompanySpace.
     * @param price Price for a ElectricCompanySpace.
     * @param rentPriceMultiplier Rent Price Multiplier for a ElectricCompanySpace.
     * @param colorGroup Color Group for a ElectricCompanySpace.
     * @param mortgageValue Mortgage Value for a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public ElectricCompanySpace(String actions, int price, ArrayList<Integer> rentPriceMultiplier, ColorGroup colorGroup, int mortgageValue) {
        super("Electric Company", actions);
        this.price = price;
        this.rentPriceMultiplier = rentPriceMultiplier;
        this.colorGroup = colorGroup;
        this.mortgageValue = mortgageValue;
        this.unmortgageValue = mortgageValue + (int) (mortgageValue * 0.1); //Mortgage value plus 10% interest
        this.isMortgaged = false;
        this.owner = "";

    }

    /**
     * Shows the actions that occur after a player lands on element space.
     * @return Displays the actions for landing on element space.
     *
     * Developed by: shifmans
     */
    @Override
    public String landOn() {
        actions += displayElectricCompanyInfo();
        return actions;
    }

    /**
     * Player is shown information about Electric Company after landing on element space.
     * @return Information on Electric Company Space.
     *
     * Developed by: shifmans
     */
    private String displayElectricCompanyInfo() {
        return "Property Name: " + getName() + "\n" +
                "Color Set: " + getColorGroup() + "\n" +
                "Purchase Price: $" + getPrice() + "\n" +
                "Rent (without houses/hotels): Depends on dice roll\n" +
                "If you own 1 Utility: Rent is 4 times the amount rolled on the dice.\n" +
                "If you own 2 Utilities: Rent is 10 times the amount rolled on the dice.\n" +
                "Mortgage Value: $" + getMortgageValue();
    }

    /**
     * Gets the price of a ElectricCompanySpace.
     * @return The price of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public int getPrice() {
        return price;
    }

    /**
     * Calculates the rent price based on the number of utilities owned and the dice roll.
     * @param numUtilitiesOwned The number of utility properties owned by the player.
     * @return The rent price based on the number of utilities owned and the dice roll.
     *
     * Developed by: shifmans
     */
    public int getRentPrice(int numUtilitiesOwned) {
        Dice dice = new Dice();
        int[] diceRoll = dice.roll();


        if (numUtilitiesOwned == 1) { // If the player owns 1 utility
            return (diceRoll[0] + diceRoll[1]) * getRentPriceMultiplier().get(0);
        }


        return (diceRoll[0] + diceRoll[1]) * getRentPriceMultiplier().get(1);
    }

    /**
     * Gets the rent prices of a ElectricCompanySpace.
     * @return The list of rent prices of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    private ArrayList<Integer> getRentPriceMultiplier() {
        return rentPriceMultiplier;
    }

    /**
     * Gets the color group of a ElectricCompanySpace.
     * @return The color group of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public ColorGroup getColorGroup() {
        return colorGroup;
    }

    /**
     * Gets the mortgage value of a ElectricCompanySpace.
     * @return The mortgage value of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public int getMortgageValue() {
        return mortgageValue;
    }

    /**
     * Gets the unmortgage value of a ElectricCompanySpace.
     * @return The unmortgage value of a ElectricCompanySpace.
     */
    public int getUnmortgageValue() {
        return unmortgageValue;
    }

    /**
     * Gets the mortgaged status of a ElectricCompanySpace.
     * @return The mortgaged status of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public boolean isMortgaged() {
        return isMortgaged;
    }

    /**
     * Sets the mortgaged status of a ElectricCompanySpace.
     * @param isMortgaged The mortgaged status of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public void setMortgagedStatus(boolean isMortgaged) {
        this.isMortgaged = isMortgaged;
    }

    /**
     * Gets the owner of a ElectricCompanySpace.
     * @return The owner of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of a ElectricCompanySpace.
     * @param owner The owner of a ElectricCompanySpace.
     *
     * Developed by: shifmans
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Executes the strategy for the ElectricCompanySpace.
     * @author crevelings
     */
    @Override
    public void executeStrategy(Player player) {
        if (player.hasProperty(getName())) {
            System.out.println("You already own the " + getName() + "!");
        } else {
            if (getOwner() == null || getOwner().isEmpty()) { // Proper null check
                System.out.println("You can buy the " + getName() + " for $" + price);
                System.out.println("Or property can be auctioned");
            } else {
                System.out.println(getOwner() + " already owns the " + getName() + "!");
            }
            try {
                player.purchaseProperty(getName(), price);
                System.out.println("Player bought " + getName());
            } catch (InsufficientFundsException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
