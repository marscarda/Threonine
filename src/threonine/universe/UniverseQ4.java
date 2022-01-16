package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLUpdate;
import methionine.sql.SQLWhere;
//**************************************************************************
public class UniverseQ4 extends UniverseQ3 {
    //**********************************************************************
    /**
     * 
     * @param universeid
     * @param status
     * @param price
     * @throws Exception 
     */
    protected void updatePubStatus (long universeid, int status, float price) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBUniverse.Universe.TABLE);
        update.addSetColumn(DBUniverse.Universe.PUBLIC, status);
        update.addSetColumn(DBUniverse.Universe.PRICE, price);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.Universe.UNIVERSEID, "=", universeid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update project public status\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
}
//**************************************************************************
