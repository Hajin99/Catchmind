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

import mandarin_catchmind.client.CatchmindClient;
import mandarin_catchmind.server.CatchmindServer;
import mandarin_catchmind.panel.RoomInfo;

public class HomePanel extends JPanel {
	private ImageIcon homeIcon = new ImageIcon(getClass().getResource("/images/시작화면2.png"));
	private Image homeImage = homeIcon.getImage();
	
	private ImageIcon startIcon = new ImageIcon(getClass().getResource("/images/StartIcon.png"));
	private ImageIcon startIcon2 = new ImageIcon(getClass().getResource("/images/StartIcon2.png"));
	
	private JButton startButton;
	private JTextField name;
	private JTextField ip;
	private JTextField port;
	
	public HomePanel(GameFrame frame) {
		this.setLayout(null);
        
		ImageGrowAnimation animation = new ImageGrowAnimation();
		animation.setBounds(10, 5, 300, 300); // 위치와 크기 설정
		animation.setBorder(null);
		animation.setOpaque(false);
        this.add(animation);
        
        ImageGrowAnimation2 animation2 = new ImageGrowAnimation2();
		animation2.setBounds(1005, 300, 50, 70); // 위치와 크기 설정
		animation2.setBorder(null);
		animation2.setOpaque(false);
        this.add(animation2);
        
        ImageGrowAnimation2 animation3 = new ImageGrowAnimation2();
        animation3.setBounds(1100, 290, 50, 70); // 위치와 크기 설정
        animation3.setBorder(null);
        animation3.setOpaque(false);
        this.add(animation3);
        
        ImageGrowAnimation2 animation4 = new ImageGrowAnimation2();
        animation4.setBounds(970, 400, 50, 70); // 위치와 크기 설정
        animation4.setBorder(null);
        animation4.setOpaque(false);
        this.add(animation4);
		
        name = new JTextField();
        name.setOpaque(false); // 배경 투명화
        name.setForeground(Color.BLACK); // 텍스트 색상 설정
        name.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        name.setBorder(null);
        name.setBounds(660, 240, 200, 50);
        add(name);
        
        ip = new JTextField();
        ip.setText("127.0.0.1");
        ip.setOpaque(false); // 배경 투명화
        ip.setForeground(Color.BLACK); // 텍스트 색상 설정
        ip.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        ip.setBorder(null);
        ip.setBounds(660, 350, 200, 50);
        add(ip);
        
        port = new JTextField();
        port.setText("5000");
        port.setOpaque(false); // 배경 투명화
        port.setForeground(Color.BLACK); // 텍스트 색상 설정
        port.setFont(new Font("맑은 고딕", Font.BOLD, 24));
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
            	String roomN = name.getText();
            	int portN = Integer.parseInt(port.getText());
<<<<<<< HEAD
            	int chN=1;
            	RoomInfo roomInfo = new RoomInfo(roomN, portN, 1, chN);
=======
            	RoomInfo roomInfo = new RoomInfo(roomN, portN, 1);
>>>>>>> c68beedd524537c128b3b8381aa165add7a514b9
            	MakeRoom.getRoomVector().add(roomInfo); // Vector에 추가
                MakeRoom.getRoomList().setListData(MakeRoom.getRoomVector()); // JList 업데이트
                name.setText(""); // 입력 필드 초기화
                port.setText("");
<<<<<<< HEAD
            	CatchmindClient client = new CatchmindClient(roomN, portN, chN);
=======
            	CatchmindClient client = new CatchmindClient(roomN, portN);
>>>>>>> c68beedd524537c128b3b8381aa165add7a514b9
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

class ImageGrowAnimation extends JPanel implements Runnable {
    private int imageWidth = 100; // 이미지 초기 너비
    private int imageHeight = 100; // 이미지 초기 높이
    private boolean running = true; // 애니메이션 실행 여부
    private ImageIcon orange = new ImageIcon(getClass().getResource("/images/orange2.png"));
	private Image orangeImage;
	
    public ImageGrowAnimation() {
    	orangeImage = orange.getImage();
        // 애니메이션 스레드 시작
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 이미지를 중앙에 그리기
        int x = (getWidth() - imageWidth) / 2;
        int y = (getHeight() - imageHeight) / 2;
        g.drawImage(orangeImage, x, y, imageWidth, imageHeight, this);
    }

    @Override
    public void run() {
        while (running) {
            try {
                // 이미지 크기 증가
                imageWidth += 2;
                imageHeight += 2;

                // 최대 크기 도달 시 멈춤
                if (imageWidth > 320 || imageHeight > 320) {
                    running = false;
                }

                // 다시 그리기 요청
                repaint();

                // 스레드 슬립 (애니메이션 속도 조절)
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ImageGrowAnimation2 extends JPanel implements Runnable {
    private int imageWidth = 50; // 이미지 초기 너비
    private int imageHeight = 70; // 이미지 초기 높이
    private boolean running = true; // 애니메이션 실행 여부
    private boolean isVisible = true; // 이미지 표시 여부
    private ImageIcon orange = new ImageIcon(getClass().getResource("/images/귤.png"));
	private Image orangeImage;
	
    public ImageGrowAnimation2() {
    	orangeImage = orange.getImage();
        // 애니메이션 스레드 시작
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isVisible) {
        // 이미지를 중앙에 그리기
        int x = (getWidth() - imageWidth) / 2;
        int y = (getHeight() - imageHeight) / 2;
        g.drawImage(orangeImage, x, y, imageWidth, imageHeight, this);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
            	
            	isVisible = !isVisible;

                // 다시 그리기 요청
                repaint();

                // 스레드 슬립 (애니메이션 속도 조절)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



