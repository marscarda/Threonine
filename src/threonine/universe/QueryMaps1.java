package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLGroupBy;
import methionine.sql.SQLInsert;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class QueryMaps1 extends QueryUniverse1 {
    //**********************************************************************
    /**
     * Inserts a new map record in the table.
     * @param record
     * @throws Exception In case something goes wrong.
     */
    protected void insertMapRecord (MapRecord record) throws Exception {
        SQLInsert insert = new SQLInsert(DBMapRecords.TABLE);
        insert.addValue(DBMapRecords.RECORDID, record.recordid);
        insert.addValue(DBMapRecords.TAG, record.tag);
        insert.addValue(DBMapRecords.CATALOG, record.catalog);
        insert.addValue(DBMapRecords.NAME, record.name);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map record \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Selects and returns a map record given its ID.
     * @param recordid
     * @return
     * @throws AppException MAPRECORDNOTFOUND
     * @throws Exception 
     */
    protected MapRecord selectMapRecord (long recordid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapRecords.TABLE);
        select.addItem(DBMapRecords.RECORDID);
        select.addItem(DBMapRecords.CATALOG);
        select.addItem(DBMapRecords.TAG);
        select.addItem(DBMapRecords.NAME);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapRecords.RECORDID, "=", recordid));
        sql.addClause(whr);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            if (!rs.next())
                throw new AppException("Map record not found", AppException.MAPRECORDNOTFOUND);
            MapRecord record = new MapRecord();
            record.recordid = recordid;
            record.catalog = rs.getString(DBMapRecords.CATALOG);
            record.tag = rs.getString(DBMapRecords.TAG);
            record.name = rs.getString(DBMapRecords.NAME);
            return record;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map record \n");
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
     * Selects and returns an array of map records by catalog.
     * @param catalog
     * @param tag if null this parameter is ignored
     * @return
     * @throws Exception 
     */
    protected MapRecord[] selectMapRecordsByCatalog (String catalog, String tag) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapRecords.TABLE);
        select.addItem(DBMapRecords.RECORDID);
        select.addItem(DBMapRecords.CATALOG);
        select.addItem(DBMapRecords.TAG);
        select.addItem(DBMapRecords.NAME);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapRecords.CATALOG, "=", catalog));
        if (tag != null) whr.addCondition(new SQLCondition(DBMapRecords.TAG, "=", tag));
        sql.addClause(whr);
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMapRecords.NAME);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapRecord> records = new ArrayList<>();
            MapRecord record;
            while (rs.next()) {
                record = new MapRecord();
                record.recordid = rs.getLong(DBMapRecords.RECORDID);
                record.catalog = rs.getString(DBMapRecords.CATALOG);
                record.tag = rs.getString(DBMapRecords.TAG);
                record.name = rs.getString(DBMapRecords.NAME);
                records.add(record);
            }
            return records.toArray(new MapRecord[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map records by catalog \n");
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
     * Selects and return a record starting at
     * @param startingat
     * @return
     * @throws Exception 
     */
    protected MapRecord[] selectMapRecordsStartingAt (String startingat) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapRecords.TABLE);
        select.addItem(DBMapRecords.RECORDID);
        select.addItem(DBMapRecords.CATALOG);
        select.addItem(DBMapRecords.TAG);
        select.addItem(DBMapRecords.NAME);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapRecords.NAME, "like", startingat + "%"));
        sql.addClause(whr);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMapRecords.NAME);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapRecord> records = new ArrayList<>();
            MapRecord record;
            while (rs.next()) {
                record = new MapRecord();
                record.recordid = rs.getLong(DBMapRecords.RECORDID);
                record.catalog = rs.getString(DBMapRecords.CATALOG);
                record.tag = rs.getString(DBMapRecords.TAG);
                record.name = rs.getString(DBMapRecords.NAME);
                records.add(record);
            }
            return records.toArray(new MapRecord[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map records starting at\n");
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
     * Deletes a map record from the table.
     * @param recordid
     * @throws Exception 
     */
    protected void deleteMapRecord (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBMapRecords.TABLE);
        sql.addClause(delete);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapRecords.RECORDID, "=", recordid));
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete from map records table\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Selects and returns an array of string resulted from a group by catalog.
     * @return
     * @throws Exception 
     */
    protected String[] selectCatalogs () throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapRecords.TABLE);
        select.addItem(DBMapRecords.CATALOG);
        sql.addClause(select);
        SQLGroupBy group = new SQLGroupBy();
        group.addColumn(DBMapRecords.CATALOG);
        sql.addClause(group);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMapRecords.CATALOG);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<String> catalogs = new ArrayList<>();
            while (rs.next()) 
                catalogs.add(rs.getString(DBMapRecords.CATALOG));
            return catalogs.toArray(new String[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map catalogs\n");
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
     * Selects tags grouped given a catalog.
     * @param catalog
     * @return
     * @throws Exception 
     */
    protected String[] selectTags (String catalog) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapRecords.TABLE);
        select.addItem(DBMapRecords.TAG);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapRecords.CATALOG, "=", catalog));
        sql.addClause(whr);
        SQLGroupBy group = new SQLGroupBy();
        group.addColumn(DBMapRecords.TAG);
        sql.addClause(group);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMapRecords.TAG);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<String> rectags = new ArrayList<>();
            while (rs.next()) 
                rectags.add(rs.getString(DBMapRecords.TAG));
            return rectags.toArray(new String[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map tags\n");
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
