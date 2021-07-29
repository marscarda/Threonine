package threonine.map;
//**************************************************************************
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
            throw new AppException("Parent Folder Not Found", AppException.OBJECTNOTFOUND);
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
    public void createMapObject (long recordid, PointAdd[] points) throws AppException, Exception {
        //=============================================================
        connection = electra.masterConnection();
        setDataBase();
        //=============================================================
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.MapRecords.TABLE);
        lock.addTable(DBMaps.Objects.TABLE);
        lock.addTable(DBMaps.LocationPoints.TABLE);
        this.getExclusiveTableAccess(lock);
        //-------------------------------------------------------------
        MapObject object = new MapObject();
        object.recordid = recordid;
        while (true) {
            object.objectid = Celaeno.getUniqueID();
            if (checkValueCount(DBMaps.Objects.TABLE, DBMaps.Objects.OBJECTID, object.objectid) == 0) break;
        }
        //=============================================================
        this.startTransaction();
        this.insertMapObject(object);
        for (PointAdd point : points) {
            point.recordid = recordid;
            point.objectid = object.objectid;
            this.insertPointLocation(point);
        }
        this.commitTransaction();
        //=============================================================
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
    public static PointAdd[] createPoints (String txtpoints, long recordid) throws AppException {
        //=============================================================
        String[] ptrows = txtpoints.split("\\r?\\n");
        int count = ptrows.length;
        PointAdd[] points = new PointAdd[count];
        String[] values;
        //=============================================================
        for (int n = 0; n < count; n++) {
            values = ptrows[n].split(",");
            if (values.length < 2) 
                throw new AppException("Invalid format", AppException.INVALIDFORMAT);
            points[n] = new PointAdd();
            points[n].recordid = count;
            points[n].index = n;
            try {
                points[n].latitude = Float.parseFloat(values[0]);
                points[n].longitude = Float.parseFloat(values[1]);
            }
            catch (Exception e) {
                throw new AppException("Invalid format", AppException.INVALIDFORMAT);
            }
        }        
        //=============================================================
        return points;
        //=============================================================
    }
    //**********************************************************************
}
//**************************************************************************
