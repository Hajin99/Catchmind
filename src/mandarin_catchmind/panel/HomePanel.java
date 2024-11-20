package mandarin_catchmind.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomePanel extends JPanel {
	private ImageIcon homeIcon = new ImageIcon(getClass().getResource("/images/시작화면.png"));
	private Image homeImage = homeIcon.getImage();
	
	private ImageIcon startIcon = new ImageIcon(getClass().getResource("/images/StartIcon.png"));
	private ImageIcon startIcon2 = new ImageIcon(getClass().getResource("/images/StartIcon2.png"));
	
	private JButton startButton;
	private JTextField name;
	private JTextField ip;
	private JTextField port;
	
	public HomePanel(GameFrame frame) {
		this.setLayout(null);
        
        name = new JTextField();
        name.setOpaque(false); // 배경 투명화
        name.setForeground(Color.BLACK); // 텍스트 색상 설정
        name.setFont(new Font("Arial", Font.BOLD, 24));
        name.setBorder(null);
        name.setBounds(660, 240, 200, 50);
        add(name);
        
        ip = new JTextField();
        ip.setOpaque(false); // 배경 투명화
        ip.setForeground(Color.BLACK); // 텍스트 색상 설정
        ip.setFont(new Font("Arial", Font.BOLD, 24));
        ip.setBorder(null);
        ip.setBounds(660, 350, 200, 50);
        add(ip);
        
        port = new JTextField();
        port.setOpaque(false); // 배경 투명화
        port.setForeground(Color.BLACK); // 텍스트 색상 설정
        port.setFont(new Font("Arial", Font.BOLD, 24));
        port.setBorder(null);
        port.setBounds(660, 460, 200, 50);
        add(port);
        
        startButton = new JButton(startIcon);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setBounds(440,600,350,100);
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	startButton.setRolloverIcon(startIcon2);
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.nextPanel();
            }
        });
        add(startButton);
        
		setVisible(true);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
        g.drawImage(homeImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
    }
	
	
}
