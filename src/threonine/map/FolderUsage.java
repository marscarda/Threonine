    package threonine.map;
//**************************************************************************
public class FolderUsage {
    //======================================================================
    long projectid = 0;
    long folderid = 0;
    float costperuse = 0;
    String publicname = null;
    String sharepass = null;
    //======================================================================
    public void setProjectID (long projectid) { this.projectid = projectid; }
    public void setFolderID (long folderid) { this.folderid = folderid; }
    public void setPublicName (String publicname) { this.publicname = publicname; }
    public void setSharePass (String sharepass) { this.sharepass = sharepass; }
    public void setCost (float cost) { costperuse = cost; }
    //======================================================================
    public long projectID () { return projectid; }
    public long folderID () { return folderid; }
    public float costPerUse () { return costperuse; }
    public String publicName () {
        if (publicname == null) return "";
        return publicname;
    }
    public String sharePassword () { 
        if (sharepass == null) return "";
        return sharepass; 
    }
    //======================================================================
    public boolean hasPassword () { return sharepass != null; }
    //======================================================================
}
//**************************************************************************
