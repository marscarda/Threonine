package tobenamed;
//**************************************************************************
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
//**************************************************************************
public class NewClassReader {
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
    public NewClassRec[] getRecordsByFolder (long folderid) throws Exception {
        //------------------------------------------------------------------
        MapRecord[] records = mapslambda.getMapRecords(folderid);
        if (records.length == 0) return new NewClassRec[0];
        //------------------------------------------------------------------
        int rcount = records.length;
        NewClassRec[] recordsg = new NewClassRec[rcount];
        //------------------------------------------------------------------
        String[] objcodes;
        MapRecord record;
        NewClassObj obj;
        //------------------------------------------------------------------
        for (int r = 0; r < rcount; r++) {
            record = records[r];
            recordsg[r] = new NewClassRec();
            objcodes = mapslambda.getMapObjectCodes(record.getID());
            for (String objcode : objcodes) {
                obj = new NewClassObj();
                obj.setPoints(mapslambda.getObjectPoints(record.getID(), objcode));
                recordsg[r].addMapObject(obj);
            }
        }
        //------------------------------------------------------------------
        return recordsg;
        //------------------------------------------------------------------
    }
    //======================================================================
    //**********************************************************************
}
//**************************************************************************
