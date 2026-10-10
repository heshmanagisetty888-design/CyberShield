package models;

public class SecurityLog {
    private String timestamp, user, ip, event, message, risk;

    public SecurityLog(String timestamp, String user, String ip,
                       String event, String message, String risk) {
        this.timestamp = timestamp;
        this.user = user;
        this.ip = ip;
        this.event = event;
        this.message = message;
        this.risk = risk;
    }

    public String getTimestamp() { return timestamp; }
    public String getUser()      { return user; }
    public String getIp()        { return ip; }
    public String getEvent()     { return event; }
    public String getMessage()   { return message; }
    public String getRisk()      { return risk; }

    public String toString() {
        return "[" + timestamp + "] " + user + "@" + ip
                + " - " + event + " (" + risk + ") :: " + message;
    }
}