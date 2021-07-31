package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.auth.AuthLamda;
import methionine.project.ProjectLambda;
import threonine.map.MapsLambda;
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
    public Universe getUniverse (long universeid, long userid) throws AppException, Exception {
        Universe universe = universelambda.getUniverse(universeid);
        projectlambda.checkAccess(universe.projectID(), userid, 1);
        return universe;
    }
    //**********************************************************************
    public Universe[] getUniversesByProject (long projectid, long userid) throws AppException, Exception {
        projectlambda.checkAccess(projectid, userid, 1);
        return universelambda.getUniverses(projectid);
    }
    //**********************************************************************
}
//**************************************************************************
