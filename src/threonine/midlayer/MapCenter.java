package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.Celaeno;
import methionine.auth.AuthLamda;
import methionine.auth.User;
import methionine.project.Project;
import methionine.project.ProjectLambda;
import threonine.map.FolderUsage;
import threonine.map.MapFolder;
import threonine.map.MapRecord;
import threonine.map.MapsLambda;
import threonine.map.PointAdd;
//**************************************************************************
public class MapCenter {
    //**********************************************************************
    AuthLamda authlambda = null;
    ProjectLambda projectlambda = null;
    MapsLambda mapslambda = null;
    public void setAuthLambda (AuthLamda authlambda) { this.authlambda = authlambda; }
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
        if (folder.publicName().length() == 0)
            throw new AppException("Share ID cannot be empty", AppException.INVALIDDATASUBMITED);
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
    public void createObject (PointAdd[] points, long recordid, long userid) throws AppException, Exception {
        MapRecord record = mapslambda.getMapRecord(recordid);
        projectlambda.checkAccess(record.getProjectID(), userid, 2);
        mapslambda.createMapObject(recordid, points);
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
    public void updateFolderAttribute (long folderid, UpdateMapFolderAttr updateattr, long userid) throws AppException, Exception {
        MapFolder folder = mapslambda.getMapFolder(folderid);
        projectlambda.checkAccess(folder.projectID(), userid, 3);
        switch (updateattr.attrib) {
            //--------------------------------------------------
            case UpdateMapFolderAttr.PUBNAME:
                if (updateattr.publicname == null)
                    throw new AppException("Public name cannot be empty", AppException.INVALIDDATASUBMITED);
                if (updateattr.publicname.length() == 0)
                    throw new AppException("Public name cannot be empty", AppException.INVALIDDATASUBMITED);
                mapslambda.updateMapFolderPublicName(folderid, updateattr.publicname);
                updateattr.sharepass = folder.sharePass();
                updateattr.costperuse = folder.costPerUse();
                updateattr.searchable = folder.isSearchableInt();
                break;
            //--------------------------------------------------
            case UpdateMapFolderAttr.SHAREPASS:
                mapslambda.updateMapFolderSharePass(folderid, updateattr.sharepass);
                updateattr.publicname = folder.publicName();
                updateattr.costperuse = folder.costPerUse();
                updateattr.searchable = folder.isSearchableInt();
                break;
            //--------------------------------------------------
            case UpdateMapFolderAttr.COSTPERUSE:
                mapslambda.updateMapFolderCostPerUse(folderid, updateattr.costperuse);
                if (updateattr.costperuse != 0) {
                    updateattr.searchable = 1;
                    mapslambda.updateSearchable(folderid, updateattr.searchable);
                }
                else updateattr.searchable = folder.isSearchableInt();
                updateattr.publicname = folder.publicName();
                updateattr.sharepass = folder.sharePass();
                break;
            //--------------------------------------------------
            case UpdateMapFolderAttr.SEARCHABLE:
                mapslambda.updateSearchable(folderid, updateattr.searchable);
                updateattr.publicname = folder.publicName();
                updateattr.sharepass = folder.sharePass();
                updateattr.costperuse = folder.costPerUse();
                break;
            //--------------------------------------------------
        }
    }
    //**********************************************************************
    public MapFolder[] searchFolders (String searchkey) throws Exception {
        //-------------------------------------------------
        Project project;
        User user;
        //-------------------------------------------------
        MapFolder[] folders = mapslambda.searchFolders(searchkey);
        for (MapFolder folder : folders) {
            project = projectlambda.getProject(folder.projectID(), 0);
            user = authlambda.getUser(project.getOwner(), false);
            folder.setUserID(user.userID());
            folder.setUserName(user.loginName());
        }
        //-------------------------------------------------
        return folders;
        //-------------------------------------------------
    }
    //**********************************************************************
    public MapFolder createFolderUsage (FolderUsage usage, long userid) throws AppException, Exception {
        //------------------------------------------------------------------
        //We check the user has access to the project 
        //where the folder is being added
        projectlambda.checkAccess(usage.projectID(), userid, 3);
        //------------------------------------------------------------------
        //We try to find the folder somehow.
        MapFolder folder = null;
        //We first try by ID.
        if (usage.folderID() != 0)
            folder = mapslambda.getMapFolder(usage.folderID());
        //If we didn't get it by ID. We try by public name
        if (folder == null)
            folder = mapslambda.getMapFolder(usage.publicName());
        //------------------------------------------------------------------
        //We make sure we add the usage Folder ID.
        usage.setFolderID(folder.getID());//We make sure we have the ID.        
        //------------------------------------------------------------------
        //If we received a Folder Password We try to add it
        if (usage.hasPassword()) {
            if (folder.checkPassword(usage.sharePassword())) {
                //We find out the user owner of the folder
                Project project = projectlambda.getProject(folder.projectID(), 0);
                User user = authlambda.getUser(project.getOwner(), false);
                folder.setUserID(user.userID());
                folder.setUserName(user.loginName());
                mapslambda.createFolderUsage(usage);
                return folder;
            }
            throw new AppException("Invalid Share Password for this folder", AppException.UNAUTHORIZED);
        }
        //------------------------------------------------------------------
        //If folder has no established usage cost. We reject it.
        if (folder.costPerUse() == 0) 
            throw new AppException("Not authorized to use this map objects folder", AppException.UNAUTHORIZED);
        //------------------------------------------------------------------
        //We find out the user owner of the folder
        Project project = projectlambda.getProject(folder.projectID(), 0);
        User user = authlambda.getUser(project.getOwner(), false);
        folder.setUserID(user.userID());
        folder.setUserName(user.loginName());
        //------------------------------------------------------------------
        //We Add the folder with usage cost.
        usage.setCost(folder.costPerUse());
        mapslambda.createFolderUsage(usage);
        //------------------------------------------------------------------
        return folder;
        //------------------------------------------------------------------
    }
    //**********************************************************************
    public MapFolder[] getUsedFoldersInProject (long projectid, long userid) throws Exception {
        //-------------------------------------------------
        //We check the user has access to the project
        projectlambda.checkAccess(projectid, userid, 1);
        //-------------------------------------------------
        Project project;
        User user;
        //-------------------------------------------------
        MapFolder[] folders = mapslambda.getFolderUsed(projectid);
        for (MapFolder folder : folders) {
            project = projectlambda.getProject(folder.projectID(), 0);
            user = authlambda.getUser(project.getOwner(), false);
            folder.setUserID(user.userID());
            folder.setUserName(user.loginName());
        }
        //-------------------------------------------------
        return folders;
        //-------------------------------------------------
    } 
    //**********************************************************************
}
//**************************************************************************
