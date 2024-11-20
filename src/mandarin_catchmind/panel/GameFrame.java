package mandarin_catchmind.panel;

import java.awt.CardLayout;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private CardLayout cards = new CardLayout();
    private HomePanel homePanel;

    public GameFrame() {
        super("귤 캐치마인드!");
        setSize(1200, 800);
        this.setLayout(cards);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        homePanel = new HomePanel(this);
        this.add(homePanel, "Home");
        this.add(homePanel, "Home");
        cards.show(this.getContentPane(), "Home");
        setVisible(true);
    }
    
    public void nextPanel() {
		cards.next(this.getContentPane());
    }
}
