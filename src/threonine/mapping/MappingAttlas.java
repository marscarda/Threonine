package threonine.mapping;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Random;
import methionine.AppException;
import methionine.Celaeno;
import methionine.sql.SQLLockTables;
//**************************************************************************
public class MappingAttlas extends MappingAtlasFolders {
    //**********************************************************************
    /* LAYERS */
    //**********************************************************************
    /**
     * Creates a Map Layer.
     * @param layer
     * @throws AppException
     * @throws Exception 
     */
    public void createLayer (MapLayer layer) throws AppException, Exception {
        //------------------------------------------------------------------
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        if (checkValueCount(DBMaps.MapLayer.TABLE, DBMaps.MapLayer.LAYERNAME, layer.layername, 
            DBMaps.MapLayer.PROJECTID, layer.projectid) != 0)
                throw new AppException("Layer Name already exists", MapErrorCodes.FOLDERNAMEALREADYEXISTS);
        //------------------------------------------------------------------
        while (true) {
            try {
                layer.layerid = Celaeno.getUniqueID();
                this.insertLayer(layer);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a layer given its ID.
     * @param layerid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapLayer getLayer (long layerid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        return this.selectLayer(layerid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns Map Layers given a project ID.
     * @param projectid
     * @return
     * @throws Exception 
     */
    public MapLayer[] getLayersByProject (long projectid) throws Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        return this.selectLayersByProject(projectid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param projectid
     * @param searchkey
     * @return
     * @throws Exception 
     */
    public MapLayer[] searchLayers (long projectid, String searchkey) throws Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        return this.selectLayersByKey(projectid, searchkey);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a random list of public layers.
     * @return
     * @throws Exception 
     */
    public MapLayer[] publicLayers () throws Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        int publiccount = this.selectPublicLayerCount();
        int offset = 0;
        //------------------------------------------------------------------
        if (publiccount > PUBLICCOUNT) {
            Random rand = new Random();
            offset = rand.nextInt(publiccount - PUBLICCOUNT);
        }
        //------------------------------------------------------------------
        return selectPublicLayers(offset);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns the list of layers for Publish.
     * @return
     * @throws Exception 
     */
    public MapLayer[] forPublishLayers () throws Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        return selectForPublishLayers();
        //------------------------------------------------------------------
    }    
    //**********************************************************************
    /**
     * 
     * @param layerid
     * @param value
     * @throws Exception 
     */
    public void setForPub (long layerid, int value) throws Exception {
        //------------------------------------------------------------------
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        this.updateForPub(layerid, value);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param layerid
     * @param name
     * @param description
     * @throws Exception 
     */
    public void setPublic (long layerid, String name, String description) throws Exception {
        //------------------------------------------------------------------
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        this.updateLayerToPublic(layerid, name, description);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /* LAYER USES */
    //**********************************************************************
    /**
     * Creates a layer use.
     * @param use
     * @throws Exception 
     */
    public void createLayerUse (LayerUse use) throws Exception {
        //------------------------------------------------------------------
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        int count = checkValueCount(DBMaps.LayerUse.TABLE, DBMaps.LayerUse.PROJECTID, use.projectid, DBMaps.LayerUse.LAYERID, use.layerid);
        if (count != 0)
            throw new AppException("Layer already added", MapErrorCodes.LAYERUSEALREADYADDED);
        this.insertLayerUse(use);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns Layers used in a project.
     * @param projectid
     * @return
     * @throws Exception 
     */
    public MapLayer[] getUsedLayers (long projectid) throws Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        return this.selectLayersByUse(projectid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /* RECORDS */
    //**********************************************************************
    /**
     * Adds a Map Record to the system.
     * @param record
     * @throws AppException
     * @throws Exception 
     */
    public void createMapRecord (MapRecord record) throws AppException, Exception {
        //------------------------------------------------------------------
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        while (true) {
            try {
                record.recordid = Celaeno.getUniqueID();
                this.insertMapRecord(record);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a map record given its ID.
     * @param recordid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public MapRecord getMapRecord (long recordid) throws AppException, Exception {
        //------------------------------------------------------------------
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapRecord(recordid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns map records given a folder
     * @param layerid
     * @return
     * @throws Exception 
     */
    public MapRecord[] getMapRecords (long layerid) throws Exception {
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        return this.selectMapRecords(layerid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /* FEATURES */
    //**********************************************************************
    public void createMapObject (long recordid, PointLocation[] points, float cost) throws AppException, Exception {
        //=============================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //=============================================================
        //We check the map record exists.
        if (checkValueCount(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.RECORDID, recordid) == 0)
            throw new AppException("Map Record not found", MapErrorCodes.MAPRECORDNOTFOUND);
        //=============================================================
        MapObject object = new MapObject();
        object.recordid = recordid;
        object.cost = cost;
        while (true) {
            object.objectid = Celaeno.getUniqueID();
            try { this.insertMapObject(object); }
            catch (SQLIntegrityConstraintViolationException e) { continue; }
            break;
        }
        //-------------------------------------------------------------
        for (PointLocation point : points) {
            point.recordid = recordid;
            point.objectid = object.objectid;
            this.insertPointLocation(point);
        }
        //=============================================================
    }    
    //**********************************************************************
    public void clearMapObjects (long recordid) throws Exception {
        //=============================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //=============================================================
        this.deleteMapObjects(recordid);
        this.deletePointLocations(recordid);
        //=============================================================
    }
    //**********************************************************************
    /* LAYER USES */
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    /**
     * Creates a new map record.
     * @param record
     * @throws AppException OBJECTNOTFOUND
     * @throws Exception 
     */
    public void createMapRecordO (MapRecord record) throws AppException, Exception {
        
        
        
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        SQLLockTables lock = new SQLLockTables();
        lock.setDataBase(databasename);
        lock.addTable(DBMaps.FolderTree.TABLE);
        lock.addTable(DBMaps.MapRecords.TABLE);
        this.getExclusiveTableAccess(lock);
        //------------------------------------------------------------------
        if (checkValueCount(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID, record.layerid) == 0)
            throw new AppException("Layer Not Found", MapErrorCodes.MAPLAYERNOTFOUND);
        //------------------------------------------------------------------
        while (true) {
            record.recordid = Celaeno.getUniqueID();
            if (checkValueCount(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.RECORDID, record.recordid) == 0) break;
        }
        //------------------------------------------------------------------
        this.insertMapRecord(record);
        this.releaseExclusiveTableAccess();
        //------------------------------------------------------------------
        
        
        
        
    }
    //**********************************************************************
    //**********************************************************************
    /**
     * 
     * @param recordid
     * @throws Exception 
     */
    @Override
    public void deleteMapRecord (long folderid, long recordid) throws Exception {
        //------------------------------------------------------------------
        connection = electra.masterConnection();
        setDataBase();
        //------------------------------------------------------------------
        super.deleteMapRecord(folderid, recordid);
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * 
     * @param recordid
     * @param points
     * @param cost
     * @throws AppException
     * @throws Exception 
     */
    //**********************************************************************
    /**
     * Clear map objects from a map record.
     * @param recordid
     * @throws Exception 
     */
    //**********************************************************************
    /**
     * 
     * @param recordid
     * @param fillpoints
     * @return
     * @throws Exception 
     */
    public MapObject[] getObjectsByRecord (long recordid, boolean fillpoints) throws Exception {
        //------------------------------------------------------------------
        if (usemaster) connection = electra.masterConnection();
        else connection = electra.slaveConnection();
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
