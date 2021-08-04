package threonine.map;
//**************************************************************************
public class PointLocation {
    //==========================================================
    public long recordid = 0;
    public long objectid = 0;
    public int ptindex = 0;
    public float latitude = 0;
    public float longitude = 0;
    public int x = 0;
    public int y = 0;
    //==========================================================
    public void setIndex (int index) { ptindex = index; }
    public void setLatitude (float latitude) { this.latitude = latitude; }
    public void setLongitude (float longitude) { this.longitude = longitude; }
    //----------------------------------------------------------
    public void setPlaneLocation (int x, int y) {
        this.x = x;
        this.y = y;
    }
    //==========================================================
    public long getRecordID () { return recordid; }
    public int getPointIndex () { return ptindex; }
    //==========================================================
    public float getLatitude () { return latitude; }
    public float getLongitude () { return longitude; }
    //==========================================================
    public int getPlaneX () { return x; }
    public int getPlaneY () { return y; }
    //==========================================================
}
//**************************************************************************

