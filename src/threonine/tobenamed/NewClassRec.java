package threonine.tobenamed;
//**************************************************************************
public class NewClassRec {
    //**********************************************************************
    long recordid = 0;
    int objcount = 0;
    NewClassObj[] mapobjectes = null;
    //**********************************************************************
    public void addMapObject (NewClassObj object) {
        NewClassObj[] newmapobjects = new NewClassObj[objcount + 1];
        if (mapobjectes != null)
            System.arraycopy(mapobjectes, 0, newmapobjects, 0, objcount);
        newmapobjects[objcount] = object;
        mapobjectes = newmapobjects;
        objcount++;
    }
    //**********************************************************************
    public long getRecordId () { return recordid; }
    public int getObjectsCount () { return objcount; }
    public NewClassObj[] getMapObjects () {
        if (mapobjectes == null) return new NewClassObj[0];
        return mapobjectes;
    }
    //**********************************************************************
    float getSouth () {
        if (mapobjectes == null) return 90;
        float s = 90;
        for (NewClassObj obj : mapobjectes) 
            if (obj.getSouth() < s) s = obj.getSouth();
        return s;
    }
    //======================================================================
    float getNorth () {
        if (mapobjectes == null) return -90;
        float n = -90;
        for (NewClassObj obj : mapobjectes) 
            if (obj.getNorth() > n) n = obj.getNorth();
        return n;
    }
    //======================================================================
    float getWest () {
        if (mapobjectes == null) return 180;
        float w = 180;
        for (NewClassObj obj : mapobjectes) 
            if (obj.getWest() < w) w = obj.getWest();
        return w;
    }    
    //======================================================================
    float getEast () {
        if (mapobjectes == null) return -180;
        float e = -180;
        for (NewClassObj obj : mapobjectes) 
            if (obj.getEast() > e) e = obj.getEast();
        return e;
    }
    //**********************************************************************
}
//**************************************************************************
