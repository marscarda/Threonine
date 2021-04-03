package threonine.map;
//*************************************************************************
public class MapObject {
    //*********************************************************
    long objectid = 0;
    long recordid = 0;
    int objtype = 3;
    PointLocation[] points = null;
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





