package threonine.midlayer;
//**************************************************************************
import threonine.map.MapObject;
//**************************************************************************
public class MapRecordGraphic {
    //**********************************************************************
    long recordid = 0;
    int objcount = 0;
    MapObjectGraphic[] mapobjects = null;
    //**********************************************************************
    public void setObjects (MapObjectGraphic[] objects) { mapobjects = objects; }
    //======================================================================
    public void setObjects (MapObject[] objects) {
        objcount = objects.length;
        mapobjects = new MapObjectGraphic[objcount];
        for (int n = 0; n < objcount; n++) {
            mapobjects[n] = new MapObjectGraphic();
            mapobjects[n].objectid = objects[n].objectID();
            mapobjects[n].objecttype = objects[n].objectType();
            mapobjects[n].setPoints(objects[n].getPointLocations());
        }
    }
    //**********************************************************************
    public void addMapObject (MapObjectGraphic object) {
        MapObjectGraphic[] newmapobjects = new MapObjectGraphic[objcount + 1];
        if (mapobjects != null)
            System.arraycopy(mapobjects, 0, newmapobjects, 0, objcount);
        newmapobjects[objcount] = object;
        mapobjects = newmapobjects;
        objcount++;
    }
    //**********************************************************************
    public long getRecordId () { return recordid; }
    public int getObjectsCount () { return objcount; }
    public MapObjectGraphic[] getMapObjects () {
        if (mapobjects == null) return new MapObjectGraphic[0];
        return mapobjects;
    }
    //**********************************************************************
    float getSouth () {
        if (mapobjects == null) return 90;
        float s = 90;
        for (MapObjectGraphic obj : mapobjects) 
            if (obj.getSouth() < s) s = obj.getSouth();
        return s;
    }
    //======================================================================
    float getNorth () {
        if (mapobjects == null) return -90;
        float n = -90;
        for (MapObjectGraphic obj : mapobjects) 
            if (obj.getNorth() > n) n = obj.getNorth();
        return n;
    }
    //======================================================================
    float getWest () {
        if (mapobjects == null) return 180;
        float w = 180;
        for (MapObjectGraphic obj : mapobjects) 
            if (obj.getWest() < w) w = obj.getWest();
        return w;
    }    
    //======================================================================
    float getEast () {
        if (mapobjects == null) return -180;
        float e = -180;
        for (MapObjectGraphic obj : mapobjects) 
            if (obj.getEast() > e) e = obj.getEast();
        return e;
    }
    //**********************************************************************
}
//**************************************************************************
