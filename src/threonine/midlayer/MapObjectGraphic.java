package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import threonine.map.PointLocation;
//**************************************************************************
public class MapObjectGraphic {
    //**********************************************************************
    int ptcount = 0;
    PointLocation[] mappoints = null;
    long objectid = 0;
    int objecttype = 3;
    //**********************************************************************
    void setPoints (PointLocation[] points) {
        this.mappoints = points;
        ptcount = this.mappoints.length;
    }
    //**********************************************************************
    public int obectType () { return objecttype; }
    public int pointsCount () { return ptcount; }
    public PointLocation[] getPoints () throws AppException {
        //------------------------------------------------------------------
        if (mappoints == null) 
            throw new AppException("Invalid read. Not a map object to return", AppException.INVALIDMETHODREAD);
        if (ptcount < 2)
            throw new AppException("Invalid read. Not a polygon but a point", AppException.INVALIDMETHODREAD);
        return mappoints;
        //------------------------------------------------------------------
    }
    //**********************************************************************
    public float getSouth () {
        if (mappoints == null) return 0;
        float s = 90;
        for (PointLocation pt : mappoints) 
            if (pt.getLatitude() < s) s = pt.getLatitude();
        return s;
    }
    //======================================================================
    public float getNorth () {
        if (mappoints == null) return 0;
        float n = -90;
        for (PointLocation pt : mappoints) 
            if (pt.getLatitude() > n) n = pt.getLatitude();
        return n;
    }
    //======================================================================
    public float getWest () {
        if (mappoints == null) return 0;
        float w = 180;
        for (PointLocation pt : mappoints) 
            if (pt.getLongitude() < w) w = pt.getLongitude();
        return w;
    }
    //======================================================================
    public float getEast () {
        if (mappoints == null) return 0;
        float e = -180;
        for (PointLocation pt : mappoints) 
            if (pt.getLongitude() > e) e = pt.getLongitude();
        return e;
    }
    //**********************************************************************
}
//**************************************************************************
