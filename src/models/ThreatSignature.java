package models;

public class ThreatSignature {

    private String signature;
    private String severity;

    public ThreatSignature(String signature, String severity) {
        this.signature = signature;
        this.severity = severity;
    }

    public String getSignature() { return signature; }
    public String getSeverity()  { return severity; }

    public String toString() {
        return signature + " (" + severity + ")";
    }
}