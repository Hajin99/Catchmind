package mandarin_catchmind.panel;

public class RoomInfo {
    private String roomName;
    private int port;
    private int number;

    public RoomInfo(String roomName, int port, int number) {
        this.roomName = roomName;
        this.port = port;
        this.number = number;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return number + "번 방: " + roomName + " (포트: " + port + ")";
    }
}
