package threonine.midlayer;
//**************************************************************************
import threonine.map.PointLocation;
//**************************************************************************
public class MapGeoQuery extends MapView {
    //************************************************************
    float targetlatitude = 0;
    float targetlongitude = 0;
    int intersections = 0;
    long[] pointinrecords = new long[0];
    //************************************************************
    /**
     * Find records containing the cartesian point. 
     * @param ptx
     * @param pty 
     */
    public void findPointInRecord (int ptx, int pty) {
        //===================================================
        //Make sure no record ID is included.
        pointinrecords = new long[0];
        //===================================================
        this.calculateLocationFromPlane(ptx, pty);
        MapObjectGraphic[] objects;
        //===================================================
        for (MapRecordGraphic record : records) {
            //===============================================
            intersections = 0;
            objects = record.getMapObjects();
            //===============================================
            for (MapObjectGraphic mobj : objects) {
                if (!inContainerRect(mobj)) continue;
                inObject(mobj);
            }            
            //===============================================
            int inside = intersections % 2;
            //===============================================
            if (inside != 0)
                addRecordID(record.itemid);
            //===============================================
        }
    }
    //************************************************************
    /**
     * Returns the record IDs that contains the point tested.
     * @return 
     */
    public long[] affectedIDs () { return pointinrecords; }
    //************************************************************
    /**
     * Calculates the geo location from a given cartesian coordinates.
     * @param refx
     * @param refy 
     */
    private void calculateLocationFromPlane (int refx, int refy) {
        //========================================================
        float frompole = (float)(topcanvasy + refy) / (float)scale;
        targetlatitude = 90 - frompole;
        float auxlon = (float)((refx - halfcanvasx) / Math.cos(Math.toRadians(targetlatitude)));
        targetlongitude = clongitude + (auxlon / (float)scale);
        //========================================================
    }
    //************************************************************
    /**
     * Tests if the point is in the quadrant of the object.
     * @param object
     * @return 
     */
    private boolean inContainerRect (MapObjectGraphic object) {
        if (targetlatitude < object.getSouth()) return false;
        if (targetlatitude > object.getNorth()) return false;
        if (targetlongitude < object.getWest()) return false;
        if (targetlongitude > object.getEast()) return false;
        return true;
    }
    //************************************************************
    /**
     * Tests if the given point is contained in the geographic object.
     * @param object 
     */
    private void inObject (MapObjectGraphic object) {
        
        //********************************************************
        int loop = object.pointsCount() - 1;
        //-----------------------------------------------------
        //Used to find if target lat is in the range of a segment.
        float minlat, maxlat; 
        //=====================================================
        PointLocation[] points;
        try { points = object.getPoints(); }
        catch (Exception e) { return; }
        PointLocation pt1;
        PointLocation pt2;
        //********************************************************
        for (int n = 0; n < loop; n++) {
            
            //****************************************************
            pt1 = points[n];
            pt2 = points[n + 1];
            //****************************************************
            //The simplest test
            //----------------------------------------------------
            //Is the point in the latitude range of the segment.
            //If not we discard it.
            minlat = Math.min(pt1.latitude, pt2.latitude);
            maxlat = Math.max(pt1.latitude, pt2.latitude);
            if (targetlatitude < minlat) continue;
            if (targetlatitude > maxlat) continue;
            //----------------------------------------------------
            //Is the point at the east of initial and final points of the segment
            //If so we discard it.
            if (targetlongitude > pt1.longitude) continue;
            if (targetlongitude > pt2.longitude) continue;
            //----------------------------------------------------
            //Is the point at the west of initial and final points of the segment
            //If so we count as an intersection and no further test.
            if (targetlongitude < pt1.longitude && targetlongitude < pt2.longitude) {
               intersections++;
                continue;
            }
            //****************************************************
            //We get here because the testing point longitude is
            //between the initial and final point of the segment.
            //****************************************************
            //We check if the point is at the west of the segment.
            float difflat = pt2.latitude - pt1.latitude;
            float difflon = pt2.longitude - pt1.longitude;
            float loncoeffitient = difflon / difflat;
            float frominitiallat = targetlatitude - pt1.latitude;
            float reflon = frominitiallat * loncoeffitient + pt1.longitude;
            //-------------------------------------------------
            if (targetlongitude <= reflon) intersections++;
            //****************************************************

        }
        //********************************************************

    }
    //************************************************************
    private void addRecordID (long recoirdid) {
        int count = pointinrecords.length;
        long[] newarray = new long[count + 1];
        System.arraycopy(pointinrecords, 0, newarray, 0, count);
        newarray[count] = recoirdid;
        pointinrecords = newarray;
    }
    //************************************************************
}
//**************************************************************************
