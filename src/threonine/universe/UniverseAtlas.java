package threonine.universe;
//**************************************************************************
import methionine.AppException;
import methionine.Celaeno;
import threonine.map.MapObject;
import threonine.map.PointLocation;
//**************************************************************************
public class UniverseAtlas extends UniverseLock {
    //**********************************************************************
    //UNIVERSES
    //**********************************************************************
    /**
     * Creates a new Universe.
     * @param universe
     * @param subset
     * @throws methionine.AppException
     * @throws Exception 
     */
    public void createUniverse (Universe universe, SubSet subset) throws AppException, Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //-------------------------------------------------------------------
        //We create the universe in the database.
        while (true) {
            universe.univerid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.Universe.TABLE, DBUniverse.Universe.UNIVERSEID, universe.univerid) == 0) break;
        }
        //-------------------------------------------------------------------
        //We now create the first subset.
        //SubSet subset = new SubSet();
        subset.name = universe.name;
        subset.description = universe.description;
        subset.universeid = universe.univerid;
        subset.weight = 1;
        while (true) {
            subset.subsetid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subset.subsetid) == 0) break;
        }
        //-------------------------------------------------------------------
        this.insertUniverse(universe);
        this.insertSubSet(subset);
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
    //** SUBSETS **
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
        if (checkValueCount(DBUniverse.Universe.TABLE, DBUniverse.Universe.UNIVERSEID, subset.universeid) == 0) {
            throw new AppException("Universe not found", AppException.UNIVERSENOTFOUND);
        }
        //-------------------------------------------------------------------
        if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subset.parentsubset,
            DBUniverse.SubSets.UNIVERSEID, subset.universeid) == 0) {
            throw new AppException("Parent subset not found", UniverseErrorCodes.SUBSETNOTFOUND);
        }
        //-------------------------------------------------------------------
        while (true) {
            subset.subsetid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subset.subsetid) == 0) break;
        }
        //-------------------------------------------------------------------
        this.insertSubSet(subset);
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns the root subset given a universe.
     * @param universeid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public SubSet getTopSubset (long universeid) throws AppException, Exception {
        //-------------------------------------------------------------------
        connection = electra.slaveConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
        SubSet subset = this.selectTopSubset(universeid);
        subset.valid = true;
        return subset;
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
        //-------------------------------------------------------------------
        connection = electra.slaveConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
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
    public void setSubsetPop (long universeid, long subsetid, int population) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
        this.updateSubsetPopulation(universeid, subsetid, population);
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    public void setMapCost (long universeid, long subsetid, float cost) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
        this.updateSubsetMapCost(universeid, subsetid, cost);
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    //*** MAP PART ***
    //**********************************************************************
    public void addMapObject (long subsetid, PointLocation[] points) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
        //We check the subset exists in the master
        if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subsetid) == 0)
            throw new AppException("Subset not found", UniverseErrorCodes.SUBSETNOTFOUND);
        //-------------------------------------------------------------------
        MapObject object = new MapObject();
        object.recordid = subsetid;
        while(true) {
            object.objectid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.SubsetMapObject.TABLE, DBUniverse.SubsetMapObject.OBJECTID, object.objectid) == 0) break;
        }
        //-------------------------------------------------------------------
        //We insert the map object.
        this.insertMapObject(object);
        //-------------------------------------------------------------------
        //We insert the points for the object
        for (PointLocation point : points) {
            point.recordid = object.recordid;
            point.objectid = object.objectid;
            this.insertPointLocation(point);
        }
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Clear map objects given a subset id
     * @param subsetid
     * @throws Exception 
     */
    public void clearMapObject (long subsetid) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
        this.deleteMapObject(subsetid);
        this.deletePointLocations(subsetid);
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    public MapObject[] getObjectsBySubset (long recordid, boolean fillpoints) throws Exception {
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
