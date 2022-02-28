package threonine.map;
//**************************************************************************

//**************************************************************************
public class MapLayer {
    //======================================================================
    long layerid = 0;
    long projectid = 0;
    String layername = null;
    String layerdescription = null;
    //======================================================================
    public void setProjectId (long projectid) { this.projectid = projectid; }
    public void setLayerName (String layername) { this.layername = layername; }
    public void setDescription (String description) { layerdescription = description; }
    //======================================================================
    public long layerID () { return layerid; }
    public long projectID () { return projectid; }
    public String layerName () {
        if (layername == null) return "";
        return layername;
    }
    public String layerDescription () {
        if (layerdescription == null) return "";
        return layerdescription;
    }
    //======================================================================
}
//**************************************************************************
