package threonine.tobenamed;
//**************************************************************************
import methionine.AppException;
import methionine.project.ProjectLambda;
import threonine.map.MapFolder;
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
//**************************************************************************
public class MapReaderGraphic {
    //**********************************************************************
    ProjectLambda projectlambda = null;
    MapsLambda mapslambda = null;
    public void setProjectLambda (ProjectLambda lambda) { projectlambda = lambda; }
    public void setMapsLambda (MapsLambda mapslambda) { this.mapslambda = mapslambda; }
    //**********************************************************************
    /**
     * 
     * @param folderid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic[] recordsByFolder (long folderid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (folderid == 0) return new MapRecordGraphic[0]; //This prevents the exception of No Project Selected.
        MapFolder folder = mapslambda.getMapFolder(folderid);
        projectlambda.checkAccess(folder.projectID(), userid, 1);
        //------------------------------------------------------------------
        MapRecord[] records = mapslambda.getMapRecords(folderid);
        if (records.length == 0) return new MapRecordGraphic[0];
        int rcount = records.length;
        MapRecordGraphic[] recordsg = new MapRecordGraphic[rcount];
        //------------------------------------------------------------------
        for (int n = 0; n < rcount; n++) {
            recordsg[n] = new MapRecordGraphic();
            recordsg[n].recordid = records[n].getID();
            recordsg[n].setObjects(mapslambda.getObjectsByRecord(recordsg[n].recordid, true));
        }
        //------------------------------------------------------------------
        return recordsg;
    }
    //**********************************************************************
    
    
    
    
    
    
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
