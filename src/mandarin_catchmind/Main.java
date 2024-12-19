package mandarin_catchmind;

import mandarin_catchmind.panel.GameFrame;
import mandarin_catchmind.panel.HomePanel;
import mandarin_catchmind.server.CatchmindServer;

public class Main {

	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		new CatchmindServer();
	}

}
