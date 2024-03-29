package threonine.mapping;
//**************************************************************************
public class MapFolder {
    //======================================================================
    long folderid = 0;
    long parentid = 0;
    long projectid = 0;
    String name = null;
    String publicname = null;
    String sharepass = null;
    float costperuse = 0;
    int searcheable = 0;
    boolean valid = false;
    //----------------------------------------------------------------------
    long userid = 0;
    String username = null;
    //======================================================================
    public void setParentID (long parentid) { this.parentid = parentid; }
    public void setProjectID (long projectid) { this.projectid = projectid; }
    public void setName (String name) { this.name = name; }
    public void setPublicName (String publicname) { this.publicname = publicname; }
    public void setSharePass (String sharepass) { this.sharepass = sharepass; }
    //public void setUserID (long userid) { this.userid = userid; }
    //public void setUserName (String username) { this.username = username; }
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
    public float costPerUse () { return costperuse; }
    public int isSearchableInt () { return searcheable; }
    public boolean isSearcheable () { return searcheable != 0; }
    //======================================================================
    //This methods are not necesarily called from all methods tha deal with it
    //This fields are not present in folders table.
    public void setUserID (long userid) { this.userid = userid; }
    public void setUserName (String username) { this.username = username; }
    //======================================================================
    public void setValid() { valid = valid; }
    //======================================================================
    public boolean isRoot () { return folderid == 0; }
    public boolean isValid () { return valid; }
    //======================================================================
    public boolean checkPassword (String pass) {
        if (sharepass == null) return false;
        if (sharepass.length() == 0) return false;
        return sharepass.compareTo(pass) == 0;
    }
    //======================================================================
    /**
     * Availability depends on the method that was called.
     * @return 
     */
    public long userID () { return userid; }
    /**
     * Availability depends on the method that was called.
     * @return 
     */
    public String userName () {
        if (username == null) return "";
        return username;
    }
    //======================================================================
}
//**************************************************************************
