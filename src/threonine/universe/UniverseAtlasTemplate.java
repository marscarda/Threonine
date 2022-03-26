package threonine.universe;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import methionine.AppException;
import methionine.Celaeno;
//**************************************************************************
public class UniverseAtlasTemplate extends UniverseLock {
    //**********************************************************************
    /**
     * Copies an existent universe to universe templates table.
     * Returns the ID of the new Universe ID.
     * @param universeid
     * @return the ID of the new Universe Template.
     * @throws AppException
     * @throws Exception 
     */
    public Universe universeToTemplate (long universeid) throws AppException, Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        Universe universe = this.selectUniverse(universeid);
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
        return universe;
        //==================================================================
    }
    //**********************************************************************
}
//**************************************************************************
