package threonine.universe;
//**************************************************************************
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class UniverseLambda extends MapQueryInterface {
    //**********************************************************************
    //UNIVERSES
    //**********************************************************************
    /**
     * Creates a new Universe.
     * @param universe
     * @return
     * @throws methionine.AppException
     * @throws Exception 
     */
    public void createUniverse (Universe universe) throws AppException, Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //-------------------------------------------------------------------
        this.setAutoCommit(0);
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBUniverse.Universe.TABLE);
        lock.addTable(DBUniverse.DBSubSets.TABLE);
        this.getExclusiveTableAccess(lock);
        //-------------------------------------------------------------------
        //We create the universe in the database.
        while (true) {
            universe.univerid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.Universe.TABLE, DBUniverse.Universe.UNIVERSEID, universe.univerid) == 0) break;
        }
        //-------------------------------------------------------------------
        //We now create the first subset.
        SubSet subset = new SubSet();
        subset.name = universe.name;
        subset.description = universe.description;
        subset.universeid = universe.univerid;
        subset.weight = 1;
        while (true) {
            subset.subsetid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.DBSubSets.TABLE, DBUniverse.DBSubSets.SUBSETID, subset.subsetid) == 0) break;
        }
        //-------------------------------------------------------------------
        this.insertUniverse(universe);
        this.insertSubSet(subset);
        this.commitTransaction();
        this.releaseExclusiveTableAccess();
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a universe given its ID.
     * @param universeid
     * @return
     * @throws AppException
     * @throws Exception UNIVERSENOTFOUND
     */
    public Universe getUniverse (long universeid) throws AppException, Exception {
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectUniverse(universeid);
    }
    //**********************************************************************
    /**
     * Returns an array of universes.
     * @param projectid
     * @return
     * @throws Exception 
     */
    public Universe[] getUniverses (long projectid) throws Exception {
        //----------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        return this.selectUniverses(projectid);
        //----------------------------------------------------
    }
    //**********************************************************************
    /**
     * Updates a universe given its ID.
     * @param universeid
     * @param universe
     * @throws Exception 
     */
    @Override
    public void updateUniverse (long universeid, Universe universe) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //-------------------------------------------------------------------
        super.updateUniverse(universeid, universe);
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Destroy a universe and all it children subsets
     * @param universeid
     * @throws Exception 
     */
    public void destroyUniverse (long universeid) throws Exception {
        connection = electra.masterConnection();
        setDataBase();
        //-------------------------------------------------------------------
        this.startTransaction();
        try {
            this.deleteUniverse(universeid);
            this.deleteSubsetsByUniverse(universeid);
        }
        catch (Exception e) {
            this.rollbackTransaction();
            throw e;
        }
        //-------------------------------------------------------------------
        this.commitTransaction();
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Checks the user access to a given universe.
     * @param universeid
     * @param projectid
     * @return
     * @throws Exception 
     */
    public int checkUserAccessToUniverse (long universeid, long projectid) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.slaveConnection();
        setDataBase();
        Universe universe;
        try { universe = this.selectUniverse(universeid); }
        catch (AppException e) { return 0; }
        //-------------------------------------------------------------------
        if (universe.projectid  != projectid) return 0;
        //-------------------------------------------------------------------
        return 2;
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    //SUBSETS
    //**********************************************************************
    /**
     * Create a new Subset
     * @param subset
     * @throws AppException UNIVERSENOTFOUND SUBSETNOTFOUND ROOTSUBSETALREADYEXISTS
     * @throws Exception 
     */
    public void createSubSet (SubSet subset) throws AppException, Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //-------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBUniverse.Universe.TABLE);
        lock.addTable(DBUniverse.DBSubSets.TABLE);
        this.getExclusiveTableAccess(lock);
        //-------------------------------------------------------------------
        if (checkValueCount(DBUniverse.Universe.TABLE, DBUniverse.Universe.UNIVERSEID, subset.universeid) == 0) {
            this.releaseExclusiveTableAccess();
            throw new AppException("Universe not found", AppException.UNIVERSENOTFOUND);
        }
        //-------------------------------------------------------------------
        if (checkValueCount(DBUniverse.DBSubSets.TABLE, DBUniverse.DBSubSets.SUBSETID, subset.parentsubset,
            DBUniverse.DBSubSets.UNIVERSEID, subset.universeid) == 0) {
            this.releaseExclusiveTableAccess();
            throw new AppException("Parent subset not found", AppException.SUBSETNOTFOUND);
        }
        //-------------------------------------------------------------------
        while (true) {
            subset.subsetid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.DBSubSets.TABLE, DBUniverse.DBSubSets.SUBSETID, subset.subsetid) == 0) break;
        }
        //-------------------------------------------------------------------
        this.insertSubSet(subset);
        this.releaseExclusiveTableAccess();
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a subset given a universe and subset id.
     * 
     * @param universeid
     * @param subsetid
     * @return
     * @throws AppException SUBSETNOTFOUND
     * @throws Exception 
     */
    public SubSet getSubset (long universeid, long subsetid) throws AppException, Exception {
        if (subsetid == 0) return new SubSet();
        connection = electra.slaveConnection();
        this.setDataBase();
        SubSet subset = this.selectSubset(universeid, subsetid);
        subset.valid = true;
        return subset;
    }
    //**********************************************************************
    /**
     * Returns an array of subsets
     * @param universid
     * @param parentid
     * @return
     * @throws Exception 
     */
    public SubSet[] getSubsets (long universid, long parentid) throws Exception {
        connection = electra.slaveConnection();
        this.setDataBase();
        SubSet[] subsets = this.selectSubsets(universid, parentid);
        for (SubSet subset : subsets)
            subset.valid = true;
        return subsets;
    }
    //**********************************************************************
    /**
     * Sets the map record for the given subset
     * @param subsetid
     * @param recordid
     * @throws Exception 
     */
    public void setSubsetMapRecord (long subsetid, long recordid) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //-------------------------------------------------------------------
        this.updateSubsetSetMapRecord(subsetid, recordid);
        //-------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
