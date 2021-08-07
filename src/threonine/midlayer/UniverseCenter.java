package threonine.midlayer;
//**************************************************************************
import methionine.AppException;
import methionine.TabList;
import methionine.auth.AuthLamda;
import methionine.billing.BillingLambda;
import methionine.billing.ComunityTransfer;
import methionine.billing.SystemCharge;
import methionine.billing.UsageCost;
import methionine.project.Project;
import methionine.project.ProjectLambda;
import threonine.map.FolderUsage;
import threonine.map.MapFolder;
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
    BillingLambda billinglambda = null;
    public void setAuthLambda (AuthLamda authlambda) { this.authlambda = authlambda; }
    public void setProjectLambda (ProjectLambda projectlambda) { this.projectlambda = projectlambda; }
    public void setUniverseLambda (UniverseLambda universelambda) { this.universelambda = universelambda; }
    public void setMapsLambda (MapsLambda mapslambda) { this.mapslambda = mapslambda; }
    public void setBillingLambda (BillingLambda billinglambda) { this.billinglambda = billinglambda; }
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
    public void setMapRecordTo(long subsetid, long recordid, long projectid, long userid) throws AppException, Exception {
        //******************************************************************
        //Reading and Verification part
        //------------------------------------------------------------------
        //We check the user has access to the project.
        projectlambda.checkAccess(projectid, userid, 2);
        //------------------------------------------------------------------
        //We check the owner of the project is able to spend.
        Project projectsubset = projectlambda.getProject(projectid, 0);
        if (!billinglambda.checkAbleToSpend(projectsubset.getOwner()))
            throw new AppException("Not enough balance", AppException.SPENDINGREJECTED);
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
        //We decide and act the usage of the map record has a cost.
        boolean dotransfer = false;
        MapFolder folder = null;
        Project projectto = null;
        if (usage.costPerUse() != 0) {
            dotransfer = true;
            folder = mapslambda.getMapFolder(record.getFolderID());
            projectto = projectlambda.getProject(folder.projectID(), 0);
        }
        //------------------------------------------------------------------
        //We check the subset exists and the user has access to the project
        //where it belongs.
        SubSet subset = universelambda.getSubset(0, subsetid);
        Universe universe = universelambda.getUniverse(subset.getUniverseID());
        if (universe.projectID() != projectid)
            throw new AppException("Unauthorized", AppException.UNAUTHORIZED);
        //------------------------------------------------------------------
        MapReaderGraphic mapreader = new MapReaderGraphic();
        mapreader.setMapsLambda(mapslambda);
        MapRecordGraphic recordg = mapreader.getMapRecord(record);
        MapObjectGraphic[] objects = recordg.getMapObjects();
        //------------------------------------------------------------------
        //If there is no map object in the record.
        if (objects.length == 0)
            throw new AppException("The record " + record.getName() + " has no map object", AppException.NOMAPOBJECTINRECORD);
        //******************************************************************
        //Writing part
        //------------------------------------------------------------------
        //We lock all tables involved
        TabList tablist = new TabList();
        universelambda.AddLockMapRecord(tablist);
        billinglambda.AddLockCommunityTransfer(tablist);
        universelambda.setAutoCommit(0);
        universelambda.lockTables(tablist);
        //------------------------------------------------------------------
        //We clear the existent map objects the subset could have
        universelambda.clearMapObject(subset.getSubsetID());
        //-----------------------------------------------------------------
        //We Add the objects to the subset.
        for (MapObjectGraphic obj : objects)
            universelambda.addMapObject(subset.getSubsetID(), obj.getPoints());
        //------------------------------------------------------------------
        //If the use of the map object has a cost we create a transfer.
        //Else we create a syatem charge.
        if (dotransfer) {
            ComunityTransfer transfer = new ComunityTransfer();
            transfer.setFromUserid(projectsubset.getOwner());
            transfer.setFromProjectId(projectsubset.workTeamID());
            transfer.setToUserId(projectto.getOwner());//If it was a null pointer we would not be here.
            transfer.setToProjectId(projectto.workTeamID());
            String description = "Map Record " + record.getName() + " Added to subset";
            transfer.setDescription(description);
            transfer.setSystemCost(UsageCost.MAPRECORDTOSUBSET);
            transfer.setTransferSize(usage.costPerUse());
            billinglambda.createComunityTransfer(transfer);
        } else {
            SystemCharge charge = new SystemCharge();
            charge.setUserid(projectsubset.getOwner());
            charge.setProjectId(projectsubset.workTeamID());
            String description = "Map Record " + record.getName() + " Added to subset";
            charge.setDescription(description);
            charge.setCost(UsageCost.MAPRECORDTOSUBSET);
            billinglambda.createSystemCharge(charge);
        }
        //-----------------------------------------------------------------
        universelambda.commit();
        universelambda.unLockTables();
        //******************************************************************
    }
    //**********************************************************************
}
//**************************************************************************
