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
    //------------------------------------------------------
    public long universeID () { return univerid; }
    //------------------------------------------------------
    public void setProjectId (long projectid) { this.projectid = projectid; }
    public void setName (String name) { this.name = name; }
    public String getName () {
        if (name == null) return "";
        return name;
    }
    //------------------------------------------------------
    public long projectID () { return projectid; }
    public void setDescription (String description) { this.description = description; }
    public String getDescription () {
        if (description == null) return "";
        return description;
    }
    //**********************************************************
    public boolean checkValidData () {
        if (name == null) return false;
        if (name.length() == 0) return false;
        return true;
    }
    //**********************************************************
}
//**************************************************************************
