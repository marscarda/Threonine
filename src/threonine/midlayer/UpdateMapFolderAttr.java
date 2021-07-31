package threonine.midlayer;
//***************************************************************************
public class UpdateMapFolderAttr {
    //**********************************************************
    int attrib = 0;
    String publicname = null;
    String sharepass = null;
    int costperuse = 0;
    int searchable = 0;
    //==========================================================
    public void setAttribute (int attrib) { this.attrib = attrib; }
    public void setPublicName (String publicname) { this.publicname = publicname; }
    public void setSharePass (String sharepass) { this.sharepass = sharepass; }
    public void setCostPerUse (int costperuse) { this.costperuse = costperuse; }
    public void setSearchable (int searchable) { this.searchable = searchable; }
    //==========================================================
    public int costPerUse () { return costperuse; }
    public boolean isSearchable () { return searchable != 0; }
    public String publicName () {
        if (publicname == null) return "";
        return publicname;
    }
    public String sharePass () {
        if (sharepass == null) return "";
        return sharepass;
    }
    //**********************************************************
    public static final int PUBNAME = 1;
    public static final int SHAREPASS = 2;
    public static final int COSTPERUSE = 3;
    public static final int SEARCHABLE = 4;
    //**********************************************************
}
//***************************************************************************