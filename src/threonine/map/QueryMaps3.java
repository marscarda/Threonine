package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.sql.SQLColumn;
import methionine.sql.SQLCondition;
import methionine.sql.SQLInnerJoin;
import methionine.sql.SQLInsert;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class QueryMaps3 extends QueryMaps2 {
    //**********************************************************************
    /**
     * Insert a new folder usage into the table.
     * @param usage
     * @throws Exception 
     */
    protected void insertFolderUsage (FolderUsage usage) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.FolderUsage.TABLE);
        insert.addValue(DBMaps.FolderUsage.PROJECTID, usage.projectid);
        insert.addValue(DBMaps.FolderUsage.FOLDERID, usage.folderid);
        insert.addValue(DBMaps.FolderUsage.COSTPERUSE, usage.costperuse);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new mapfolder usage\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Selects and return a set of folder usages given the project id.
     * @param projectid
     * @return
     * @throws Exception 
     */
    protected FolderUsage[] selectFolderUsages (long projectid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderUsage.TABLE);
        select.addItem(DBMaps.FolderUsage.PROJECTID);
        select.addItem(DBMaps.FolderUsage.FOLDERID);
        select.addItem(DBMaps.FolderUsage.COSTPERUSE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderUsage.PROJECTID, "=", projectid));
        //-------------------------------------------------------
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
            List<FolderUsage> usages = new ArrayList<>();
            FolderUsage usage;
            while (rs.next()) {
                usage = new FolderUsage();
                usage.projectid = rs.getLong(DBMaps.FolderUsage.PROJECTID);
                usage.folderid = rs.getLong(DBMaps.FolderUsage.FOLDERID);
                usage.costperuse = rs.getInt(DBMaps.FolderUsage.COSTPERUSE);
                usages.add(usage);
            }
            return usages.toArray(new FolderUsage[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map folder usages: qnuhrwgh\n");
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
     * 
     * @param projectid
     * @return
     * @throws Exception 
     */
    protected MapFolder[] selectFoldersUsedInProject (long projectid) throws Exception {
        //----------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.PUBLICNAME);
        select.addItem(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.COSTPERUSE);
        //-------------------------
        SQLInnerJoin join = new SQLInnerJoin(DBMaps.FolderUsage.TABLE);
        SQLColumn columnfolder, columnusage;
        SQLCondition condition;
        //-------------------------
        columnfolder = new SQLColumn(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID);
        columnusage = new SQLColumn(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.FOLDERID);
        condition = new SQLCondition(columnfolder, "=", columnusage);
        join.addCondition(condition);
        //-------------------------
        columnusage = new SQLColumn(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.PROJECTID);
        condition = new SQLCondition(columnusage, "=", projectid);
        join.addCondition(condition);
        join.addCondition(condition);
        //----------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.FolderTree.PUBLICNAME);
        //----------------------------------------------------------
        sql.addClause(select);
        sql.addClause(join);
        sql.addClause(order);
        //----------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapFolder> records = new ArrayList<>();
            MapFolder folder;
            while (rs.next()) {
                folder = new MapFolder();
                folder.folderid = rs.getLong(1);
                folder.projectid = rs.getLong(2);
                folder.publicname = rs.getString(3);
                folder.costperuse = rs.getInt(4);
                records.add(folder);
            }
            return records.toArray(new MapFolder[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to used folders used in project jrvjuys\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
    
    
    
    
    
    
/*
        //----------------------------------------------------------------------
        SQLMainText sql = new SQLMainText();
        SQLSelect select = new SQLSelect(DBRoads.TABLE);
        select.addItem(DBRoads.IDROAD);
        select.addItem(DBRoads.LONFROM);
        select.addItem(DBRoads.LATTO);
        select.addItem(DBRoads.LONTO);
        sql.addClause(select);
        //----------------------------------------------------------------------
        SQLInnerJoin join = new SQLInnerJoin(DBRegionRoadUnions.TABLE);
        SQLColumn columnroad, columnunion;
        SQLCondition condition;
        //---------------------------
        columnroad = new SQLColumn(DBRoads.TABLE, DBRoads.IDROAD);
        columnunion = new SQLColumn(DBRegionRoadUnions.TABLE, DBRegionRoadUnions.IDROAD);
        condition = new SQLCondition(columnroad, "=", columnunion);
        join.addCondition(condition);
        //---------------------------
        columnroad = new SQLColumn(DBRoads.TABLE, DBRoads.IDITEM);
        columnunion = new SQLColumn(DBRegionRoadUnions.TABLE, DBRegionRoadUnions.IDITEM);
        condition = new SQLCondition(columnroad, "=", columnunion);
        join.addCondition(condition);
        //---------------------------
        columnroad = new SQLColumn(DBRoads.TABLE, DBRoads.DICTIONARYCODE);
        condition = new SQLCondition(columnroad, "=", country);
        join.addCondition(condition);
        //---------------------------
        columnunion = new SQLColumn(DBRegionRoadUnions.TABLE, DBRegionRoadUnions.COUNTRYCODE);
        condition = new SQLCondition(columnunion, "=", country);
        join.addCondition(condition);
        //---------------------------
        columnunion = new SQLColumn(DBRegionRoadUnions.TABLE, DBRegionRoadUnions.PARENTIDCODE);
        condition = new SQLCondition(columnunion, "=", parent);
        join.addCondition(condition);
        //---------------------------
        columnunion = new SQLColumn(DBRegionRoadUnions.TABLE, DBRegionRoadUnions.IDROAD);
        condition = new SQLCondition(columnunion, "=", idroad);
        join.addCondition(condition);
        //---------------------------
        sql.addClause(join);
        //----------------------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {
            st = this.getDBConnection().prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute(this.getUsedbText());
            rs = st.executeQuery();
            List<RoadItem> items = new ArrayList<>();
            RoadItem road;
            while (rs.next()) {
                road = new RoadItem();
                road.idroad = rs.getString(DBRoads.IDROAD);
                road.iditem = rs.getString(DBRoads.IDITEM);
                road.name = rs.getString(DBRoads.NAME);
                road.setSide(rs.getString(DBRoads.SIDE));
                road.setAllowEven(rs.getString(DBRoads.ALLOWEVEN));
                road.setAllowOdd(rs.getString(DBRoads.ALLOWODD));
                road.numberfrom = rs.getInt(DBRoads.NUMBERFROM);
                road.numberto = rs.getInt(DBRoads.NUMBERTO);
                road.latfrom = rs.getFloat(DBRoads.LATFROM);
                road.lonfrom = rs.getFloat(DBRoads.LONFROM);
                road.latto = rs.getFloat(DBRoads.LATTO);
                road.lonto = rs.getFloat(DBRoads.LONTO);
                items.add(road);
            }
            return items.toArray(new RoadItem[0]);
    }
*/
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //**********************************************************************
}
//**************************************************************************