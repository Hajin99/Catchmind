package mandarin_catchmind.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MakeRoom extends JPanel {
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	//private List <String> roomNameList = new ArrayList<>();
	private Vector<String> v = new Vector<String>();
	JList<String> roomList = new JList<>(v);
	
	private ImageIcon homeIcon = new ImageIcon(getClass().getResource("/images/연한오렌지.png"));
	private Image homeImage = homeIcon.getImage();
	private ImageIcon bage = new ImageIcon(getClass().getResource("/images/연한베이지.png"));
	private Image bageImage = bage.getImage();
	
	public MakeRoom(GameFrame frame) {
		this.setLayout(new GridLayout(1,2));
		add(new RoomPanel());
        add(new EastPanel());
        
		setVisible(true);
	}
	
	//방 리스트를 보여주는 판넬
	class RoomPanel extends JPanel {
		
		public RoomPanel(){
			this.setLayout(new GridLayout(2,1));
			
			roomList.setVisibleRowCount(1);
			roomList.setFixedCellWidth(100);
			JScrollPane scrollPane = new JScrollPane(roomList);
			add(scrollPane);
			add(new MandarinPanel());
			setVisible(true);
		}
		
		@Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
	        g.drawImage(homeImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
	    }
	} //RoomPanel끝
	
	class EastPanel extends JPanel{

		
		public EastPanel() {
			this.setLayout(new GridLayout(2,1));
			add(new PortPanel());
			add(new MandarinPanel());
			setVisible(true);
		}
		
		//포트번호 입력, 방만들기
		class PortPanel extends JPanel {
			private ImageIcon textRoom = new ImageIcon(getClass().getResource("/images/textRoom.png"));
			
			private ImageIcon makeRoom = new ImageIcon(getClass().getResource("/images/makeRoom.png"));
			private ImageIcon makeRoom2 = new ImageIcon(getClass().getResource("/images/makeRoom2.png"));
			
			private JButton makeButton;
			private JTextField nameField;
			private JLabel nameLabel;
			
			public PortPanel(){
				this.setLayout(new GridLayout(3,1));
				
				nameLabel = new JLabel(textRoom);
				nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
				add(nameLabel);
				
				nameField = new JTextField();
		        //port.setOpaque(false); // 배경 투명화
				nameField.setForeground(Color.BLACK); // 텍스트 색상 설정
				nameField.setFont(new Font("Arial", Font.BOLD, 24));
				nameField.setBorder(null);
				nameField.setBounds(850, 200, 200, 50);
		        add(nameField);
		        
		        makeButton = new JButton(makeRoom);
		        makeButton.setFocusPainted(false); 
		        makeButton.setBorderPainted(false);
		        makeButton.setContentAreaFilled(false);
		        //makeButton.setBounds(440,600,50,50);
		        makeButton.addMouseListener(new MouseAdapter() {
		            public void mouseEntered(MouseEvent e) {
		            	makeButton.setRolloverIcon(makeRoom2);
		            }
		        });
		        makeButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	String roomName = nameField.getText();
		            	v.add(roomName);
		            	roomList.setListData(v);
                        nameField.setText(""); // 입력 필드 초기화
                        //GameScreen gameScreen = new GameScreen(roomName);
                        //gameScreen.setVisible(true);
                        
		            }
		        });
		        add(makeButton);
			}
			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
		        g.drawImage(bageImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
		    }
		}
		//PortPanel끝
		
		
		
	}
	//EastPanel끝

	//MandarinPanelt시작
			class MandarinPanel extends JPanel {
				private ImageIcon orange = new ImageIcon(getClass().getResource("/images/orange.png"));
				
				private JLabel orangeLabel;
				
				public MandarinPanel() {
					orangeLabel= new JLabel(orange);
					add(orangeLabel);
				}
				
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
			        g.drawImage(homeImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
			    }
			}//MandarinPanel끝
	
}
