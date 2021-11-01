package threonine.map;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class MapsLambda extends MapsLambdaFolders {
    //**********************************************************************
    /**
     * Creates a new map record.
     * @param record
     * @throws AppException OBJECTNOTFOUND
     * @throws Exception 
     */
    public void createMapRecord (MapRecord record) throws AppException, Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.FolderTree.TABLE);
        lock.addTable(DBMaps.MapRecords.TABLE);
        this.getExclusiveTableAccess(lock);
        //------------------------------------------------------------------
        if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID, record.folderid) == 0)
            throw new AppException("Parent Folder Not Found", MapErrorCodes.MAPFOLDERNOTFOUND);
        //------------------------------------------------------------------
        while (true) {
            record.recordid = Celaeno.getUniqueID();
            if (checkValueCount(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.RECORDID, record.recordid) == 0) break;
        }
        //------------------------------------------------------------------
        this.insertMapRecord(record);
        this.releaseExclusiveTableAccess();
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a map record given its ID.
     * @param recordid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecord getMapRecord (long recordid) throws AppException, Exception {
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapRecord(recordid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns map records given a folder
     * @param folderid
     * @return
     * @throws Exception 
     */
    public MapRecord[] getMapRecords (long folderid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapRecords(folderid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param recordid
     * @throws Exception 
     */
    @Override
    public void deleteMapRecord (long folderid, long recordid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.deleteMapRecord(folderid, 0);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param recordid
     * @param points
     * @param cost
     * @throws AppException
     * @throws Exception 
     */
    public void createMapObject (long recordid, PointLocation[] points, float cost) throws AppException, Exception {
        //=============================================================
        connection = electra.masterConnection();
        setDataBase();
        //=============================================================
        //We check the map record exists.
        if (checkValueCount(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.RECORDID, recordid) == 0)
            throw new AppException("Map Record not found", MapErrorCodes.MAPRECORDNOTFOUND);
        //=============================================================
        MapObject object = new MapObject();
        object.recordid = recordid;
        object.cost = cost;
        while (true) {
            object.objectid = Celaeno.getUniqueID();
            try { this.insertMapObject(object); }
            catch (SQLIntegrityConstraintViolationException e) { continue; }
            break;
        }
        //-------------------------------------------------------------
        for (PointLocation point : points) {
            point.recordid = recordid;
            point.objectid = object.objectid;
            this.insertPointLocation(point);
        }
        //=============================================================
    }
    //**********************************************************************
    /**
     * Clear map objects from a map record.
     * @param recordid
     * @throws Exception 
     */
    public void clearMapObjects (long recordid) throws Exception {
        //=============================================================
        connection = electra.masterConnection();
        setDataBase();
        //=============================================================
        this.setAutoCommit(0);
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.Objects.TABLE);
        lock.addTable(DBMaps.LocationPoints.TABLE);
        this.getExclusiveTableAccess(lock);
        //=============================================================
        this.deleteMapObjects(recordid);
        this.deletePointLocations(recordid);
        //=============================================================
        this.commitTransaction();
        this.releaseExclusiveTableAccess();
        //=============================================================
    }
    //**********************************************************************
    /**
     * 
     * @param recordid
     * @param fillpoints
     * @return
     * @throws Exception 
     */
    public MapObject[] getObjectsByRecord (long recordid, boolean fillpoints) throws Exception {
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        MapObject[] objects = this.selectMapObjects(recordid);
        if (!fillpoints) return objects;
        //------------------------------------------------------------------
        for (MapObject object : objects)
            object.points = this.selectPointLocations(object.objectid);
        //------------------------------------------------------------------
        return objects;
        //------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
