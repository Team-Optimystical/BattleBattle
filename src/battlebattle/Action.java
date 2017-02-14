package battlebattle;

import java.util.function.Consumer;

public class Action {
	Consumer<Game> action;
	
	public Action(Consumer<Game> action) {
		this.action = action;
	}
}
