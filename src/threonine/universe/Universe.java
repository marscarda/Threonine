package threonine.universe;
//**************************************************************************
import methionine.ValidData;
//**************************************************************************
public class Universe extends ValidData {
    //**********************************************************
    long univerid = 0;
    long projectid = 0;
    String name = null;
    String description = null;
    int ispublic = 0;
    float price = 0;
    String notpubuntil = null;
    String currentdate = null;
    //------------------------------------------------------
    public long universeID () { return univerid; }
    //------------------------------------------------------
    public void setProjectId (long projectid) { this.projectid = projectid; }
    public void setName (String name) { this.name = name; }
    public void setNotPubUntilDate (String date) { notpubuntil = date; }
    void setCurrentDate (String now) { currentdate = now; }
    public String getName () {
        if (name == null) return "";
        return name;
    }
    //------------------------------------------------------
    public long projectID () { return projectid; }
    public void setDescription (String description) { this.description = description; }
    public boolean isPublic () { return ispublic != 0; }
    public float getPrice () { return price; }
    public String getDescription () {
        if (description == null) return "";
        return description;
    }
    public String notPubUntil () {
        if (notpubuntil == null) return "";
        return notpubuntil;
    }
    //**********************************************************
    public boolean allowToPublish () { 
        if (currentdate == null) return true;
        if (notpubuntil == null) return true;
        int v = currentdate.compareTo(notpubuntil);
        return v > 0;
    }
    //**********************************************************
    public static int PUBUNTILDAYS = 21;
    //**********************************************************
}
//**************************************************************************
