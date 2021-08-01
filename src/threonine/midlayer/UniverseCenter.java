package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.auth.AuthLamda;
import methionine.project.Project;
import methionine.project.ProjectLambda;
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
        //We check the user thai is trying has write access to the project
        projectlambda.checkAccess(universe.projectID(), userid, 2);
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
        projectlambda.checkAccess(projectid, userid, 1);
        return universelambda.getUniverses(projectid);
    }
    //**********************************************************************
    public void createSubset (SubSet subset, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (subset.getParentSubSet() == 0)
            throw new AppException("New Subset must have a valid parent", AppException.ROOTSUBSETALREADYEXISTS);
        //------------------------------------------------------------------
        Universe universe = universelambda.getUniverse(subset.getUniverseID());
        projectlambda.checkAccess(universe.projectID(), userid, 2);
        universelambda.createSubSet(subset);
    }
    //**********************************************************************
    public SubSet[] getSubsets (long universeid, long parentid, long userid) throws AppException, Exception {

        SubSet[] subsets = universelambda.getSubsets(universeid, parentid);
        return subsets;

    }
    //**********************************************************************
}
//**************************************************************************
