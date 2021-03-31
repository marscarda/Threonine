package threonine.map;
//**************************************************************************
public class MapRecord {
    //======================================================================
    long recordid = 0;
    long folderid = 0;
    long projectid = 0;
    String name = null;
    String extradata = null;
    String admindiv = null;
    //======================================================================
    public long getID () { return recordid; }
    //----------------------------------------------------------------------
    public void setFolderId (long folderid) { this.folderid = folderid; }
    public void setProjectId (long projectid) { this.projectid = projectid; }
    public void setName (String name) { this.name = name; }
    public void setExtraData (String extradata) { this.extradata = extradata; }
    public void setAdminDivision (String admindiv) { this.admindiv = admindiv; }
    //----------------------------------------------------------------------
    public long getFolderID () { return folderid; }
    public long getProjectID () { return projectid; }
    public String getName () {
        if (name == null) return "";
        return name;
    }
    public String getExtraData () {
        if (extradata == null) return "";
        return extradata;
    }
    public String getAdminDivision () {
        if (admindiv == null) return "";
        return admindiv;
    }
    //======================================================================
}
//**************************************************************************
