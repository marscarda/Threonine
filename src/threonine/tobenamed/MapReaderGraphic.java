package threonine.tobenamed;
//**************************************************************************
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
//**************************************************************************
public class MapReaderGraphic {
    //**********************************************************************
    MapsLambda mapslambda = null;
    public void setMapsLambda (MapsLambda mapslambda) { this.mapslambda = mapslambda; }
    //**********************************************************************
    /**
     * Selects and returns an array of objects given their catalog.
     * @param folderid
     * @return
     * @throws Exception 
     */
    
    
    
    /*
    public MapRecordGraphic[] getRecordsByFolder (long folderid) throws Exception {
        //------------------------------------------------------------------
        MapRecord[] records = mapslambda.getMapRecords(folderid);
        if (records.length == 0) return new MapRecordGraphic[0];
        //------------------------------------------------------------------
        int rcount = records.length;
        MapRecordGraphic[] recordsg = new MapRecordGraphic[rcount];
        //------------------------------------------------------------------
        String[] objcodes;
        MapRecord record;
        MapObjectGraphic obj;
        //------------------------------------------------------------------
        for (int r = 0; r < rcount; r++) {
            record = records[r];
            recordsg[r] = new MapRecordGraphic();
            objcodes = mapslambda.getMapObjectCodes(record.getID());
            for (String objcode : objcodes) {
                obj = new MapObjectGraphic();
                obj.setPoints(mapslambda.getObjectPoints(record.getID(), objcode));
                recordsg[r].addMapObject(obj);
            }
        }
        //------------------------------------------------------------------
        return recordsg;
        //------------------------------------------------------------------
    }
    */
    //======================================================================
    //**********************************************************************
}
//**************************************************************************
