package threonine.map;
//**************************************************************************
public class PointLocation {
    //==========================================================
    long recordid = 0;
    String objcode = null;
    int ptindex = 0;
    float latitude = 0;
    float longitude = 0;
    int x = 0;
    int y = 0;
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
    public String getObjCode () {
        if (objcode == null) return "";
        return objcode;
    }
    //==========================================================
    public float getLatitude () { return latitude; }
    public float getLongitude () { return longitude; }
    //==========================================================
    public int getPlaneX () { return x; }
    public int getPlaneY () { return y; }
    //==========================================================
}
//**************************************************************************

