package threonine.universe;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import methionine.Celaeno;
//**************************************************************************
public class UniverseAtlasTemplate extends UniverseLock {
    //**********************************************************************
    public void addTemplate (Universe universe) throws Exception {
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
    //**********************************************************************
}
//**************************************************************************
