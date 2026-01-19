package Model;

/**
 * The Label enum defines the categories available for classifying {@link Event} objects.
 * These labels are used to group events logically and determine their visual
 * representation (such as background color) in the calendar views.
 */
public enum Label {
    /** Represents physical exercises or sporting events. */
    sport,

    /** Represents academic tasks, classes, or research activities. */
    study,

    /** Represents hobbies, social gatherings, or general leisure tasks. */
    free_time_activity,

    /** Represents trips, vacations, or transit-related events. */
    travel;
}