package threonine.midlayer;
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
        //------------------------------------------------------------------
        if (userid != 0) {
            MapFolder folder = mapslambda.getMapFolder(folderid);
            projectlambda.checkAccess(folder.projectID(), userid, 1);
        }
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
    public MapRecordGraphic getMapRecord (long recordid) throws AppException, Exception {
        MapRecord record = mapslambda.getMapRecord(recordid);
        return getMapRecord(record);
    }
//======================================================================    
    /**
     * Gets a MapRecordGraphic
     * @param record
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic getMapRecord (MapRecord record) throws AppException, Exception {
        MapRecordGraphic recordg = new MapRecordGraphic();
        recordg.recordid = record.getID();
        recordg.setObjects(mapslambda.getObjectsByRecord(record.getID(), true));
        return recordg;
    }
    //**********************************************************************
}
//**************************************************************************
