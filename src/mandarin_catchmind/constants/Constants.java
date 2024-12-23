package mandarin_catchmind.constants;

public interface Constants {
	static final String DELIMETER = "&";
	static final String SUB_DELIMETER = ",";
	   
	static final String ALL_CONNECTED = "ALL_CONNECTED";
	static final String ID = "ID";
	static final String ROOMNAME = "ROOMNAME";
	static final String NICKNAME = "NICKNAME";
	static final String START_READY = "START_READY";
	static final String TURN_READY = "TURN_READY";
	static final String CurP = "CurP";
	static final String WORD = "WORD";
	static final String CORRECT = "CORRECT";
	static final String TURN_END = "TURN_END";
	static final String TIMEOVER = "TIMEOVER";
	static final String RESULT = "RESULT";
	static final String EXIT = "EXIT";
	   
	static final String CHAT = "CHAT";
	static final String CH = "CH";
	static final String DRAW = "DRAW";
	static final String COLOR = "COLOR";
	static final String THICKNESS = "THICKNESS";
	   
	//플레이어 수 설정(테스트를 위해 2명으로 설정, 최대 4명까지 가능)
	static final int playerCount = 2;
	//턴 수 설정(테스트를 위해 4턴으로 설정, 최대 10턴)
	static final int TURN_COUNT = 3;
	//제한시간 설정
	static final int SEC = 30;  
}