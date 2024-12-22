package mandarin_catchmind.panel;

public class RoomInfo {
    private String roomName;
    private int port;
    private int number;
    private int chN;
    
    public RoomInfo(String roomName, int port, int number, int chN) {
        this.roomName = roomName;
        this.port = port;
        this.number = number;
        this.chN = chN;

    }

    public String getRoomName() {
        return roomName;
    }

    public int getPort() {
        return port;
    }
    
    public int chN() {
    	return chN;
    }

    @Override
    public String toString() {
        return number + "번방▶  " + roomName + " (포트: " + port + ")";

    }
}
