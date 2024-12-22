package mandarin_catchmind.panel;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class RankFrame extends JFrame {
    private static RankFrame instance;  // RankFrame의 인스턴스를 저장하는 static 변수

    // 순위별 닉네임과 점수를 표시할 레이블
    private JLabel rank1, rank2, rank3, rank4;
    
    // private 생성자 (외부에서 생성하지 못하도록)
    private RankFrame(String resultMessage) {
        setTitle("결과 발표");
        setSize(350, 380);  // 창 크기를 조정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 창 닫을 때만 종료
        setLocationRelativeTo(null);  // 화면 중앙에 창을 위치시킴
        setLayout(null);  // 절대 위치 배치 관리

        // 배경 이미지 설정
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/rank.png"));  // rank.png 이미지 파일 경로 설정
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(-10, -20, getWidth(), getHeight());  // 배경 크기를 프레임 크기에 맞춤
        add(background);  // 배경 추가
        
        // 순위별 텍스트를 표시할 레이블 설정 (순위 텍스트 제거)
        rank1 = createRankLabel(40);
        rank2 = createRankLabel(110);
        rank3 = createRankLabel(180);
        rank4 = createRankLabel(250);
        
        // 텍스트를 순위별로 갱신
        updateRank(resultMessage);

        // 배경 위에 레이블 추가
        background.add(rank1);
        background.add(rank2);
        background.add(rank3);
        background.add(rank4);
    }

    // 레이블을 생성하는 함수
    private JLabel createRankLabel(int yPos) {
        JLabel label = new JLabel("");  // 초기에는 빈 텍스트
        label.setFont(new Font("1훈솜사탕 Regular", Font.BOLD, 20));
        label.setForeground(Color.BLACK); 
        label.setBounds(90, yPos, 350, 30);  // 레이블 위치와 크기 설정
        return label;
    }

    // 순위 정보를 갱신하는 메소드
    public void updateRank(String resultMessage) {
        String[] lines = resultMessage.split("\n");
        if (lines.length > 0) rank1.setText(lines[0]);  // 순위 1
        if (lines.length > 1) rank2.setText(lines[1]);  // 순위 2
        if (lines.length > 2) rank3.setText(lines[2]);  // 순위 3
        if (lines.length > 3) rank4.setText(lines[3]);  // 순위 4
    }

    // getInstance 메소드로 인스턴스 관리
    public static RankFrame getInstance(String resultMessage) {
        if (instance == null) {  // 아직 인스턴스가 생성되지 않았다면
            instance = new RankFrame(resultMessage);
        } else {
            instance.updateRank(resultMessage);  // 이미 열려 있으면 텍스트만 갱신
        }
        return instance;
    }
}

