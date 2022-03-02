package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.project.ProjectLambda;
import threonine.map.MapObject;
import threonine.map.MapRecord;
import threonine.map.MappingAttlas;
import threonine.universe.SubSet;
import threonine.universe.UniverseAtlas;
//**************************************************************************
public class MapReaderGraphic {
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    ProjectLambda projectlambda = null;
    MappingAttlas mapslambda = null;
    UniverseAtlas universerlambda = null;
    public void setProjectLambda (ProjectLambda lambda) { projectlambda = lambda; }
    public void setMapsLambda (MappingAttlas mapslambda) { this.mapslambda = mapslambda; }
    public void setUniverserLambda (UniverseAtlas lambda) { universerlambda = lambda; }
    //**********************************************************************
    /**
     * 
     * @param param
     * @param userid
     * @return 
     * @throws methionine.AppException 
     */
    public MapRecordDraw[] getDrawingRecords (MapGraphicGetParam param, long userid) throws AppException, Exception {
        if (param.folderid != 0) 
            return recordsByFolder(param.folderid);
        if (param.universeid != 0)
            return recordsBySubset(param.universeid, param.subsetid);
        return new MapRecordDraw[0];
    }
    //**********************************************************************
    /**
     * Get the raw records for drawing by folder. 
     * @param folderid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordDraw[] recordsByFolder (long folderid) throws AppException, Exception {
        //------------------------------------------------------------------
        MapRecord[] records = mapslambda.getMapRecords(folderid);
        if (records.length == 0) return new MapRecordDraw[0];
        int rcount = records.length;
        MapRecordDraw[] recordsg = new MapRecordDraw[rcount];
        //------------------------------------------------------------------
        for (int n = 0; n < rcount; n++) {
            recordsg[n] = new MapRecordDraw();
            recordsg[n].itemid = records[n].getID();
            recordsg[n].setObjects(mapslambda.getObjectsByRecord(recordsg[n].itemid, true));
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
    public MapRecordDraw[] recordsBySubset (long universeid, long subsetid) throws AppException, Exception {
        //------------------------------------------------------------------
        SubSet[] subsets = universerlambda.getSubsets(universeid, subsetid);
        if (subsets.length == 0) return new MapRecordDraw[0];
        int scount = subsets.length;
        MapRecordDraw[] recordsg = new MapRecordDraw[scount];
        //------------------------------------------------------------------
        for (int n = 0; n < scount; n++) {
            recordsg[n] = new MapRecordDraw();
            recordsg[n].itemid = subsets[n].getSubsetID();
            recordsg[n].setObjects(universerlambda.getObjectsBySubset(recordsg[n].itemid, true));
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
    public MapRecordDraw getRecord (long recordid) throws AppException, Exception {
        MapRecord record = mapslambda.getMapRecord(recordid);
        return getRecord(record);
    }
    //======================================================================    
    /**
     * Gets a MapRecordDraw.Overload of previous subset.
     * @param record
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecordDraw getRecord (MapRecord record) throws AppException, Exception {
        MapRecordDraw recordg = new MapRecordDraw();
        recordg.itemid = record.getID();
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
    public MapRecordDraw subsetGetRecord (long subsetid) throws AppException, Exception {
        MapRecordDraw recordg = new MapRecordDraw();
        recordg.itemid = subsetid;
        MapObject[] mapobjs = universerlambda.getObjectsBySubset(subsetid, true);
        recordg.setObjects(mapobjs);
        return recordg;
    }
    //**********************************************************************
}
//**************************************************************************
