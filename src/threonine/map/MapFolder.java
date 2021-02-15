package threonine.map;
//**************************************************************************
public class MapFolder {
    //======================================================================
    long folderid = 0;
    long parentid = 0;
    String name = null;
    boolean valid = false;
    //======================================================================
    public void setParentID (long parentid) { this.parentid = parentid; }
    public void setName (String name) { this.name = name; }
    //======================================================================
    public long getID () { return folderid; }
    public long getParentID () { return parentid; }
    public String getName () {
        if (name == null) return "";
        return name;
    }
    //======================================================================
    public boolean isRoot () { return folderid == 0; }
    public boolean isValid () { return valid; }
    //======================================================================
}
//**************************************************************************
