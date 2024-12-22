package mandarin_catchmind.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import mandarin_catchmind.client.Music;

public abstract class GameScreen extends JFrame implements ActionListener, MouseListener, MouseMotionListener, WindowListener, KeyListener {

	private Graphics g;
	protected Graphics2D graphic;

	protected JPanel paintPanel;
	protected JPanel infoPanel;

	protected JButton bigPencil;
	protected JButton mediumPencil;
	protected JButton smallPencil;

	protected JButton bigEraser;
	protected JButton mediumEraser;
	protected JButton smallEraser;

	protected JButton clearEraser;

	protected JButton redPen;
	protected JButton orangePen;
	protected JButton yellowPen;
	protected JButton greenPen;
	protected JButton bluePen;
	protected JButton blackPen;

	protected int thickness = 5;
	protected int startX;
	protected int startY;
	protected int endX;
	protected int endY;
	protected int chN; // 캐릭터 선택 번호(1, 2);
	
	protected Color currentColor = Color.BLACK;
	protected Color currentColorMemory;

	protected JLabel[] nameLabelArr = new JLabel[4];
	protected JLabel[] scoreLabelArr = new JLabel[4];
	protected JLabel[] imgLabelArr = new JLabel[4]; //캐릭터 보여줄 라벨
	protected JTextArea[] messageTaArr = new JTextArea[4];
	
	protected JLabel turnLabel;
	protected JLabel topLabel;
	public JLabel timerLabel;

	protected JTextField messageTf = new JTextField();
	
	protected JTextArea newChatArea = new JTextArea();
    protected JTextField newMessageTf = new JTextField();
    
    private ImageIcon newSendBtnIcon = new ImageIcon(getClass().getResource("/images/button.png"));
    JButton newSendBtn = new JButton("보내기", newSendBtnIcon);
    
	private ImageIcon playerBackgroundIcon = new ImageIcon(getClass().getResource("/images/playerbackground.png"));
	private ImageIcon chatBackgroundIcon = new ImageIcon(getClass().getResource("/images/chatbackground.png"));
	private Font bigFont = new Font("1훈솜사탕 Regular", Font.PLAIN, 24);
	private Font smallFont = new Font("1훈솜사탕 Regular", Font.PLAIN, 16);
	private ImageIcon ch = new ImageIcon(getClass().getResource("/images/button.png"));
	private ImageIcon ch1 = new ImageIcon(getClass().getResource("/images/1_2.png"));
	private ImageIcon ch2 = new ImageIcon(getClass().getResource("/images/2_2.png"));
	private ImageIcon onA = new ImageIcon(getClass().getResource("/images/on.png"));
	private ImageIcon offA = new ImageIcon(getClass().getResource("/images/off.png"));
	private Music m = new Music();
	private int onOff = 0;
	
	//채팅창 패널
    public JPanel getNewChatPanel() {
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(chatBackgroundIcon.getImage(), 10, 50, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };

        panel.setLayout(null);

        newChatArea.setEditable(false);
        newChatArea.setLineWrap(true);
        JScrollPane newScrollPane = new JScrollPane(newChatArea);
        newScrollPane.setBounds(30, 70, 260, 530);
        newScrollPane.setBorder(null);
        
        ImageIcon textFieldIcon = new ImageIcon(getClass().getResource("/images/textbackground.png"));
        newMessageTf = new JTextField() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            g.drawImage(textFieldIcon.getImage(), -20, 0, 250, getHeight(), null);
	            super.paintComponent(g);
	        }
	    };
	    
	    JButton bgmButton = new JButton(onA);
	    bgmButton.setBounds(200, 5, 50, 50);
	    bgmButton.setBorderPainted(false);
	    bgmButton.setContentAreaFilled(false);
	    bgmButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	if(onOff==0) {
            		bgmButton.setRolloverIcon(onA);
				}
				else {
					bgmButton.setRolloverIcon(offA);
				}
            	
            }
        });
	    
	    bgmButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(onOff==0) {
					m.mStart();
					bgmButton.setIcon(onA);
					onOff=1;
				}
				else {
					m.mStop();
					bgmButton.setIcon(offA);
					onOff=0;
				}
            	 
//            	 try {
//					Thread.sleep(m.getClip().getMicrosecondLength()/1000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
            }  
        });
	    newMessageTf.setBorder(null);
	    newMessageTf.setOpaque(false); 
	    newMessageTf.addKeyListener(this);
        newMessageTf.setBounds(30, 650, 180, 30);
        
        newSendBtn.setHorizontalTextPosition(SwingConstants.CENTER); 
        newSendBtn.setVerticalTextPosition(SwingConstants.BOTTOM); 
        newSendBtn.setOpaque(false); 
        newSendBtn.setContentAreaFilled(false); 
        newSendBtn.setBorderPainted(false);
        newSendBtn.setFocusPainted(false);

        newSendBtn.setBounds(210, 645, 80, 60);
        newSendBtn.addActionListener(this);

        panel.add(bgmButton);
        panel.add(newScrollPane);
        panel.add(newMessageTf);
        panel.add(newSendBtn);

        return panel;
    }

    //플레이어 닉네임, 점수, 입력한 답 반환
	public JPanel getChatPanel(int idx) {

		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(playerBackgroundIcon.getImage(), 10, 10, 280, 160, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		JPanel taPanel = new JPanel();

		panel.setLayout(null);

		nameLabelArr[idx] = new JLabel("Player" + (idx + 1));
		nameLabelArr[idx].setFont(smallFont);
		nameLabelArr[idx].setBounds(200, 80, 100, 30);

		scoreLabelArr[idx] = new JLabel("점수 : 0");
		scoreLabelArr[idx].setFont(smallFont);
		scoreLabelArr[idx].setBounds(200, 110, 100, 20);
		

	    imgLabelArr[idx] = new JLabel(ch);
	    imgLabelArr[idx].setIcon(ch);
		imgLabelArr[idx].setBounds(50,70,80,90);
		
		taPanel.setOpaque(false);
		taPanel.setBounds(130, 50, 150, 100);

		messageTaArr[idx] = new JTextArea(5, 10);
		messageTaArr[idx].setEditable(false);
		messageTaArr[idx].setLineWrap(true);
		messageTaArr[idx].setFont(smallFont);
		messageTaArr[idx].setOpaque(false);
		
		panel.add(nameLabelArr[idx]);
		panel.add(scoreLabelArr[idx]);
		panel.add(imgLabelArr[idx]);
		taPanel.add(messageTaArr[idx]);
		panel.add(taPanel);

		return panel;
	}

	//턴, 제한시간 상태 표시 패널
	public JPanel getStatusBarPanel() {
	    JPanel panel = new JPanel();
	    panel.setOpaque(false);
	    panel.setLayout(null);

	    ImageIcon turnBackgroundIcon = new ImageIcon(getClass().getResource("/images/orangeimg.png"));
	    turnLabel = new JLabel("-/10 턴") {
	        @Override
	        protected void paintComponent(Graphics g) {
	            g.drawImage(turnBackgroundIcon.getImage(), 10, 0, 80, 60, null);
	            super.paintComponent(g);
	        }
	    };
	    turnLabel.setFont(smallFont);
	    turnLabel.setHorizontalAlignment(JLabel.CENTER);
	    turnLabel.setBounds(30, 10, 100, 60); 
	    turnLabel.setOpaque(false);

	    ImageIcon timerBackgroundIcon = new ImageIcon(getClass().getResource("/images/orangeimg.png"));
	    timerLabel = new JLabel("30 초") {
	        @Override
	        protected void paintComponent(Graphics g) {
	            g.drawImage(timerBackgroundIcon.getImage(), 10, 0, 80, 60, null);
	            super.paintComponent(g);
	        }
	    };
	    timerLabel.setFont(smallFont);
	    timerLabel.setHorizontalAlignment(JLabel.CENTER);
	    timerLabel.setBounds(450, 10, 100, 60);
	    timerLabel.setOpaque(false);

	    ImageIcon titleBackgroundIcon = new ImageIcon(getClass().getResource("/images/title.png"));
	    topLabel = new JLabel() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(titleBackgroundIcon.getImage(), 50, 0, 180, 50, null);
	    		super.paintComponent(g);
	    	}
	    };
	    topLabel.setBounds(150, 20, 300, 50);

	    panel.add(turnLabel);
	    panel.add(topLabel);
	    panel.add(timerLabel);

	    return panel;
	}

	//펜의 굵기 선택(대,중,소)
	//지우개의 굵기 선택(대,중,소)
	//펜의 색상 선택(빨,주,노,초,파,검)
	//그림판 전체 지우기
	public JPanel getInfoPanel() {

		JPanel leftPanel1 = new JPanel();
		leftPanel1.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "연필", TitledBorder.CENTER, TitledBorder.TOP));
		leftPanel1.setLayout(new GridLayout(1, 3));
		leftPanel1.setOpaque(false);
		
		ImageIcon bigpenIcon = new ImageIcon(getClass().getResource("/images/lsize.png"));
		ImageIcon mediumpenIcon = new ImageIcon(getClass().getResource("/images/msize.png"));
		ImageIcon smallpenIcon = new ImageIcon(getClass().getResource("/images/ssize.png"));
		
		bigPencil = new JButton(bigpenIcon);
		bigPencil.setOpaque(false);  
		bigPencil.setContentAreaFilled(false); 
		bigPencil.setBorderPainted(false);
		bigPencil.setFocusPainted(false);
		
		mediumPencil = new JButton(mediumpenIcon);
		mediumPencil.setOpaque(false);  
		mediumPencil.setContentAreaFilled(false); 
		mediumPencil.setBorderPainted(false);
		mediumPencil.setFocusPainted(false);
		
		smallPencil = new JButton(smallpenIcon);
		smallPencil.setOpaque(false);  
		smallPencil.setContentAreaFilled(false);  
		smallPencil.setBorderPainted(false);
		smallPencil.setFocusPainted(false);

		bigPencil.addActionListener(this);
		mediumPencil.addActionListener(this);
		smallPencil.addActionListener(this);

		leftPanel1.add(bigPencil);
		leftPanel1.add(mediumPencil);
		leftPanel1.add(smallPencil);

		JPanel leftPanel2 = new JPanel();
		leftPanel2.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "지우개", TitledBorder.CENTER, TitledBorder.TOP));
		leftPanel2.setLayout(new GridLayout(1, 3));
		leftPanel2.setOpaque(false);
		
		ImageIcon bigeraserIcon = new ImageIcon(getClass().getResource("/images/lsize.png"));
		ImageIcon mediumeaserIcon = new ImageIcon(getClass().getResource("/images/msize.png"));
		ImageIcon smalleraserIcon = new ImageIcon(getClass().getResource("/images/ssize.png"));


		bigEraser = new JButton(bigeraserIcon);
		bigEraser.setOpaque(false);  
		bigEraser.setContentAreaFilled(false);  
		bigEraser.setBorderPainted(false);
		bigEraser.setFocusPainted(false);
		
		mediumEraser = new JButton(mediumeaserIcon);
		mediumEraser.setOpaque(false);  
		mediumEraser.setContentAreaFilled(false);  
		mediumEraser.setBorderPainted(false);
		mediumEraser.setFocusPainted(false);
		
		smallEraser = new JButton(smalleraserIcon);
		smallEraser.setOpaque(false);  
		smallEraser.setContentAreaFilled(false);  
		smallEraser.setBorderPainted(false);
		smallEraser.setFocusPainted(false);

		bigEraser.addActionListener(this);
		mediumEraser.addActionListener(this);
		smallEraser.addActionListener(this);

		leftPanel2.add(bigEraser);
		leftPanel2.add(mediumEraser);
		leftPanel2.add(smallEraser);

		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "전체삭제", TitledBorder.CENTER, TitledBorder.TOP));
		centerPanel.setOpaque(false);
		ImageIcon clearIcon = new ImageIcon(getClass().getResource("/images/allclear.png"));
		clearEraser = new JButton(clearIcon);
		clearEraser.setOpaque(false); 
		clearEraser.setContentAreaFilled(false);  
		clearEraser.setBorderPainted(false);
		clearEraser.setFocusPainted(false);
		clearEraser.addActionListener(this);
		centerPanel.add(clearEraser);

		ImageIcon ColorBackgroundIcon = new ImageIcon(getClass().getResource("/images/colorboard.png"));
		JPanel rightPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(ColorBackgroundIcon.getImage(), 0, 10, 185, 140, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		rightPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "색상선택", TitledBorder.CENTER, TitledBorder.TOP));
		rightPanel.setLayout(new GridLayout(2, 3));
		rightPanel.setOpaque(false);
		
		ImageIcon redIcon = new ImageIcon(getClass().getResource("/images/redcolor.png"));
		ImageIcon orangeIcon = new ImageIcon(getClass().getResource("/images/orangecolor.png"));
		ImageIcon yellowIcon = new ImageIcon(getClass().getResource("/images/yellowcolor.png"));
		ImageIcon greenIcon = new ImageIcon(getClass().getResource("/images/greencolor.png"));
		ImageIcon blueIcon = new ImageIcon(getClass().getResource("/images/bluecolor.png"));
		ImageIcon blackIcon = new ImageIcon(getClass().getResource("/images/blackcolor.png"));
		
	    redPen = new JButton() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(redIcon.getImage(), 15, 20, 40, 40, null);
	    		super.paintComponent(g);
	    	}
	    };
	    redPen.setBounds(150, 20, 40, 40);		
		redPen.setOpaque(false);  
		redPen.setContentAreaFilled(false); 
		redPen.setBorderPainted(false);

		orangePen = new JButton() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(orangeIcon.getImage(), 10, 10, 40, 40, null);
	    		super.paintComponent(g);
	    	}
	    };
	    orangePen.setBounds(150, 20, 40, 40);
		orangePen.setOpaque(false);  
		orangePen.setContentAreaFilled(false);  
		orangePen.setBorderPainted(false); 
		
		yellowPen = new JButton() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(yellowIcon.getImage(), 0, 25, 40, 40, null);
	    		super.paintComponent(g);
	    	}
	    };
	    yellowPen.setBounds(150, 20, 40, 40);
		yellowPen.setOpaque(false);  
		yellowPen.setContentAreaFilled(false);  
		yellowPen.setBorderPainted(false);  
		
		greenPen = new JButton() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(greenIcon.getImage(), 20, 0, 40, 40, null);
	    		super.paintComponent(g);
	    	}
	    };
	    greenPen.setBounds(150, 20, 40, 40);
		greenPen.setOpaque(false); 
		greenPen.setContentAreaFilled(false);  
		greenPen.setBorderPainted(false);  
		
		bluePen = new JButton() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(blueIcon.getImage(), 10, 12, 40, 40, null);
	    		super.paintComponent(g);
	    	}
	    };
	    bluePen.setBounds(150, 20, 40, 40);
		bluePen.setOpaque(false);  
		bluePen.setContentAreaFilled(false);  
		bluePen.setBorderPainted(false); 
		
		blackPen = new JButton() {
	    	@Override
	    	protected void paintComponent(Graphics g) {
	    		g.drawImage(blackIcon.getImage(), 0, 5, 40, 40, null);
	    		super.paintComponent(g);
	    	}
	    };
	    blackPen.setBounds(150, 20, 40, 40);
		blackPen.setOpaque(false);  
		blackPen.setContentAreaFilled(false);  
		blackPen.setBorderPainted(false);  

		redPen.addActionListener(this);
		orangePen.addActionListener(this);
		yellowPen.addActionListener(this);
		greenPen.addActionListener(this);
		bluePen.addActionListener(this);
		blackPen.addActionListener(this);

		rightPanel.add(redPen);
		rightPanel.add(orangePen);
		rightPanel.add(yellowPen);
		rightPanel.add(greenPen);
		rightPanel.add(bluePen);
		rightPanel.add(blackPen);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2, 1));
		leftPanel.setOpaque(false);
		leftPanel.add(leftPanel1);
		leftPanel.add(leftPanel2);

		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(1, 3));
		infoPanel.setOpaque(false);
		infoPanel.add(leftPanel);
		infoPanel.add(centerPanel);
		infoPanel.add(rightPanel);

		return infoPanel;
	}

	//그림판 패널
	public JPanel getPaintPanel() {
	    paintPanel = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/drawingboardimg.png"));
	            g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
	        }
	    };

	    paintPanel.setBackground(Color.WHITE); //기본 배경 색 흰색으로 설정
	    paintPanel.setPreferredSize(new Dimension(400, 600));
	    paintPanel.addMouseListener(this);
	    paintPanel.addMouseMotionListener(this);

	    return paintPanel;
	}


	//위치 설정
	public GameScreen(String title, int chN) {
		super(title);
		this.chN=chN;
		//this.title=title;
		JPanel Player1 = getChatPanel(0);
		Player1.setBounds(0, 0, 300, 167);

		JPanel Player2 = getChatPanel(1);
		Player2.setBounds(0, 167, 300, 167);

		JPanel Player3 = getChatPanel(2);
		Player3.setBounds(0, 334, 300, 167);

		JPanel Player4 = getChatPanel(3);
		Player4.setBounds(0, 501, 300, 167);

		JPanel TopBar = getStatusBarPanel();
		TopBar.setBounds(300, 0, 600, 70);

		paintPanel = getPaintPanel();
		JPanel infoPanel = getInfoPanel();
		paintPanel.setBounds(320, 80, 560, 440);
		infoPanel.setBounds(320, 530, 560, 150);
		
		JPanel newChatPanel = getNewChatPanel();
        newChatPanel.setBounds(880, 0, 300, 700);

		//setTitle("캐치마인드");
		setSize(new Dimension(1200, 735));
		//setResizable(false);
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setBackground(new Color(255, 230, 170));

		setLayout(null);
		add(Player1);
		add(Player2);
		add(Player3);
		add(Player4);
		add(TopBar);
		add(paintPanel);
		add(infoPanel);
		add(newChatPanel);

		g = paintPanel.getGraphics();
		graphic = (Graphics2D) g;
		
		addWindowListener(this);

	}
	
	@Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        // 음악 정지
        if (m != null) {
            m.mStop(); // 음악 정지
            m.close(); // 리소스 해제
        }

        dispose(); // 창 닫기
    }
}