package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.project.ProjectLambda;
import threonine.map.MapObject;
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
import threonine.universe.SubSet;
import threonine.universe.UniverseAtlas;
//**************************************************************************
public class MapReaderGraphic {
    //**********************************************************************
    ProjectLambda projectlambda = null;
    MapsLambda mapslambda = null;
    UniverseAtlas universerlambda = null;
    public void setProjectLambda (ProjectLambda lambda) { projectlambda = lambda; }
    public void setMapsLambda (MapsLambda mapslambda) { this.mapslambda = mapslambda; }
    public void setUniverserLambda (UniverseAtlas lambda) { universerlambda = lambda; }
    //**********************************************************************
    /**
     * 
     * @param param
     * @param userid
     * @return 
     * @throws methionine.AppException 
     */
    public MapRecordGraphic[] getDrawingRecords (MapGraphicGetParam param, long userid) throws AppException, Exception {
        if (param.folderid != 0) 
            return recordsByFolder(param.folderid, userid);
        if (param.universeid != 0)
            return recordsBySubset(param.universeid, param.subsetid, userid);
        return new MapRecordGraphic[0];
    }
    //**********************************************************************
    /**
     * Get the raw records for drawing by folder. 
     * @param folderid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic[] recordsByFolder (long folderid, long userid) throws AppException, Exception {
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
    /**
     * Get the raw records for drawing by universe subset. 
     * @param universeid
     * @param subsetid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic[] recordsBySubset (long universeid, long subsetid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        SubSet[] subsets = universerlambda.getSubsets(universeid, subsetid);
        if (subsets.length == 0) return new MapRecordGraphic[0];
        int scount = subsets.length;
        MapRecordGraphic[] recordsg = new MapRecordGraphic[scount];
        //------------------------------------------------------------------
        for (int n = 0; n < scount; n++) {
            recordsg[n] = new MapRecordGraphic();
            recordsg[n].recordid = subsets[n].getSubsetID();
            recordsg[n].setObjects(universerlambda.getObjectsBySubset(recordsg[n].recordid, true));
        }
        //------------------------------------------------------------------
        return recordsg;
    }
    //**********************************************************************
    /**
     * Returns a record by its ID
     * @param recordid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic getRecord (long recordid) throws AppException, Exception {
        MapRecord record = mapslambda.getMapRecord(recordid);
        return getRecord(record);
    }
    //======================================================================    
    /**
     * Gets a MapRecordGraphic.Overload of previous subset.
     * @param record
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic getRecord (MapRecord record) throws AppException, Exception {
        MapRecordGraphic recordg = new MapRecordGraphic();
        recordg.recordid = record.getID();
        recordg.setObjects(mapslambda.getObjectsByRecord(record.getID(), true));
        return recordg;
    }
    //**********************************************************************
    /**
     * If the subset doesn't exist. It returns an empty record.
     * @param subsetid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordGraphic subsetGetRecord (long subsetid) throws AppException, Exception {
        MapRecordGraphic recordg = new MapRecordGraphic();
        recordg.recordid = subsetid;
        MapObject[] mapobjs = universerlambda.getObjectsBySubset(subsetid, true);
        recordg.setObjects(mapobjs);
        return recordg;
    }
    //**********************************************************************
}
//**************************************************************************
