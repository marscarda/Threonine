package threonine.map;
//**************************************************************************
public class MapFolder {
    //======================================================================
    long folderid = 0;
    long parentid = 0;
    long projectid = 0;
    String name = null;
    String publicname = null;
    String sharepass = null;
    int costperuse = 0;
    int searcheable = 0;
    boolean valid = false;
    //======================================================================
    public void setParentID (long parentid) { this.parentid = parentid; }
    public void setProjectID (long projectid) { this.projectid = projectid; }
    public void setName (String name) { this.name = name; }
    public void setPublicName (String publicname) { this.publicname = publicname; }
    public void setSharePass (String sharepass) { this.sharepass = sharepass; }
    //======================================================================
    public long getID () { return folderid; }
    public long getParentID () { return parentid; }
    public long projectID () { return projectid; }
    public String getName () {
        if (name == null) return "";
        return name;
    }
    public String publicName () {
        if (publicname == null) return "";
        return publicname;
    }
    public String sharePass () {
        if (sharepass == null) return "";
        return sharepass;
    }
    public int costPerUse () { return costperuse; }
    public int isSearchableInt () { return searcheable; }
    public boolean isSearcheable () { return searcheable != 0; }
    public void setValid() { valid = valid; }
    //======================================================================
    public boolean isRoot () { return folderid == 0; }
    public boolean isValid () { return valid; }
    //======================================================================
}
//**************************************************************************
