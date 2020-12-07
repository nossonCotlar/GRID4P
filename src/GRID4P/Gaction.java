package grid4p;

/**
 * An interface for user defined actions to be performed upon the occurrence of certain midi events.
 * All of the methods are default, so any or none of
 * them may be implemented or left out without consequence.
 */
public interface Gaction {
    /**
     * method to be called upon the "pushing down" of a GRID controller element
     */
    default void onpush() {
        Grid.verboseWarning("The user hasn't implemented this method");
    }
    /**
     * method to be called upon the "release" of a GRID controller element
     */
    default void onrelease(){
        Grid.verboseWarning("The user hasn't implemented this method");
    }
    /**
     * method to be called upon any change to the value of a GRID controller element
     */
    default void ontweak(){
        Grid.verboseWarning("The user hasn't implemented this method");
    }
    /**
     * method to be called upon the value of a GRID controller element reaching its maximum or minimum
     */
    default void onlimitreached(){
        Grid.verboseWarning("The user hasn't implemented this method");
    }
}
