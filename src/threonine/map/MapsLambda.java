package threonine.map;
//**************************************************************************
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class MapsLambda extends QueryMaps2 {
    //**********************************************************************
    /**
     * Creates a new map folder
     * @param folder
     * @throws AppException IDENTIFIERALREADYEXISTS, OBJECTNOTFOUND
     * @throws Exception 
     */
    public void createFolder (MapFolder folder) throws AppException, Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.FolderTree.TABLE);
        this.getExclusiveTableAccess(lock);
        //------------------------------------------------------------------
        if (folder.parentid != 0) {
            if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID, folder.parentid) == 0)
                throw new AppException("Parent Folder Not Found", AppException.OBJECTNOTFOUND);
            if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERNAME, folder.name, 
                DBMaps.FolderTree.PARENTFOLDER, folder.parentid) != 0)
                    throw new AppException("Folder Name already exists", AppException.IDENTIFIERALREADYEXISTS);
        } else {
            if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERNAME, folder.name, 
                DBMaps.FolderTree.PROJECTID, folder.projectid) != 0)
                    throw new AppException("Folder Name already exists", AppException.IDENTIFIERALREADYEXISTS);
        }
        //------------------------------------------------------------------
        if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.PUBLICNAME, folder.publicname) != 0)
            throw new AppException("Share ID name already used", AppException.IDENTIFIERALREADYEXISTS);
        //------------------------------------------------------------------
        while (true) {
            folder.folderid = Celaeno.getUniqueID();
            if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID, folder.folderid) == 0) break;
        }
        //--------------------------------------------------------------
        this.insertMapFolder(folder);
        this.releaseExclusiveTableAccess();
        //--------------------------------------------------------------
    }
    //**********************************************************************
    @Override
    public void updateMapFolderPublicName (long folderid, String value) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.updateMapFolderPublicName(folderid, value);
        //------------------------------------------------------------------
    }
    //======================================================================
    public void updateSearchable (long folderid, int value) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.updateMapFolderSearchable(folderid, value);
        //------------------------------------------------------------------
    }
    //======================================================================
    
    
    
    
    //**********************************************************************
    /**
     * Returns a map folder given its ID.
     * @param folderid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapFolder getMapFolder (long folderid) throws AppException, Exception {
        //------------------------------------------------------------------
        MapFolder folder;
        //------------------------------------------------------------------
        if (folderid == 0) {
            folder = new MapFolder();
            folder.valid = true;
            return folder;
        }
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        folder = this.selectMapFolder(folderid);
        return folder;
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a list of folders given their parent.
     * @param projectid
     * @param parentid
     * @return
     * @throws Exception 
     */
    public MapFolder[] getChildrenFolders (long projectid, long parentid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapFolders(projectid, parentid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a list of folders given a key search
     * @param searchkey
     * @return
     * @throws Exception 
     */
    public MapFolder[] searchFoldersByShareID (String searchkey) throws Exception {
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapFoldersByShareID(searchkey);
    }
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
