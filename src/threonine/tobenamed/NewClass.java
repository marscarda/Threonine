package threonine.tobenamed;
//**************************************************************************
import methionine.AppException;
import methionine.project.ProjectLambda;
import threonine.map.MapFolder;
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
//**************************************************************************
public class NewClass {
    //**********************************************************************
    ProjectLambda projectlambda = null;
    MapsLambda mapslambda = null;
    public void setProjectLambda (ProjectLambda projectlambda) { this.projectlambda = projectlambda; }
    public void setMapsLambda (MapsLambda mapslambda) { this.mapslambda = mapslambda; }
    //**********************************************************************
    /**
     * Creates a new Map Folder
     * @param folder
     * @param userid
     * @throws AppException
     * @throws Exception 
     */
    public void createFolder (MapFolder folder, long userid) throws AppException, Exception {
        projectlambda.checkAccess(folder.projectID(), userid, 2);
        mapslambda.createFolder(folder);
    }
    //**********************************************************************
    /**
     * 
     * @param record
     * @param userid
     * @throws AppException
     * @throws Exception 
     */
    public void createRecord (MapRecord record, long userid) throws AppException, Exception {
        MapFolder folder = mapslambda.getMapFolder(record.getFolderID());
        projectlambda.checkAccess(folder.projectID(), userid, 2);
        record.setProjectId(folder.projectID());//Since project ID must macht with folder we assign here
        mapslambda.createMapRecord(record);
    }
    //**********************************************************************
    public MapFolder getMapFolder (long folderid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        MapFolder folder;
        //------------------------------------------------------------------
        if (folderid == 0) {
            folder = new MapFolder();
            folder.setValid();
            return folder;
        }
        //------------------------------------------------------------------
        folder = mapslambda.getMapFolder(folderid);
        projectlambda.checkAccess(folder.projectID(), userid, 1);
        //------------------------------------------------------------------
        return folder;
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param parentid
     * @param projectid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapFolder[] getMapFolders(long parentid, long projectid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        //Check the user has access to the project to this root
        if (parentid == 0)
            projectlambda.checkAccess(projectid, userid, 1);
        //------------------------------------------------------------------
        //If children folders we check the user has access to the parent's project
        projectlambda.checkAccess(projectid, userid, 1);
        //------------------------------------------------------------------
        //If not root Parent folder must match the current project.
        if (parentid != 0) {
            MapFolder folder = mapslambda.getMapFolder(parentid);
            if (folder.projectID() != projectid) return new MapFolder[0];
        }
        //------------------------------------------------------------------
        return mapslambda.getChildrenFolders(projectid, parentid);
    }
    //**********************************************************************
    /**
     * Returns map records given a folder.
     * @param folderid
     * @param projectid
     * @param userid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecord[] getMapRecords(long folderid, long projectid, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (folderid == 0) return new MapRecord[0];
        //------------------------------------------------------------------
        //We check the user has access to the project where the folder belongs.
        //If projectID is specified (!= 0) it must match the the project in wich the folder is.
        MapFolder folder = mapslambda.getMapFolder(folderid);
        if (projectid != 0)
            if (projectid != folder.projectID()) return new MapRecord[0];
        projectlambda.checkAccess(folder.projectID(), userid, 1);
        //------------------------------------------------------------------
        return mapslambda.getMapRecords(folderid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
