package threonine.universe;
//**************************************************************************
public class MapRecord {
    //======================================================================
    long recordid = 0;
    String catalog = null;
    String tag = null;
    String name = null;
    //----------------------------------------------------------------------
    int recordsincatalog = 0;
    //======================================================================
    public long getID () { return recordid; }
    //----------------------------------------------------------------------
    public void setCatalog (String catalog) { this.catalog = catalog; }
    public void setTag (String tag) { this.tag = tag; }
    public void setName (String name) { this.name = name; }
    //----------------------------------------------------------------------
    public String getCatalog () {
        if (catalog == null) return "";
        return catalog;
    }
    //----------------------------------------------------------------------
    public String getTag () {
        if (tag == null) return "";
        return tag;
    }
    //----------------------------------------------------------------------
    public String getName () {
        if (name == null) return "";
        return name;
    }
    //======================================================================
    public int recsInCatalogs () { return recordsincatalog; }
    //======================================================================
}
//**************************************************************************
