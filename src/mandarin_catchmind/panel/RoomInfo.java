package mandarin_catchmind.panel;

public class RoomInfo {
    private String roomName;
    private int port;
    private int number;
<<<<<<< HEAD
    private int chN;
    
    public RoomInfo(String roomName, int port, int number, int chN) {
        this.roomName = roomName;
        this.port = port;
        this.number = number;
        this.chN = chN;
=======

    public RoomInfo(String roomName, int port, int number) {
        this.roomName = roomName;
        this.port = port;
        this.number = number;
>>>>>>> c68beedd524537c128b3b8381aa165add7a514b9
    }

    public String getRoomName() {
        return roomName;
    }

    public int getPort() {
        return port;
    }
<<<<<<< HEAD
    
    public int chN() {
    	return chN;
    }

    @Override
    public String toString() {
        return number + "번방▶  " + roomName + " (포트: " + port + ")";
=======

    @Override
    public String toString() {
        return number + "번 방: " + roomName + " (포트: " + port + ")";
>>>>>>> c68beedd524537c128b3b8381aa165add7a514b9
    }
}
