package threonine.universe;
//**************************************************************************
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class MapQueryInterface extends QueryMaps2 {
    //**********************************************************************
    /**
     * Creates a new Map Record.
     * @param record
     * @return
     * @throws AppException IDENTIFIERALREADYEXISTS
     * @throws Exception 
     */
    public long createMapRecord (MapRecord record) throws AppException, Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMapRecords.TABLE);
        this.getExclusiveTableAccess(lock);
        //------------------------------------------------------------------
        if (checkValueCount(DBMapRecords.TABLE, DBMapRecords.CATALOG, record.catalog, DBMapRecords.NAME, record.name) != 0)
            throw new AppException("Identifier already used", AppException.IDENTIFIERALREADYEXISTS);
        //------------------------------------------------------------------
        while (true) {
            record.recordid = Celaeno.getUniqueID();
            if (checkValueCount(DBMapRecords.TABLE, DBMapRecords.RECORDID, record.recordid) == 0) break;
        }
        //------------------------------------------------------------------
        this.insertMapRecord(record);
        this.releaseExclusiveTableAccess();
        //-------------------------------------------------------------------
        record.recordsincatalog = this.checkValueCount(DBMapRecords.TABLE, DBMapRecords.CATALOG, record.catalog);
        return record.recordid;
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a map record given its ID.
     * @param recordid
     * @return
     * @throws AppException MAPRECORDNOTFOUND
     * @throws Exception 
     */
    public MapRecord getMapRecord (long recordid) throws AppException, Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectMapRecord(recordid);
    }
    //**********************************************************************
    /**
     * Returns an array of MapRecords Given a catalog.
     * @param catalog
     * @param tag If null or lenght 0 this parameter is ignored.
     * @return
     * @throws Exception 
     */
    public MapRecord[] getMapRecordsByCatalog (String catalog, String tag) throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        if (tag != null && tag.length() == 0) tag = null;
        return this.selectMapRecordsByCatalog(catalog, tag);
    }
    //**********************************************************************
    /**
     * Returns an array of records starting at.
     * @param startingat
     * @return
     * @throws Exception 
     */
    public MapRecord[] getMapRecordsStartingAt (String startingat) throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectMapRecordsStartingAt(startingat);
    }
    //**********************************************************************
    /**
     * Returns the array of the catalogs.
     * @return
     * @throws Exception 
     */
    public String[] getCatalogs () throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectCatalogs();
    }
    //**********************************************************************
    /**
     * Returns record tags given a catalog.
     * @param catalog
     * @return
     * @throws Exception 
     */
    public String[] getRecordTags (String catalog) throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectTags(catalog);
    }
    //**********************************************************************
    /**
     * inserts an array of map points for maprecord id
     * @param recordid
     * @param points
     * @throws AppException
     * @throws Exception MAPRECORDNOTFOUND
     */
    public void createMapObject (long recordid, MapPoint[] points) throws AppException, Exception {
        //=============================================================
        connection = electra.masterConnection();
        setDataBase();
        //=============================================================
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMapRecords.TABLE);
        lock.addTable(DBMapObjects.TABLE);
        this.getExclusiveTableAccess(lock);
        //-------------------------------------------------------------
        if (this.checkValueCount(DBMapRecords.TABLE, DBMapRecords.RECORDID, recordid)  == 0) {
            this.releaseExclusiveTableAccess();
            throw new AppException("Map Record Not Found", AppException.MAPRECORDNOTFOUND);
        }
        //-------------------------------------------------------------
        String objcode;
        while (true) {
            objcode = Celaeno.randomString(10);
            if (checkValueCount(DBMapObjects.TABLE, DBMapObjects.OBJECTCODE, objcode) == 0) break;
        }
        //=============================================================
        this.startTransaction();
        for (MapPoint point : points) {
            point.recordid = recordid;
            point.objcode = objcode;
            this.insertMapPoint(point);
        }
        this.commitTransaction();
        //=============================================================
        this.releaseExclusiveTableAccess();
        //=============================================================
    }
    //**********************************************************************
    /**
     * Returns the number of objects given a record id
     * @param recordid
     * @return
     * @throws Exception 
     */
    public int getMapObjectsCount (long recordid) throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectMapObjectsCount(recordid);
    }
    //**********************************************************************
    /**
     * Returns the object codes for a given record id
     * @param recordid
     * @return
     * @throws Exception 
     */
    public String[] getMapObjectCodes (long recordid) throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectMapObjectCodes(recordid);
    }
    //**********************************************************************
    /**
     * Returns the array of points given a record id and name
     * @param recordid If not 0 includes the id in the where clause
     * @param code if not null includes the code in the where clause
     * @return
     * @throws Exception 
     */
    public MapPoint[] getObjectPoints (long recordid, String code) throws Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectMapPoints(recordid, code);
    }
    //**********************************************************************
    /**
     * Destroys a map record given its ID.
     * @param recordid
     * @throws Exception 
     */
    public void destroyMapRecord (long recordid) throws Exception {
        //=============================================================
        connection = electra.masterConnection();
        setDataBase();
        //=============================================================
        this.startTransaction();
        this.deleteMapRecord(recordid);
        this.deleteMapPoints(recordid);
        this.commitTransaction();
        //=============================================================
    }
    //**********************************************************************
    public void clearMapObjects (long maprecordid) throws Exception {
        //=============================================================
        connection = electra.masterConnection();
        setDataBase();
        //=============================================================
        this.deleteMapPoints(maprecordid);
        //=============================================================
    }
    //**********************************************************************
}
//**************************************************************************

