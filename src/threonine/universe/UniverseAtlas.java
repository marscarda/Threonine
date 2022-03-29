package threonine.universe;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import methionine.AppException;
import methionine.Celaeno;
import threonine.mapping.MapFeature;
import threonine.mapping.PointLocation;
//**************************************************************************
public class UniverseAtlas extends UniverseAtlasTemplate {
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
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        //We create the universe in the database.
        while (true) {
            universe.univerid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.Universe.TABLE, DBUniverse.Universe.UNIVERSEID, universe.univerid) == 0) break;
        }
        //------------------------------------------------------------------
        //We now create the first subset.
        subset.universeid = universe.univerid;
        while (true) {
            subset.subsetid = Celaeno.getUniqueID();
            if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subset.subsetid) == 0) break;
        }
        //------------------------------------------------------------------
        this.insertUniverse(universe);
        this.insertSubSet(subset);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    //**********************************************************************
    /**
     * Returns a universe given its ID.
     * @param universeid
     * @return
     * @throws AppException
     * @throws Exception UNIVERSENOTFOUND
     */
    public Universe getUniverse (long universeid) throws AppException, Exception {
        //==================================================================
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        Universe universe = this.selectUniverse(universeid);
        return universe;
        //==================================================================
    }
    //**********************************************************************
    /**
     * Returns an array of universes.
     * @param projectid
     * @return
     * @throws Exception 
     */
    public Universe[] getUniverses (long projectid) throws Exception {
        //==================================================================
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================        
        return this.selectUniverses(projectid);
        //==================================================================        
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
    //** SUBSETS **
    //**********************************************************************
    /**
     * Create a new Subset
     * @param subset
     * @throws AppException UNIVERSENOTFOUND SUBSETNOTFOUND ROOTSUBSETALREADYEXISTS
     * @throws Exception 
     */
    public void createSubSet (SubSet subset) throws AppException, Exception {
        //================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //================================================================
        if (checkValueCount(DBUniverse.Universe.TABLE, DBUniverse.Universe.UNIVERSEID, subset.universeid) == 0) {
            throw new AppException("Universe not found", UniverseErrorCodes.UNIVERSENOTFOUND);
        }
        //-------------------------------------------------------------------
        if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subset.parentsubset,
            DBUniverse.SubSets.UNIVERSEID, subset.universeid) == 0) {
            throw new AppException("Parent subset not found", UniverseErrorCodes.SUBSETNOTFOUND);
        }
        //-------------------------------------------------------------------
        while (true) {
            try {
                subset.subsetid = Celaeno.getUniqueID();
                this.insertSubSet(subset);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
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
        //----------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //----------------------------------------------------------        
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
        //----------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //----------------------------------------------------------        
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
    public void setMapStatus (long subsetid, int status) throws Exception {
        //-------------------------------------------------------------------
        connection = electra.masterConnection();
        this.setDataBase();
        //-------------------------------------------------------------------
        this.updateSubsetMapStatus(subsetid, status);
        //-------------------------------------------------------------------        
    }
    //**********************************************************************
    //*** MAP PART ***
    //**********************************************************************
    public void addMapFeature (long subsetid, PointLocation[] points) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        //We check the subset exists in the master
        if (checkValueCount(DBUniverse.SubSets.TABLE, DBUniverse.SubSets.SUBSETID, subsetid) == 0)
            throw new AppException("Subset not found", UniverseErrorCodes.SUBSETNOTFOUND);
        //-------------------------------------------------------------------
        MapFeature feature = new MapFeature();
        feature.recordid = subsetid;
        //-------------------------------------------------------------------
        while (true) {
            try {
                feature.recordid = Celaeno.getUniqueID();
                this.insertMapFeature(feature);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //-------------------------------------------------------------------        
        //We insert the points for the object
        for (PointLocation point : points) {
            point.recordid = feature.recordid;
            point.objectid = feature.objectid;
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
    public MapFeature[] getObjectsBySubset (long recordid, boolean fillpoints) throws Exception {
        //----------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //----------------------------------------------------------        
        MapFeature[] objects = this.selectMapObjects(recordid);
        if (!fillpoints) return objects;
        //------------------------------------------------------------------
        for (MapFeature object : objects)
            object.points = this.selectPointLocations(object.objectid);
        //------------------------------------------------------------------
        return objects;
        //------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
