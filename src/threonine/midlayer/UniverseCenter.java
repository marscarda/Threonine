package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.auth.AuthLamda;
import methionine.project.ProjectLambda;
import threonine.map.FolderUsage;
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
import threonine.universe.SubSet;
import threonine.universe.Universe;
import threonine.universe.UniverseLambda;
//**************************************************************************
public class UniverseCenter {
    //**********************************************************************
    AuthLamda authlambda = null;
    ProjectLambda projectlambda = null;
    UniverseLambda universelambda = null;
    MapsLambda mapslambda = null;
    public void setAuthLambda (AuthLamda authlambda) { this.authlambda = authlambda; }
    public void setProjectLambda (ProjectLambda projectlambda) { this.projectlambda = projectlambda; }
    public void setUniverseLambda (UniverseLambda universelambda) { this.universelambda = universelambda; }
    public void setMapsLambda (MapsLambda mapslambda) { this.mapslambda = mapslambda; }
    //**********************************************************************
    /**
     * Creates a new Universe. 
     * @param universe
     * @param userid
     * @throws AppException
     * @throws Exception 
     */
    public void createUniverse (Universe universe, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (universe.getName().length() == 0)
            throw new AppException("Universe Name cannot be empty", AppException.INVALIDDATASUBMITED);
        //------------------------------------------------------------------
        //We check the user thai is trying has write access to the project
        projectlambda.checkAccess(universe.projectID(), userid, 2);
        //------------------------------------------------------------------
        universelambda.createUniverse(universe);
    }
    //**********************************************************************
    /**
     * Returns a universe given its ID
     * @param universeid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public Universe getUniverse (long universeid, long userid) throws AppException, Exception {
        Universe universe = universelambda.getUniverse(universeid);
        //We check the user has access to the project
        if (userid != 0) projectlambda.checkAccess(universe.projectID(), userid, 1);
        return universe;
    }
    //**********************************************************************
    /**
     * Returns the list of universes given a project id
     * @param projectid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public Universe[] getUniversesByProject (long projectid, long userid) throws AppException, Exception {
        //We check the user has access to the project.
        if (userid != 0) 
            projectlambda.checkAccess(projectid, userid, 1);
        return universelambda.getUniverses(projectid);
    }
    //**********************************************************************
    public void createSubset (SubSet subset, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (subset.getName().length() == 0)
            throw new AppException("Subset Name cannot be empty", AppException.INVALIDDATASUBMITED);
        //------------------------------------------------------------------
        if (subset.getParentSubSet() == 0)
            throw new AppException("New Subset must have a valid parent", AppException.ROOTSUBSETALREADYEXISTS);
        //------------------------------------------------------------------
        Universe universe = universelambda.getUniverse(subset.getUniverseID());
        projectlambda.checkAccess(universe.projectID(), userid, 2);
        universelambda.createSubSet(subset);
    }
    //**********************************************************************
    public SubSet getSubset (long universeid, long subsetid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        //We check the user has access to the project.
        if (userid != 0) {
            Universe universe = universelambda.getUniverse(universeid);
            projectlambda.checkAccess(universe.projectID(), userid, 1);
        }
        //------------------------------------------------------------------
        SubSet subset;
        if (subsetid == 0) {
            subset = new SubSet();
            subset.setUniverseID(universeid);
            subset.setValid();
            subset.setROOT();
        }
        else subset = universelambda.getSubset(universeid, subsetid);
        return subset;
        //------------------------------------------------------------------
    }
    //**********************************************************************
    public SubSet[] getSubsets (long universeid, long parentid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        //We check the user has access to the project.
        if (userid != 0) {
            Universe universe = universelambda.getUniverse(universeid);
            projectlambda.checkAccess(universe.projectID(), userid, 1);
        }
        //------------------------------------------------------------------
        SubSet[] subsets = universelambda.getSubsets(universeid, parentid);
        return subsets;
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Sets a map record to a subset.
     * The record is copied and not referenced.
     * @param subsetid
     * @param recordid
     * @param projectid
     * @param userid
     * @throws AppException
     * @throws Exception 
     */
    public void setMapRecordTo(long subsetid, long recordid, long projectid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        //We check the user has access to the project.
        projectlambda.checkAccess(projectid, userid, 2);
        //------------------------------------------------------------------
        //We recover the record. In the proccess we check if the record can be
        //used in the project that is intended. The usage is useful here to
        //set the using cost of the map record.
        MapRecord record = mapslambda.getMapRecord(recordid);
        FolderUsage usage;
        try { usage = mapslambda.getFolderUsage(projectid, record.getFolderID()); }
        catch (AppException e) {
            if (e.getErrorCode() == AppException.OBJECTNOTFOUND)
                throw new AppException("Unauthorized", AppException.UNAUTHORIZED);
            throw e;
        }
        //------------------------------------------------------------------
        //We check the subset exists and the user has access to the project
        //where it belongs.
        SubSet subset = universelambda.getSubset(0, subsetid);
        Universe universe = universelambda.getUniverse(subset.getUniverseID());
        if (universe.projectID() != projectid)
            throw new AppException("Unauthorized", AppException.UNAUTHORIZED);
        //------------------------------------------------------------------
        //We do the job.
        setMapRecordTo(subset, record);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Sets a Map Record to a subset
     * @param subset
     * @param record
     * @throws AppException
     * @throws Exception 
     */
    private void setMapRecordTo (SubSet subset, MapRecord record) throws AppException, Exception {
        //-----------------------------------------------------------------
        MapReaderGraphic mapreader = new MapReaderGraphic();
        mapreader.setMapsLambda(mapslambda);
        MapRecordGraphic recordg = mapreader.getMapRecord(record);
        MapObjectGraphic[] objects = recordg.getMapObjects();
        //-----------------------------------------------------------------
        //If there is no map object in the record.
        if (objects.length == 0)
            throw new AppException("The record " + record.getName() + " has no map object", AppException.NOMAPOBJECTINRECORD);
        //-----------------------------------------------------------------
        //Transactions and Locks here
        universelambda.setAutoCommit(0);
        //-----------------------------------------------------------------
        //We clear the existent map objects the subset could have
        universelambda.clearMapObject(subset.getSubsetID());
        //-----------------------------------------------------------------
        for (MapObjectGraphic obj : objects) {
            universelambda.addMapObject(subset.getSubsetID(), obj.getPoints());
        }
        //-----------------------------------------------------------------
        
        //Billing here
        
        //-----------------------------------------------------------------
        universelambda.commitTransaction();
        //-----------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
