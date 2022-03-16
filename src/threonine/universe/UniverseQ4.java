package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.sql.SQLCondition;
import methionine.sql.SQLLimit;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLUpdate;
import methionine.sql.SQLWhere;
//**************************************************************************
public class UniverseQ4 extends UniverseQ3 {
    //**********************************************************************
    protected static final int PUBLICCOUNT = 15;
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
    /**
     * 
     * @return
     * @throws Exception 
     */
    protected int selectPublicCount () throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.Universe.TABLE);
        select.addItem("COUNT", "*", "C");
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.Universe.PUBLIC, "!=", 0));
        sql.addClause(select);
        sql.addClause(whr);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select public universe count\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
    /**
     * Selects a list of public universes.
     * @param search
     * @param offset
     * @return
     * @throws Exception 
     */
    protected Universe[] selectPublicUniverses (String search, int offset) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.Universe.TABLE);
        select.addItem(DBUniverse.Universe.UNIVERSEID);
        select.addItem(DBUniverse.Universe.PROJECTID);
        select.addItem(DBUniverse.Universe.NAME);
        select.addItem(DBUniverse.Universe.DESCRIPTION);
        select.addItem(DBUniverse.Universe.PUBLIC);
        select.addItem(DBUniverse.Universe.PRICE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.Universe.PUBLIC, "!=", 0));
        if (search != null)
            whr.addCondition(new SQLCondition(DBUniverse.Universe.NAME, "LIKE", "%" + search + "%"));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.Universe.NAME);
        //-------------------------------------------------------
        sql.addClause(select);
        sql.addClause(whr);
        sql.addClause(order);
        //-------------------------------------------------------
        if (search == null) {
            SQLLimit limit = new SQLLimit(offset, PUBLICCOUNT);
            sql.addClause(limit);
        }
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<Universe> universes = new ArrayList<>();
            Universe universe;
            while (rs.next()) {
                universe = new Universe();
                universe.univerid = rs.getLong(DBUniverse.Universe.UNIVERSEID);
                universe.projectid = rs.getLong(DBUniverse.Universe.PROJECTID);
                universe.name = rs.getString(DBUniverse.Universe.NAME);
                universe.description = rs.getString(DBUniverse.Universe.DESCRIPTION);
                universe.ispublic = rs.getInt(DBUniverse.Universe.PUBLIC);
                universe.price = rs.getFloat(DBUniverse.Universe.PRICE);
                universes.add(universe);
            }
            return universes.toArray(new Universe[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select universe \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
}
//**************************************************************************
