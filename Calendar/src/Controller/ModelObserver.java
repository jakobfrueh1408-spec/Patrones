package Controller;

/**
 * ModelObserver defines the contract for any object that needs to be notified
 * of changes within the {@link Model}.
 * <p>
 * This interface is a key component of the Observer Design Pattern. It decouples
 * the Model from the View, allowing multiple views or components to stay
 * synchronized with the underlying data without the Model needing to know
 * the specific details of the View implementation.
 * </p>
 */
public interface ModelObserver {

    /**
     * Invoked when the state or data of the observed model undergoes a change.
     * <p>
     * Implementation classes (typically UI panels or frames) should use this
     * method to trigger a visual refresh, such as repainting components,
     * updating labels, or switching screens via {@code CardLayout}.
     * </p>
     */
    void modelChanged();
}