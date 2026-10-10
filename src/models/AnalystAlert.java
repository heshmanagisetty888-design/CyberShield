package models;

public class AnalystAlert {
    private String analyst;
    private String alert;
    private String skill;

    public AnalystAlert(String analyst, String alert, String skill) {
        this.analyst = analyst;
        this.alert = alert;
        this.skill = skill;
    }

    public String getAnalyst() { return analyst; }
    public String getAlert()   { return alert; }
    public String getSkill()   { return skill; }

    public String toString() {
        return analyst + " -> " + alert + " (" + skill + ")";
    }
}