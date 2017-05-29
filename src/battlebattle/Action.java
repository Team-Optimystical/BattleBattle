package battlebattle;

import java.util.function.Consumer;

/**
 * The basic action class for the game of battle battle. Each action is created
 * with a function that takes a single {@link Game} as an argument. The function
 * is executed, followed by a call to {@link Game#postAction()} upon completion. </br>
 * </br>
 * The action is expected to change the state of the game, though it is not a
 * requirement.
 * @author Alex Sampley
 *
 */
public class Action {
	/**
	 * The function that is called when the action is executed.
	 */
	Consumer<Game> action;
	
	/**
	 * Create a new action with the function that takes a game as an argument,
	 * which will be called upon calling {@link #execute(Game)}
	 * @param action the function to be executed on a call to {@link #execute(Game)}
	 */
	public Action(Consumer<Game> action) {
		this.action = action;
	}
	
	/**
	 * Executes the action in the game. This will result in a call to the action's
	 * function, followed by {@link Game#postAction()}
	 * @param game
	 */
	public void execute(Game game) {
		action.accept(game);
		game.postAction();
	}
}
