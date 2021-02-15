package threonine.map;
//**************************************************************************
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class MapsLambda extends QueryMaps1 {
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
        }
        //------------------------------------------------------------------
        if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERNAME, folder.name, 
            DBMaps.FolderTree.PARENTFOLDER, folder.parentid) != 0)
                throw new AppException("Folder Name already exists", AppException.IDENTIFIERALREADYEXISTS);
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
     * @param parentid
     * @return
     * @throws Exception 
     */
    public MapFolder[] getChildrenFolders (long parentid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapFolders(parentid);
        //------------------------------------------------------------------
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
    //**********************************************************************
    /**
     * Returns map records given a folder
     * @param folderid
     * @return
     * @throws Exception 
     */
    public MapRecord[] getMapRecords (long folderid) throws Exception {
        //------------------------------------------------------------------
        if (folderid == 0) return new MapRecord[0];
        //------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapRecords(folderid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
