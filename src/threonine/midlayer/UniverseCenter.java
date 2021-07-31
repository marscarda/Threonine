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
    public void createUniverse (Universe universe) throws AppException, Exception {
        universelambda.createUniverse(universe);
    }
    //**********************************************************************
}
//**************************************************************************
