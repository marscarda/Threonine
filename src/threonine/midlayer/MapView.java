package threonine.midlayer;
//**************************************************************************
import threonine.mapping.PointLocation;
//**************************************************************************
public class MapView {
    //************************************************************
    int width = 0;
    int height = 0;
    int scale = 0;
    int halfcanvasx = 0;
    int topcanvasy = 0;
    float clatitude = 0;
    float clongitude = 0;
    float south = 90;
    float north = -90;
    float west = 180;
    float east = -180;
    //************************************************************
    int recount = 0;
    MapRecordDraw[] records = null;
    //============================================================
    public void addRecord (MapRecordDraw record) {
        MapRecordDraw[] newrecords = new MapRecordDraw[recount + 1];
        if (records != null)
            System.arraycopy(records, 0, newrecords, 0, recount);
        newrecords[recount] = record;
        records = newrecords;
        recount++;
    }
    //============================================================
    public void setRecords (MapRecordDraw[] records) { 
        this.records = records; 
        recount = records.length;
    }
    //************************************************************
    /**
     * Used to set the canvas dimensions.
     * @param width
     * @param height 
     */
    public void setCanvasDimentions (int width, int height) {
        this.width = width;
        this.height = height;
    }
    //============================================================
    /**
     * To set the scale.
     * @param scale 
     */
    public void setScale (float scale) { this.scale = Math.round(scale); }
    public void setScale (int scale) { this.scale = scale; }
    public int getScale () { return scale; }
    //============================================================
    /**
     * Used for setting the map center.
     * If latitude is bigger than 90 the flag centered will be
     * set to false and the View will attempt to calculate it.
     * @param latitude If > 90 it is concedered not centered.
     * @param longitude 
     */
    public void setCenter (float latitude, float longitude) {
        clatitude = latitude;
        clongitude = longitude;
    }
    public float getCenterLatitude () { return clatitude; }
    public float getCenterLongitude () { return clongitude; }
    //============================================================
    /**
     * Calculates the geo location from a given cartesian coordinates and centers the map.
     * @param refx
     * @param refy 
     */
    private void calculateNewCenterFromPlaneCoords (int refx, int refy) {
        //========================================================
        float frompole = (float)(topcanvasy + refy) / (float)scale;
        clatitude = 90 - frompole;
        float auxlon = (float)((refx - halfcanvasx) / Math.cos(Math.toRadians(clatitude)));
        clongitude += (auxlon / scale);
        //========================================================
    }
    //============================================================
    /**
     * Calculates the center geo location.
     * @param downx
     * @param downy
     * @param upx
     * @param upy 
     */
    public void calculateDisplacement (int downx, int downy, int upx, int upy) {
        //========================================================
        //Mouse down geographic coordinates.
        float downlat = 90 - ((float)(topcanvasy + downy) / (float)scale);
        float auxlon = (float)((downx - halfcanvasx) / Math.cos(Math.toRadians(clatitude)));
        float downlon = clongitude + (auxlon / scale);
        //========================================================
        clatitude += (float)(upy - downy) / (float)scale;
        //--------------------------------------------------------
        int difx = upx - halfcanvasx;
        float diflon = (float)difx / (float)scale / (float)Math.cos(Math.toRadians(downlat));
        clongitude = downlon - diflon;
        //========================================================
    }
    //============================================================
    public void changeZoom (float scalefactor, int eventx, int eventy) {
        calculateNewCenterFromPlaneCoords(eventx, eventy);
        scale = (int)((float)scale * scalefactor);
        if (scale < 2) scale = 2;
    }
    //************************************************************
    public int recordsCount () { return recount; }
    //============================================================
    public MapRecordDraw[] getRecords () {
        if (records == null) return new MapRecordDraw[0];
        return records;
    }
    //************************************************************
    public void initialize () {
        this.calculateGeoMargins();
        this.calculateCenter();
        this.calculateScale();
        this.calculateCanvasTop();
    }    
    //************************************************************
    public void makeDraw () {
        MapObjectGraphic[] objects;
        PointLocation[] points;
        for (MapRecordDraw record : records) {
            objects = record.getMapObjects();
            //===============================================
            for (MapObjectGraphic mobj : objects) {
                try {
                    points = mobj.getPoints();
                    for (PointLocation point: points)
                        this.getMapPlanePoint(point);
                }
                catch (Exception e) {}
            }            
            //===============================================
        }
    }
    //============================================================
    /**
     * Calculates the cartesian point for a given geographic
     * coordinate in the current view.
     * @param latitude
     * @param longitude
     * @return 
     */
    void getMapPlanePoint (PointLocation mappoint) {
        //========================================================
        //DrawPoint pt = new DrawPoint();
        //========================================================
        float latitude = mappoint.getLatitude();
        float longitude = mappoint.getLongitude();
        int x, y;
        //--------------------------------------------------------
        x = (int)((longitude - clongitude) * scale);
        x = (int)(x * Math.cos(Math.toRadians(latitude))) + halfcanvasx;
        //--------------------------------------------------------
        y = (int)((90 - latitude) * scale);
        y = y - topcanvasy;
        //========================================================
        mappoint.setPlaneLocation(x, y);
        //========================================================
    }
    //************************************************************
    //AUTO CALCULATIONS.
    //************************************************************
    /**
     * Calculates the geographic margins of the loaded objects.
     * @return 
     */
    private boolean calculateGeoMargins () {
        //---------------------------------------------
        if (records == null) return false;
        //---------------------------------------------
        if (north != -90) return false;
        if (south != 90) return false;
        if (west != 180) return false;
        if (east != -180) return false;
        //---------------------------------------------
        for (MapRecordDraw record : records) {
            if (record.getSouth() < south) south = record.getSouth();
            if (record.getNorth() > north) north = record.getNorth();
            if (record.getWest() < west) west = record.getWest();
            if (record.getEast() > east) east = record.getEast();
        }
        //---------------------------------------------
        return true;
        //---------------------------------------------
    }
    //============================================================
    /**
     * Tries to calculate the scale if it is 0
     * @return 
     */
    private boolean calculateScale () {
        if (scale != 0) return false;
        if (width == 0) return false;
        if (height == 0) return false;
        int canvasdim;
        float geodif;
        if (width < height) { 
            canvasdim = width;
            geodif = east - west;
        }
        else { 
            canvasdim = height;
            geodif = north - south;
        }
        if (north == -90) scale = 0;
        else scale = (int)(canvasdim / geodif);
        return true;
    }
    //============================================================
    /**
     * Calculates the geographic center of the map view.
     * @return 
     */
    private boolean calculateCenter () {
        //---------------------------------------------
        if (scale != 0) return false;
        //---------------------------------------------
        if (north == -90) return false;
        if (south == 90) return false;
        if (west == 180) return false;
        if (east == -180) return false;
        //---------------------------------------------
        clatitude = south + ((north - south) / 2);
        clongitude = west + ((east - west) / 2);
        //---------------------------------------------
        return true;
    }
    //============================================================
    /**
     * Calculates the drawing offset for axis X & Y
     * @return 
     */
    private boolean calculateCanvasTop () {
        //========================================================
        int distance;
        int half;
        //========================================================
        //Half canvas x
        this.halfcanvasx = width / 2;
        //--------------------------------------------------------
        //top Y
        float fromnpole = 90 - clatitude;
        distance = (int)(fromnpole * scale);
        half = height / 2;
        topcanvasy = distance - half;
        //========================================================
        return true;
        //========================================================
    }
    //************************************************************
}
//**************************************************************************
