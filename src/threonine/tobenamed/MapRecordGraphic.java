package threonine.tobenamed;
//**************************************************************************
public class MapRecordGraphic {
    //**********************************************************************
    long recordid = 0;
    int objcount = 0;
    MapObjectGraphic[] mapobjectes = null;
    //**********************************************************************
    public void addMapObject (MapObjectGraphic object) {
        MapObjectGraphic[] newmapobjects = new MapObjectGraphic[objcount + 1];
        if (mapobjectes != null)
            System.arraycopy(mapobjectes, 0, newmapobjects, 0, objcount);
        newmapobjects[objcount] = object;
        mapobjectes = newmapobjects;
        objcount++;
    }
    //**********************************************************************
    public long getRecordId () { return recordid; }
    public int getObjectsCount () { return objcount; }
    public MapObjectGraphic[] getMapObjects () {
        if (mapobjectes == null) return new MapObjectGraphic[0];
        return mapobjectes;
    }
    //**********************************************************************
    float getSouth () {
        if (mapobjectes == null) return 90;
        float s = 90;
        for (MapObjectGraphic obj : mapobjectes) 
            if (obj.getSouth() < s) s = obj.getSouth();
        return s;
    }
    //======================================================================
    float getNorth () {
        if (mapobjectes == null) return -90;
        float n = -90;
        for (MapObjectGraphic obj : mapobjectes) 
            if (obj.getNorth() > n) n = obj.getNorth();
        return n;
    }
    //======================================================================
    float getWest () {
        if (mapobjectes == null) return 180;
        float w = 180;
        for (MapObjectGraphic obj : mapobjectes) 
            if (obj.getWest() < w) w = obj.getWest();
        return w;
    }    
    //======================================================================
    float getEast () {
        if (mapobjectes == null) return -180;
        float e = -180;
        for (MapObjectGraphic obj : mapobjectes) 
            if (obj.getEast() > e) e = obj.getEast();
        return e;
    }
    //**********************************************************************
}
//**************************************************************************
