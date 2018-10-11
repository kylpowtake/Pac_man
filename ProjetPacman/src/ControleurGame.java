
public class ControleurGame implements InterfaceController {

	public Game game;
	private ViewCommande view;
	
	public ControleurGame(Game game){
		this.game = game;
		view = new ViewCommande(game,this);
	}
	
	@Override
	public void step() {
		System.out.println("Test step");
		this.game.step();
	}

	@Override
	public void pause() {
		this.game.pause();
	}

	@Override
	public void start() {
		this.game.run();
		
	}

	@Override
	public void restart() {
		this.game.init();
	}

}
