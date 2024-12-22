package mandarin_catchmind.client;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private File audioFile;
    private AudioInputStream audioStream;
    private Clip clip;

    public Music() {
        try {
            // 오디오 파일 로드
            audioFile = new File("src/audio/funny.wav");
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("오디오 파일을 로드하거나 클립을 열 수 없습니다.");
        }
    }

    // 음악 재생
    public void mStart() {
        if (clip != null) {
            clip.setMicrosecondPosition(0); // 처음부터 재생
            clip.start();
            System.out.println("음악 재생 시작.");
        }
    }

    // 음악 정지
    public void mStop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            System.out.println("음악 정지.");
        }
    }

    // 리소스 닫기
    public void close() {
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            if (audioStream != null) {
                audioStream.close();
            }
            System.out.println("오디오 리소스 해제 완료.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Clip getClip() {
    	return clip;
    }
    
//    public static void main(String[] args) throws Exception
//	{
//    	Music m = new Music();
//    	m.mStart();
//    	Thread.sleep(m.getClip().getMicrosecondLength()/1000);
//	}
}
