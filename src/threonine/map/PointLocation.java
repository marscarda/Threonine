package threonine.map;
//**************************************************************************
public class PointLocation {
    long recordid = 0;
    String objcode = null;
    int ptindex = 0;
    float latitude = 0;
    float longitude = 0;
    //==========================================================
    public void setIndex (int index) { ptindex = index; }
    public void setLatitude (float latitude) { this.latitude = latitude; }
    public void setLongitude (float longitude) { this.longitude = longitude; }
    //==========================================================
    public long getRecordID () { return recordid; }
    public int getPointIndex () { return ptindex; }
    public float getLatitude () { return latitude; }
    public float getLongitude () { return longitude; }
    public String getObjCode () {
        if (objcode == null) return "";
        return objcode;
    }
    //==========================================================
}
//**************************************************************************

