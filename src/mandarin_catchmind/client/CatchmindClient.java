package mandarin_catchmind.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket; 
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mandarin_catchmind.client.CatchmindClient.TimerThread;
import mandarin_catchmind.constants.Constants;
import mandarin_catchmind.panel.GameScreen;
import mandarin_catchmind.panel.RankFrame;

public class CatchmindClient extends GameScreen implements Runnable, Constants {
	   
	   private Socket socket;
	   private BufferedReader br;
	   private PrintWriter writer;
	   private String roomName;
	   private int portNumber;
	   private int chN;
	   
	   private String sendDraw = null;
	   private String sendColor = null;
	   private String sendThickness = null;
	   private String sendScore = null;
	   private String sendMessage = null;
	   
	   private String myNickName;
	   private int myChr;//캐릭터 설정
	   private ImageIcon ch = new ImageIcon(getClass().getResource("/images/button.png"));
	   private ImageIcon ch1 = new ImageIcon(getClass().getResource("/images/1_2.png"));
	   private ImageIcon ch2 = new ImageIcon(getClass().getResource("/images/2_2.png"));
		
		
	   private int myId;
	   private String[] nicknames = new String[PLAYER_COUNT];
	   private int[] scores = new int[PLAYER_COUNT];
	   private int currentPlayerCount;
	   private boolean canDraw = true;
	   private int turnCount = 0;
	   private SimpleDateFormat sdf = new SimpleDateFormat("(YYYY-MM-dd HH:mm:ss)");
	   
	   public CatchmindClient(String roomName, int portNumber, int chN) {
		  super(roomName, chN);
		  this.roomName=roomName;
		  this.portNumber=portNumber;
		  this.chN = chN;
		  System.out.println(chN); //캐릭터 선택 확인
		  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	      addWindowListener(this);
	      connectSocket();
	   }
	   
	   TimerThread timer;
	   
	   //제한 시간 표시
	   public class TimerThread extends Thread {
	      
	      CatchmindClient client;
	      boolean run = true;
	      
	      TimerThread(CatchmindClient client) {
	         this.client = client;
	      }
	      
	      public void stopTimer() {
	         run = false;
	      }
	      
	   @Override
	   public void run() {
	      try {
	         int i = SEC;
	         while (true) {
	         
	            if (run == true) {
	               timerLabel.setText(i + " 초");
	               Thread.sleep(2000);
	            
	               //제한시간 1초씩 감소
	               i--;
	               if (i < 0) {
	            	  //제한시간 끝나면 시간초과창
	                  int timeover = JOptionPane.showConfirmDialog(client, "타임오버!", "시간초과 안내", JOptionPane.DEFAULT_OPTION);
	                  
	                  if (timeover == JOptionPane.OK_OPTION) {
	                     writer.println("TIMEOVER&");
	                     turnCount++;
	                     
	                     	if (turnCount == TURN_COUNT) {
	                     		writer.println("RESULT&");
	                     	}
	                  }     
	                  break;
	               	}
	            }
	         }
	      } catch (InterruptedException e) {
	         e.printStackTrace();
	      	}
	   	}
	  }

	   public void connectSocket() {
	      try {
	    	 socket = new Socket("127.0.0.1", portNumber); 
	        //socket = new Socket("127.0.0.1", 5000); 

	        //서버에 정보를 보내는 OutputStream, 정보를 받는 InputStream 연결
	        writer = new PrintWriter(socket.getOutputStream(), true); 
	        br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
	        System.out.println( roomName + "에 입장!");
	        
	        writer.println("ROOMNAME&" + roomName); // 방 이름을 서버로 전송
            System.out.println("서버로 방 이름 전송: " + roomName);
	        
	        Thread thread = new Thread(this); 
	        thread.start();

	        System.out.println(
	        	//서버 연결 성공시 IP와 현재 시간 표시
	            "서버와 연결되었습니다.\n" + "IP : " + socket.getInetAddress() + sdf.format(System.currentTimeMillis()));

	      } catch (IOException e) {
	    	  //서버와 연결 실패시
	    	  System.out.println("서버와 연결을 실패했습니다.");
	    	  e.printStackTrace();
	      }
	   }

	   //그림, 닉네임, 채팅 수신
	   @Override
	   public void run() {
	      try {
	       
	         String Message;
	         String[] parsMessage;

	         int startX = 0;
	         int startY = 0;
	         int endX = 0;
	         int endY = 0;
	         Color SendedColor = Color.BLACK;
	         Color SendedColorMemory = Color.BLACK;
	         
	         while ((Message = br.readLine()) != null) {
	            parsMessage = Message.split(DELIMETER);
	            
	            switch (parsMessage[0]) {
	            case ID:
	               myId = Integer.parseInt(parsMessage[1]);
	               break;
	            
	            case ALL_CONNECTED:
	               myNickName = JOptionPane.showInputDialog("닉네임을 입력하세요");
	               nameLabelArr[myId].setText(myNickName + "(나)");
	               writer.println("NICKNAME&" + myNickName + "," + chN);
	               break;
	            
	            case NICKNAME:
	               System.out.println(Message);
	               receiveNickname(parsMessage);
	               break;
	            
	            case CurP:
	               receiveCurP(parsMessage);
	               break;
	              
	            case WORD:
	               receiveWord(parsMessage);
	               break;
	            
	            case CORRECT:
	               receiveCorrect(parsMessage);
	               break;
	           
	            case RESULT:
	               receiveResult(parsMessage);
	               break;
	               
	            case DRAW:
	               if ("delete".equals(parsMessage[1])) {
	                  paintPanel.repaint();
	                  break;
	               }
	               
	               String draw[] = parsMessage[1].split(SUB_DELIMETER);
	               graphic.setColor(SendedColor);
	               
	               if ("start".equals(draw[0])) {
	                  startX = Integer.parseInt(draw[1]);
	                  startY = Integer.parseInt(draw[2]);
	               }

	               if ("end".equals(draw[0])) {
	                  endX = Integer.parseInt(draw[1]);
	                  endY = Integer.parseInt(draw[2]);

	                  graphic.drawLine(startX, startY, endX, endY); 

	                  startX = endX;
	                  startY = endY;
	               }
	               break;
	               
	            case COLOR:
	               if ("red".equals(parsMessage[1])) {
	                  graphic.setColor(Color.RED);
	                  SendedColor = Color.RED;
	               } else if ("orange".equals(parsMessage[1])) {
	                  graphic.setColor(Color.ORANGE);
	                  SendedColor = Color.ORANGE;
	               } else if ("yellow".equals(parsMessage[1])) {
	                  graphic.setColor(Color.YELLOW);
	                  SendedColor = Color.YELLOW;
	               } else if ("green".equals(parsMessage[1])) {
	                  graphic.setColor(Color.GREEN);
	                  SendedColor = Color.GREEN;
	               } else if ("blue".equals(parsMessage[1])) {
	                  graphic.setColor(Color.BLUE);
	                  SendedColor = Color.BLUE;
	               } else if ("black".equals(parsMessage[1])) {
	                  graphic.setColor(Color.BLACK);
	                  SendedColor = Color.BLACK;
	               } else if ("white".equals(parsMessage[1])) {
	                  graphic.setColor(Color.WHITE);
	                  SendedColorMemory = SendedColor;
	                  SendedColor = Color.WHITE;
	               }
	               break;
	               
	            case THICKNESS:
	               graphic.setStroke(new BasicStroke(Integer.parseInt(parsMessage[1]), BasicStroke.CAP_ROUND, 0)); 
	               break;
	            	
	            case CHAT:
	            	String chat[] = parsMessage[1].split(SUB_DELIMETER); 
	            	// 보낸 사람의 ID
	                int senderId = Integer.parseInt(chat[0]);           
	                String senderMessage = chat[1]; 
	                // ID를 닉네임으로 변환                    
	                String senderName = nicknames[senderId];            
	                // 채팅창에 닉네임: 메시지 형식으로 출력
	                newChatArea.append(senderName + ": " + senderMessage + "\n");
	               break;
	               
	            case  CH:
	                String chatText = parsMessage[1];
	                newChatArea.append(chatText + "\n");
	                break;
	                
	            default:
	            }
	         }
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   //접속한 플레이어가 닉네임을 모두 입력하면 게임 시작
	   public void receiveNickname(String[] parsMessage) {
		   String[] data = parsMessage[1].split(SUB_DELIMETER); // SUB_DELIMETER는 ',' 또는 '&' 등
		   for (int i = 0; i < data.length; i += 2) {
		        int index = i / 2;
		        nicknames[index] = data[i]; // 닉네임 저장
		        int characterNumber = Integer.parseInt(data[i + 1]); // 캐릭터 번호 저장

		        // UI 업데이트
		        nameLabelArr[index].setText(nicknames[index]);
		        if (characterNumber == 1) {
		            imgLabelArr[index].setIcon(ch1);
		        } else {
		            imgLabelArr[index].setIcon(ch2);
		        }
		    }
		    // 내 이름은 따로 표시
		    nameLabelArr[myId].setText(myNickName + "(나)");
	       
	       int startReady = JOptionPane.showConfirmDialog(this, "게임을 시작합니다", "게임 시작 안내",
	             JOptionPane.DEFAULT_OPTION);

	       if (startReady == JOptionPane.OK_OPTION) {
	          writer.println("START_READY&");
	       }     
	   }

	   //출제자,턴 수,제한시간 표시 및 제시어 받기
	   public void receiveCurP(String[] parsMessage) {
	      turnLabel.setText((turnCount+1) + "/10 턴");  
	       topLabel.setText("-");  
	       paintPanel.repaint();  
	       timerLabel.setText(SEC + " 초");  
	       
	       currentPlayerCount = Integer.parseInt(parsMessage[1]);
	       
	       int turnReady = JOptionPane.showConfirmDialog(this, nicknames[currentPlayerCount] + "님 차례입니다" , "출제자 안내",
	             JOptionPane.DEFAULT_OPTION);
	       
	       if (turnReady == JOptionPane.OK_OPTION) {
	          writer.println("TURN_READY&");
	       }
	      
	   }

	   //제시어 받기, 출제자만 제시어를 볼 수 있음, 제시어 받으면 제한시간 줄어들기 시작
	   public void receiveWord(String[] parsMessage) {
	      
	       if (myId != currentPlayerCount) {
	          
	          messageTf.setEditable(true);
	          messageTaArr[myId].setText("");

	          messageTaArr[currentPlayerCount].setText(nicknames[currentPlayerCount] + "님 차례입니다.");

	          canDraw = false;
	       }

	       if (myId == currentPlayerCount) {
	          
	          messageTf.setEditable(false);
	          for (int i=0; i<PLAYER_COUNT; ++i) messageTaArr[i].setText("");  
	          
	          canDraw = true;

	          messageTaArr[currentPlayerCount].setText("※ 제시어 : " + parsMessage[1]);   
	       }
	       timer = new TimerThread(this);
	       timer.start();
	   }  	

	   //정답이 나오면 타이머 멈추며 다음 출제자 알려주고 모두 확인하면 다음 턴 시작
	   //정답을 맞춘 플레이어는 2점, 출제자는 1점
	   public void receiveCorrect(String[] parsMessage) {
	       turnCount++;  
	       timer.stopTimer();
	       timer = null;
	       System.gc();
	       String correct[] = parsMessage[1].split(SUB_DELIMETER);
	       String correctNickname = nicknames[Integer.parseInt(correct[0])];
	             int correctJop = JOptionPane.showConfirmDialog(this, "정답은 " + correct[1] + "입니다!\n" + correctNickname + "님 정답!", "정답 안내",
	             JOptionPane.DEFAULT_OPTION);

	       if (correctJop == JOptionPane.OK_OPTION) {
	          if (turnCount != TURN_COUNT)
	             writer.println("TURN_END&");  
	       }
	       
	       scores[Integer.parseInt(correct[0])] += 2;
	       scores[currentPlayerCount] += 1;
	       
	       scoreLabelArr[Integer.parseInt(correct[0])].setText("점수 : " + scores[Integer.parseInt(correct[0])]);
	       scoreLabelArr[currentPlayerCount].setText("점수 : " + scores[currentPlayerCount]);

	       if (turnCount == TURN_COUNT) {
	          writer.println("RESULT&");
	       }
	   }

	   //모든 턴이 끝나면 순위와 점수 안내
	   public void receiveResult(String[] parsMessage) {
		    String resultMessage = "\n";

		    int[] rank = idxOfSorted(scores);
		    Set<String> printedNicknames = new HashSet<>();
		    Set<Integer> usedRanks = new HashSet<>();

		    for (int i = 0; i < rank.length; ++i) {
		        int currentRank = i + 1;
		        int currentScore = scores[rank[i]];
		        String currentNickname = nicknames[rank[i]];

		        // 이미 출력한 닉네임이면 다음 등수로 건너뛰기
		        if (!printedNicknames.add(currentNickname)) {
		            continue;
		        }

		        // 같은 점수인 경우 등수를 증가시키지 않고 그대로 유지
		        if (i > 0 && scores[rank[i - 1]] == currentScore) {
		            currentRank = i;
		        }

		        resultMessage += currentNickname + "   ==   " + currentScore + "점\n";
		    }

		    // 새로운 랭킹 화면을 띄운다 (getInstance 사용)
		    RankFrame rankFrame = RankFrame.getInstance(resultMessage);
		    rankFrame.setVisible(true);

		    // 이전 게임 화면을 숨긴다
		    this.setVisible(false);  // 현재 게임 화면을 숨깁니다.
		    this.dispose();  // 게임 화면을 완전히 종료합니다.
		}


	   //결과 창 순위
	   public int[] idxOfSorted(int[] Scores) {
	          Integer[] indexes = new Integer[Scores.length];
	          for (int i = 0; i < indexes.length; i++) {
	              indexes[i] = i;
	          }

	          Arrays.sort(indexes, (i, j) -> Integer.compare(Scores[j], Scores[i]));

	          int[] idxOfSorted = new int[Scores.length];
	          for (int i = 0; i < idxOfSorted.length; i++) {
	              idxOfSorted[i] = indexes[i];
	          }

	          return idxOfSorted;
	      }
	   
	   @Override
	   public void keyPressed(KeyEvent e) {
	      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        
	         messageTaArr[myId].setText(messageTf.getText());

	         sendMessage = "CHAT&" + myId + "," + messageTf.getText();
	         writer.println(sendMessage);
	         //writer.println(SendMessage);

	         messageTf.setText(null);
	      }
	   }

	   //그림판 패널에서 마우스 클릭
	   @Override
	   public void mousePressed(MouseEvent e) {
	      
	      if (canDraw == true) {
	              graphic.setColor(currentColor);
	              
	            startX = e.getX(); 
	            startY = e.getY(); 

	            // 서버로 전달
	            sendScore = "DRAW&" + "start," + startX + "," + startY;
	            writer.println(sendScore);
	            if (true) {
	               sendThickness = "THICKNESS&" + thickness;
	               writer.println(sendThickness);
	            }
	      }
	   }

	   //그림판 패널에서 마우스 드래그
	   @Override
	   public void mouseDragged(MouseEvent e) {
	      
	      
	      if (canDraw == true) {
	            endX = e.getX();

	            endY = e.getY();
	            
	            sendScore = "DRAW&" + "end," + endX + "," + endY;
	            writer.println(sendScore);

	            graphic.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, 0)); 
	            graphic.drawLine(startX, startY, endX, endY); 

	            startX = endX; 
	            startY = endY; 
	      }
	   }

	  
	   @Override
	   public void actionPerformed(ActionEvent e) {
	      JButton jButton = (JButton) e.getSource();

	      //채팅창 메시지 보내는 경우
	      if ("보내기".equals(jButton.getText())) {

	          String newChatMessage = "CHAT&" + myId + SUB_DELIMETER + newMessageTf.getText();
	          writer.println(newChatMessage);

	          newMessageTf.setText(null);
	      }
	     
	      //연필 굵기, 지우개 굵기, 전체 삭제, 펜의 색깔 선택 하는 경우
	      if (e.getSource() == bigPencil) {
	        currentColor = currentColorMemory;
	         graphic.setColor(currentColor);
	         thickness = 10; 
	         sendThickness = "THICKNESS&" + thickness; 
	         writer.println(sendThickness);
	      }
	      
	      if (e.getSource() == mediumPencil) {
	        currentColor = currentColorMemory;
	         graphic.setColor(currentColor);
	         thickness = 5; 
	         sendThickness = "THICKNESS&" + thickness; 
	         writer.println(sendThickness);
	      }
	      
	      if (e.getSource() == smallPencil) {
	        currentColor = currentColorMemory;
	         graphic.setColor(currentColor);
	         thickness = 1; 
	         sendThickness = "THICKNESS&" + thickness; 
	         writer.println(sendThickness);
	      }
	     
	      if (e.getSource() == bigEraser) {
	        currentColorMemory = currentColor;
	         currentColor = Color.WHITE;
	         graphic.setColor(currentColor);
	         thickness = 10; 
	         sendThickness = "THICKNESS&" + thickness; 
	         sendColor = "COLOR&white"; 
	         writer.println(sendThickness);
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == mediumEraser) {
	        currentColorMemory = currentColor;
	         currentColor = Color.WHITE;
	         graphic.setColor(currentColor);
	         thickness = 5; 
	         sendThickness = "THICKNESS&" + thickness; 
	         sendColor = "COLOR&white"; 
	         writer.println(sendThickness);
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == smallEraser) {
	        currentColorMemory = currentColor;
	         currentColor = Color.WHITE;
	         graphic.setColor(currentColor);
	         thickness = 1; 
	         sendThickness = "THICKNESS&" + thickness; 
	         sendColor = "COLOR&white"; 
	         writer.println(sendThickness);
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == clearEraser) {
	         paintPanel.repaint(); 
	         sendDraw = "DRAW&delete";
	         writer.println(sendDraw);
	      }
	      
	      if (e.getSource() == redPen) {
	         currentColor = Color.RED;
	         graphic.setColor(currentColor);
	         sendColor = "COLOR&red";
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == orangePen) {
	         currentColor = Color.ORANGE;
	         graphic.setColor(currentColor);
	         sendColor = "COLOR&orange";
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == yellowPen) {
	         currentColor = Color.YELLOW;
	         graphic.setColor(currentColor);
	         sendColor = "COLOR&yellow";
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == greenPen) {
	         currentColor = Color.GREEN;
	         graphic.setColor(currentColor);
	         sendColor = "COLOR&green";
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == bluePen) {
	         currentColor = Color.BLUE;
	         graphic.setColor(currentColor);
	         sendColor = "COLOR&blue";
	         writer.println(sendColor);
	      }
	      
	      if (e.getSource() == blackPen) {
	         currentColor = Color.BLACK;
	         graphic.setColor(currentColor);
	         sendColor = "COLOR&black";
	         writer.println(sendColor);
	      }
	   }

	   //창 종료 시 소켓 종료
	   @Override
	   public void windowClosed(WindowEvent e) {
	      try {
	         socket.close();
	         System.out.println("소켓 닫힘");
	      } catch (IOException e1) {
	         e1.printStackTrace();
	      }
	   }

	   @Override
	   public void keyTyped(KeyEvent e) {}
	   @Override
	   public void keyReleased(KeyEvent e) {}
	   @Override
	   public void mouseClicked(MouseEvent e) {}
	   @Override
	   public void mouseReleased(MouseEvent e) {}
	   @Override
	   public void mouseEntered(MouseEvent e) {}
	   @Override
	   public void mouseExited(MouseEvent e) {}
	   @Override
	   public void mouseMoved(MouseEvent e) {}
	   @Override
	   public void windowOpened(WindowEvent e) {}
	   @Override
	   public void windowClosing(WindowEvent e) {}
	   @Override
	   public void windowIconified(WindowEvent e) {}
	   @Override
	   public void windowDeiconified(WindowEvent e) {}
	   @Override
	   public void windowActivated(WindowEvent e) {}
	   @Override
	   public void windowDeactivated(WindowEvent e) {}

	   public static void main(String[] args) {
	      //new CatchmindClient("gkdl");
	   }
}