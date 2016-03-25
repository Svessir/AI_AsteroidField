package robot;

/**
 * TransitionModel - Includes information of how the rocket moves.
 * SINGLE_ROTATION - Defines how many radians the rocket rotates for each rotation action
 * FUEL_PER_THRUST - Defines how much fuel is consumed for each thrust action
 * NUMBER_OF_ACTIONS_PER_TRANSITION - Defines how many actions will be applied in between states
 */
public class TransitionModel {
	public final double SINGLE_ROTATION;
	public final double FUEL_PER_THRUST;
	public final long NUMBER_OF_ACTIONS_PER_TRANSITION;
	
	public TransitionModel(double singleRotation, double fuelPerThrust, long numberOfActionsPerTransition) {
		SINGLE_ROTATION = numberOfActionsPerTransition * singleRotation;
		FUEL_PER_THRUST = numberOfActionsPerTransition * fuelPerThrust;
		NUMBER_OF_ACTIONS_PER_TRANSITION = numberOfActionsPerTransition;
	}
}
