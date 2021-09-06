package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import threonine.map.MapErrorCodes;
import threonine.map.PointLocation;
//**************************************************************************
public class MapValidationAndMath {
    //**********************************************************************
    static final int EARTH_RADIUS_M = 6371000;
    //**********************************************************************
    public static PointLocation[] createPoints (String txtpoints) throws AppException {
        //=============================================================
        String[] ptrows = txtpoints.split("\\r?\\n");
        int count = ptrows.length;
        PointLocation[] points = new PointLocation[count];
        String[] values;
        //=============================================================
        for (int n = 0; n < count; n++) {
            values = ptrows[n].split(",");
            if (values.length < 2) 
                throw new AppException("Invalid format", AppException.INVALIDFORMAT);
            points[n] = new PointLocation();
            points[n].recordid = count;
            points[n].ptindex = n;
            try {
                points[n].latitude = Float.parseFloat(values[0]);
                points[n].longitude = Float.parseFloat(values[1]);
            }
            catch (Exception e) {
                throw new AppException("Invalid format", AppException.INVALIDFORMAT);
            }
        }        
        //=============================================================
        return points;
        //=============================================================
    }
    //**********************************************************************
    /**
     * Checks the validity of the object
     * @param ponts
     * @throws AppException 
     */
    public static void checkValid (PointLocation[] ponts) throws AppException {
        MapRatioInfo ratio = checkPointDistRatio(ponts);
        if (!ratio.aproved()) {
            throw new AppException("To many points", MapErrorCodes.MAXALLOWEDPOINTSEXCEDED);
        }
    }
    //======================================================================
    public static MapRatioInfo checkPointDistRatio (PointLocation[] points) {
        //======================================================
        int ptcount = points.length;
        //------------------------------------------------------
        //We won't invalidate an object with so few points
        if (ptcount < 100) return new MapRatioInfo();
        //------------------------------------------------------
        //We calculate the total perimeter of the area.
        int itr = ptcount - 1;
        int perimeter = 0;
        PointLocation pt1, pt2;
        for (int n = 0; n < itr; n++) {
            pt1 = points[n];
            pt2 = points[n + 1];
            perimeter += harvesineDistance(pt1, pt2);
        }
        //======================================================
        //Algorithm to determine min separation of the points
        double propconst = 0.00025;
        int minadd = 50;
        int kmts = (int)(perimeter / 1000);
        for (int n = 0; n < kmts; n += 20) minadd++;
        for (int n = 0; n < kmts; n += 300) propconst *= 0.99;
        int pteverymt = (int)(perimeter * propconst + minadd);
        //======================================================
        //Allowed is the number of points allowed for this object.
        int allowedpts = (int)(perimeter / pteverymt);
        if (ptcount > allowedpts) {
            MapRatioInfo ratioinf = new MapRatioInfo();
            ratioinf.perimeter = perimeter;
            ratioinf.separation = pteverymt;
            ratioinf.ptallowed = allowedpts;
            ratioinf.ptobject = ptcount;
            return ratioinf;
        }
        //======================================================
        return new MapRatioInfo();
        //======================================================
    }
    //**********************************************************************
    public static int harvesineDistance (PointLocation pt1, PointLocation pt2) {
        //-------------------------------------------------
        double lat1 = Math.toRadians(pt1.latitude);
        double lon1 = Math.toRadians(pt1.longitude);
        double lat2 = Math.toRadians(pt2.latitude);
        double lon2 = Math.toRadians(pt2.longitude);
        double deltalat = lat2 - lat1;
        double deltalon = lon2 - lon1;
        //-------------------------------------------------
        double term1 = Math.sin(deltalat / 2) * Math.sin(deltalat / 2);
        double term2 = Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltalon/2) * Math.sin(deltalon/2);
        double termsum = term1 + term2;
        double angdistance = 2 * Math.atan2(Math.sqrt(termsum), Math.sqrt(1 - termsum));
        return (int)((double)EARTH_RADIUS_M * angdistance); // in metres
    }
    //**********************************************************************
}
//**************************************************************************
