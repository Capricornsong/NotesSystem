package cn.edu.bnuz.notes.websocket;

public class SocketMessage {
    private String content;
    private String time;
    private int isMeSend;//0是对方发送 1是自己发送

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsMeSend() {
        return isMeSend;
    }

    public void setIsMeSend(int isMeSend) {
        this.isMeSend = isMeSend;
    }
}
