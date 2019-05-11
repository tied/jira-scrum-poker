package de.codescape.jira.plugins.scrumpoker.model;

/**
 * Global Settings for Scrum Poker to be used across all Jira projects.
 */
public class GlobalSettings {

    // defaults

    public static final Integer SESSION_TIMEOUT_DEFAULT = 12;
    public static final AllowRevealDeck ALLOW_REVEAL_DECK_DEFAULT = AllowRevealDeck.EVERYONE;
    public static final boolean DEFAULT_PROJECT_ACTIVATION_DEFAULT = true;
    public static final boolean DISPLAY_DROPDOWN_ON_BOARDS_DEFAULT = false;

    // settings

    private String storyPointField;
    private Integer sessionTimeout = SESSION_TIMEOUT_DEFAULT;
    private AllowRevealDeck allowRevealDeck = ALLOW_REVEAL_DECK_DEFAULT;
    private boolean defaultProjectActivation = DEFAULT_PROJECT_ACTIVATION_DEFAULT;
    private boolean displayDropdownOnBoards = DISPLAY_DROPDOWN_ON_BOARDS_DEFAULT;

    /**
     * Set the story point field.
     */
    public void setStoryPointField(String storyPointField) {
        this.storyPointField = storyPointField;
    }

    /**
     * Return the story point field.
     */
    public String getStoryPointField() {
        return storyPointField;
    }

    /**
     * Set the session timeout.
     */
    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    /**
     * Return the session timeout.
     */
    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * Set who is allowed to reveal the deck.
     */
    public void setAllowRevealDeck(AllowRevealDeck allowRevealDeck) {
        this.allowRevealDeck = allowRevealDeck;
    }

    /**
     * Return who is allowed to reveal the deck.
     */
    public AllowRevealDeck getAllowRevealDeck() {
        return allowRevealDeck;
    }

    /**
     * Set whether Scrum Poker is globally enabled.
     */
    public void setDefaultProjectActivation(boolean defaultProjectActivation) {
        this.defaultProjectActivation = defaultProjectActivation;
    }

    /**
     * Return whether Scrum Poker is globally enabled.
     */
    public boolean isDefaultProjectActivation() {
        return defaultProjectActivation;
    }

    /**
     * Set whether the dropdown on boards shall be displayed.
     */
    public void setDisplayDropdownOnBoards(boolean displayDropdownOnBoards) {
        this.displayDropdownOnBoards = displayDropdownOnBoards;
    }

    /**
     * Return whether the dropdown on boards shall be displayed.
     */
    public boolean isDisplayDropdownOnBoards() {
        return displayDropdownOnBoards;
    }

}
