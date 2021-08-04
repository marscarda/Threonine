package threonine.map;
//**************************************************************************
import methionine.AppException;
//**************************************************************************
public class MapObjectMaker {
    /*
    //**************************************************************
    public static PointLocation[] createMapObject (String txtpoints) throws AppException, Exception {
        //=============================================================
        String[] ptrows = txtpoints.split("\\r?\\n");
        int count = ptrows.length;
        PointLocation[] points = new PointLocation[count];
        PointLocation point;
        String[] coordinates;
        //=============================================================
        for (int n = 0; n < count; n++) {
            coordinates = ptrows[n].split(",");
            point = new PointLocation();
            point.setIndex(n);
            if (coordinates.length < 2) 
                throw new AppException("Invalid format", AppException.INVALIDFORMAT);
            try {
                point.setLatitude(Float.parseFloat(coordinates[0]));
                point.setLongitude(Float.parseFloat(coordinates[1]));
            }
            catch (Exception e) {
                throw new AppException("Invalid format", AppException.INVALIDFORMAT);
            }
            points[n] = point;
        }
        //=============================================================
        return points;
        //=============================================================
    }
    //**************************************************************
    */

}
//**************************************************************************
