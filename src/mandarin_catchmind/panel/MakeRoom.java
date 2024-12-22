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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mandarin_catchmind.client.CatchmindClient;
import mandarin_catchmind.panel.RoomInfo;

public class MakeRoom extends JPanel {
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	//private List <String> roomNameList = new ArrayList<>();
	private static Vector<RoomInfo> v = new Vector<RoomInfo>();
	private static JList<RoomInfo> roomList = new JList<>(v);
	private ImageIcon homeIcon = new ImageIcon(getClass().getResource("/images/연한오렌지.png"));
	private Image homeImage = homeIcon.getImage();
	private ImageIcon bage = new ImageIcon(getClass().getResource("/images/연한베이지.png"));
	private Image bageImage = bage.getImage();
	int number=2;
<<<<<<< HEAD
	int chN=1;
=======
>>>>>>> c68beedd524537c128b3b8381aa165add7a514b9
	public MakeRoom(GameFrame frame) {
		this.setLayout(new GridLayout(1,2));
		add(new RoomPanel());
        add(new EastPanel());
        
		setVisible(true);
	}
	
	public static Vector<RoomInfo> getRoomVector() {
        return v;
    }

    public static JList<RoomInfo> getRoomList() {
        return roomList;
    }
	
	
	//방 리스트를 보여주는 판넬
	class RoomPanel extends JPanel {
		
		public RoomPanel(){
			this.setLayout(new GridLayout(2,1));
			
			roomList.setVisibleRowCount(1);
			roomList.setFixedCellWidth(100);
			roomList.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			roomList.setBackground(Color.ORANGE);
			roomList.setBorder(null);
			
			roomList.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 2) { // 더블 클릭
	                    int index = roomList.locationToIndex(e.getPoint()); // 클릭한 항목의 인덱스
	                    if (index != -1) { // 유효한 항목 클릭
	                        RoomInfo selectedRoom = roomList.getModel().getElementAt(index); // 선택된 방 이름
	                        int port = selectedRoom.getPort(); // 해당 방의 포트 번호
	                        String roomName = selectedRoom.getRoomName();
	                        
	                        // 다이얼로그 표시
	                        int result = JOptionPane.showConfirmDialog(null,
	                                "입장하시겠습니까?", " 방 선택",
	                                JOptionPane.YES_NO_OPTION);

	                        if (result == JOptionPane.YES_OPTION) { // "확인"을 클릭한 경우
	                            // 포트 번호로 소켓 생성
	                            CatchmindClient client = new CatchmindClient(roomName, port, chN);
	                            System.out.println("소켓 생성 성공: " + selectedRoom + "에 연결되었습니다. (포트: " + port + ")");
	                        }
	                    }
	                }
	            }  
	        });
			
			JScrollPane scrollPane = new JScrollPane(roomList);
			add(scrollPane);
			add(new MandarinPanel());
			
			setVisible(true);
		}
		
		@Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
	        g.drawImage(bageImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
	    }
	} //RoomPanel끝
	
	class EastPanel extends JPanel{
		public EastPanel() {
			this.setLayout(new GridLayout(2,1));
			add(new PortPanel());
			add(new MandarinPanel2());
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
				this.setLayout(new GridLayout(5,1));
				
				nameLabel = new JLabel(textRoom);
				nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
				add(nameLabel);
				
				nameField = new JTextField();
		        //port.setOpaque(false); // 배경 투명화
				nameField.setForeground(Color.BLACK); // 텍스트 색상 설정
				nameField.setBackground(Color.ORANGE);
				nameField.setHorizontalAlignment(JTextField.CENTER);
				nameField.setFont(new Font("맑은 고딕", Font.BOLD, 24));
				nameField.setBorder(null);
				nameField.setBounds(850, 200, 200, 50);
		        add(nameField);
		        
		        //JPanel portPanel = new JPanel(); // 기본 FlowLayout 사용
				JLabel pt = new JLabel("포트 번호 입력 ↓ ");
				pt.setHorizontalAlignment(SwingConstants.CENTER);
				JTextField portNumber = new JTextField(10); // 너비 10 칸
				portNumber.setBackground(Color.ORANGE);
				portNumber.setHorizontalAlignment(JTextField.CENTER);
				portNumber.setFont(new Font("맑은 고딕", Font.BOLD, 24));
				portNumber.setBorder(null);
				//portPanel.add(portNumber);
				add(pt);
				add(portNumber);
		        
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
		            	String roomName = nameField.getText(); //방이름
		            	int portNumberN = Integer.parseInt(portNumber.getText());
		            	RoomInfo roomInfo = new RoomInfo(roomName, portNumberN, number, chN);
		            	number++;
		            	v.add(roomInfo);
		            	roomList.setListData(v);
                        nameField.setText(""); // 입력 필드 초기화
                        portNumber.setText("");
                        //MyGameScreen myGameScreen = new MyGameScreen(roomName);
                        //myGameScreen.setVisible(true);
                        CatchmindClient client = new CatchmindClient(roomName, portNumberN, chN);
                        
                        client.setVisible(true);
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
				private ImageIcon orange2 = new ImageIcon(getClass().getResource("/images/1.png"));
				private JButton ch = new JButton(orange2);
				//private JLabel orangeLabel;
				
				public MandarinPanel() {
					//ch.setFocusPainted(false); 
					ch.setBorderPainted(false);
					ch.setContentAreaFilled(false);
					ch.addMouseListener(new MouseAdapter() {
			            @Override
			            public void mouseClicked(MouseEvent e) {
			                if (e.getClickCount() == 2) { // 더블 클릭
			                    int index = roomList.locationToIndex(e.getPoint()); // 클릭한 항목의 인덱스
			                    if (index != -1) { // 유효한 항목 클릭
			                        //RoomInfo selectedRoom = roomList.getModel().getElementAt(index); // 선택된 방 이름
			                        //int port = selectedRoom.getPort(); // 해당 방의 포트 번호
			                        //String roomName = selectedRoom.getRoomName();
			                        
			                        // 다이얼로그 표시
			                        int result = JOptionPane.showConfirmDialog(null,
			                                "캐릭터를 선택하시겠습니까?",  " 캐릭터 선택",
			                                JOptionPane.YES_NO_OPTION);

			                        if (result == JOptionPane.YES_OPTION) { // "확인"을 클릭한 경우
			                            chN=1;//캐릭터 선택
			                        }
			                    }
			                }
			            }  
			        });
					add(ch);
				}
				
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
			        g.drawImage(homeImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
			    }
			}//MandarinPanel끝
			
			class MandarinPanel2 extends JPanel {
				private ImageIcon orange3 = new ImageIcon(getClass().getResource("/images/2.png"));
				private JButton ch = new JButton(orange3);
				//private JLabel orangeLabel;
				
				public MandarinPanel2() {
					ch.setBorderPainted(false);
					ch.setContentAreaFilled(false);
					ch.addMouseListener(new MouseAdapter() {
			            @Override
			            public void mouseClicked(MouseEvent e) {
			                if (e.getClickCount() == 2) { // 더블 클릭
			                    int index = roomList.locationToIndex(e.getPoint()); // 클릭한 항목의 인덱스
			                    if (index != -1) { // 유효한 항목 클릭
			                        //RoomInfo selectedRoom = roomList.getModel().getElementAt(index); // 선택된 방 이름
			                        //int port = selectedRoom.getPort(); // 해당 방의 포트 번호
			                        //String roomName = selectedRoom.getRoomName();
			                        
			                        // 다이얼로그 표시
			                        int result = JOptionPane.showConfirmDialog(null,
			                                "캐릭터를 선택하시겠습니까?",  " 캐릭터 선택",
			                                JOptionPane.YES_NO_OPTION);

			                        if (result == JOptionPane.YES_OPTION) { // "확인"을 클릭한 경우
			                        	chN=2;//캐릭터 선택
			                        	//System.out.println("캐선"+chN);
			                        }
			                    }
			                }
			            }  
			        });
					add(ch);
				}
				
				@Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        // 패널 크기에 맞춰 이미지를 배경으로 그립니다.
			        g.drawImage(homeImage, 0, 0, getWidth(), getHeight(), null); // 배경에 꽉차게
			    }
			}//MandarinPanel끝
	
}
