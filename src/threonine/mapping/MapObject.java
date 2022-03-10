package threonine.mapping;
//*************************************************************************
public class MapObject {
    //*********************************************************
    public long objectid = 0;
    public long recordid = 0;
    public int objtype = 3;
    public PointLocation[] points = null;
    public float cost = 0;
    //=========================================================
    public void setRecordID (long recordid) { this.recordid = recordid; }
    //=========================================================
    public long recordID () { return recordid; }
    public long objectID () { return objectid; }
    public int objectType () { return objtype; }
    //---------------------------------------------------------
    public PointLocation[] getPointLocations () {
        if (points == null) return new PointLocation[0];
        return points;
    }
    //*********************************************************
    public static final int OBJTYPEREGION = 3;
    //*********************************************************
}
//*************************************************************************





