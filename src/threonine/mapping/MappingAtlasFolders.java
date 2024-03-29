package threonine.mapping;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Random;
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class MappingAtlasFolders extends LockMap {
    //**********************************************************************
    @Override
    public void updateMapFolderPublicName (long folderid, String value) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.FolderTree.TABLE);
        this.getExclusiveTableAccess(lock);
        //------------------------------------------------------------------
        //if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.PUBLICNAME, value) != 0)
        //    throw new AppException("Public name already used", MapErrorCodes.FOLDERPUBLICNAMEALREADYUSED);
        //------------------------------------------------------------------
        super.updateMapFolderPublicName(folderid, value);
        //------------------------------------------------------------------
        this.releaseExclusiveTableAccess();
        //------------------------------------------------------------------
    }
    //======================================================================
    @Override
    public void updateMapFolderSharePass (long folderid, String value) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.updateMapFolderSharePass(folderid, value);
        //------------------------------------------------------------------
    }
    //======================================================================
    @Override
    public void updateMapFolderCostPerUse (long folderid, float value) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.updateMapFolderCostPerUse(folderid, value);
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
    //**********************************************************************
    @Override
    public void deleteFolder (long folderid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.deleteFolder(folderid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param usage
     * @throws AppException
     * @throws Exception 
     */
    public void createFolderUsage (FolderUsage usage) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.FolderUsage.TABLE);
        this.getExclusiveTableAccess(lock);
        //------------------------------------------------------------------
        if (this.checkValueCount(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.PROJECTID, usage.projectid, 
                DBMaps.FolderUsage.FOLDERID, usage.folderid) != 0)
            throw new AppException("The folder is already in the project", AppException.ALREADYADDED);
        //------------------------------------------------------------------
        this.insertFolderUsage(usage);
        //------------------------------------------------------------------
        this.releaseExclusiveTableAccess();
    }
    //**********************************************************************
    /**
     * 
     * @param projectid
     * @param folderid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public FolderUsage getFolderUsage (long projectid, long folderid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectFolderUsage(projectid, folderid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @throws Exception 
     */
    @Override
    public void deleteFolderUsage (long folderid, long projectid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.deleteFolderUsage(folderid, projectid);
        //------------------------------------------------------------------
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
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        folder = this.selectMapFolder(folderid);
        return folder;
        //------------------------------------------------------------------
    }
    //======================================================================
    public MapFolder getMapFolder (String publicname) throws AppException, Exception {
        //------------------------------------------------------------------
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapFolder(publicname);
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
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
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
    
    /*
    public MapFolder[] getPublicList (String searchkey) throws Exception {
        //------------------------------------------------------------------
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        int publiccount = this.selectPublicCount();
        int offset = 0;
        if (publiccount > PUBLICCOUNT) {
            Random rand = new Random();
            offset = rand.nextInt(publiccount - PUBLICCOUNT);
        }
        //==========================================================
        return this.selectPublicFolders(searchkey, offset);
    }
    */
    //**********************************************************************
    /**
     * Returns the list of folders used in a given project.
     * @param projectid
     * @return
     * @throws Exception 
     */
    public MapFolder[] getFolderUsed (long projectid) throws Exception {
        //------------------------------------------------------------------
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectFoldersUsedInProject(projectid);
    }
    //**********************************************************************
}
//**************************************************************************