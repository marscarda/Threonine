package threonine.universe;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Random;
import methionine.AppException;
import methionine.Celaeno;
import threonine.mapping.MapFeature;
import threonine.mapping.PointLocation;
//**************************************************************************
public class UniverseAtlasTemplate extends UniverseLock {
    //**********************************************************************
    public void addTemplateUniverse (Universe universe) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        while (true) {
            try {
                universe.univerid = Celaeno.getUniqueID();
                this.insertTemplateUniverse(universe);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //==================================================================
    }
    //**********************************************************************
    public void templateAddSubset (SubSet subset) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();        
        //==================================================================
        while (true) {
            try {
                subset.subsetid = Celaeno.getUniqueID();
                this.insertTemplateSubset(subset);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //==================================================================
    }
    //**********************************************************************
    /**
     * 
     * @param subsetid
     * @param points
     * @throws Exception 
     */
    public void templateAddFeature (long subsetid, PointLocation[] points) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        MapFeature feature = new MapFeature();
        feature.recordid = subsetid;
        //------------------------------------------------------------------
        while (true) {
            try {
                feature.objectid = Celaeno.getUniqueID();
                this.insertTemplateFeature(feature);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //------------------------------------------------------------------
        //We insert the points for the object
        for (PointLocation point : points) {
            point.recordid = feature.recordid;
            point.objectid = feature.objectid;
            this.insertTemplatePointLocation(point);
        }
        //------------------------------------------------------------------
    }
    //**********************************************************************
    /**
     * Returns a universe given its ID.
     * @param universeid
     * @return
     * @throws AppException
     * @throws Exception UNIVERSENOTFOUND
     */
    public Universe getTemplate (long universeid) throws AppException, Exception {
        //==================================================================
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        Universe universe = this.selectUniverseTemplate(universeid);
        return universe;
        //==================================================================
    }    
    //**********************************************************************
    /**
     * Returns a list of universe templates.
     * @return
     * @throws Exception 
     */
    public Universe[] getTemplates () throws Exception {
        
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        int templcount = this.selectTemplateCount();
        int offset = 0;
        //------------------------------------------------------------------
        if (templcount > PUBLICCOUNT) {
            Random rand = new Random();
            offset = rand.nextInt(templcount - PUBLICCOUNT);
        }
        //------------------------------------------------------------------
        return selectUniverseTemplates(offset);
        //------------------------------------------------------------------        
    }
    //**********************************************************************
}
//**************************************************************************
