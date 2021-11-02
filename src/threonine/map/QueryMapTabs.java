package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.Alcyone;
import methionine.sql.MySQLEngine;
import methionine.sql.SQLCreateTable;
//**************************************************************************
public class QueryMapTabs extends Alcyone {
    //***********************************************************************
    public void ensureTables() throws Exception {
        String[] tables;// = getTables();
        connection = electra.masterConnection();
        this.setDataBase();
        try { tables =  getTables(); }
        catch (Exception e) {
            StringBuilder err = new StringBuilder("Failed to check tables existence in database " + databasename + "\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        //===================================================================
        if (!checkTableExists(DBMaps.FolderTree.TABLE, tables)) createFolderTree();
        if (!checkTableExists(DBMaps.FolderUsage.TABLE, tables)) createFolderShares();
        if (!checkTableExists(DBMaps.MapRecords.TABLE, tables)) createMapRecords();
        if (!checkTableExists(DBMaps.Objects.TABLE, tables)) createMapObjects();
        if (!checkTableExists(DBMaps.LocationPoints.TABLE, tables)) createLocationPoints();
        //===================================================================
    }
    //***********************************************************************
    private void createFolderTree () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.FolderTree.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.FolderTree.FOLDERID, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderTree.PROJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderTree.PARENTFOLDER, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderTree.FOLDERNAME, "VARCHAR (100) NOT NULL");
        create.addField(DBMaps.FolderTree.PUBLICNAME, "VARCHAR (100) NOT NULL");
        create.addField(DBMaps.FolderTree.SHAREPASS, "VARCHAR (100) NULL DEFAULT NULL");
        create.addField(DBMaps.FolderTree.COSTPERUSE, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBMaps.FolderTree.SEARCHABLE, "INTEGER NOT NULL DEFAULT 0");
        create.addUnique(DBMaps.FolderTree.FOLDERID);
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.FolderTree.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally {
            try { if (st != null) st.close(); } catch (Exception e) {}
        }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
    private void createFolderShares () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.FolderUsage.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.FolderUsage.PROJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderUsage.FOLDERID, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderUsage.COSTPERUSE, "INTEGER NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.FolderUsage.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally {
            try { if (st != null) st.close(); } catch (Exception e) {}
        }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
    private void createMapRecords () throws Exception {
        SQLCreateTable create = new SQLCreateTable(DBMaps.MapRecords.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.MapRecords.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapRecords.PROJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapRecords.FOLDERID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapRecords.NAME, "VARCHAR (100) NOT NULL");
        create.addField(DBMaps.MapRecords.EXTRADATA, "VARCHAR (100) NOT NULL");
        create.addField(DBMaps.MapRecords.ADMINDIV, "VARCHAR (50) NOT NULL");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.MapRecords.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally {
            try { if (st != null) st.close(); } catch (Exception e) {}
        }
        //-------------------------------------------------------------------
    }    
    //***********************************************************************
    private void createMapObjects () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.Objects.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.Objects.OBJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.Objects.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMaps.Objects.OBJTYPE, "INTEGER NOT NULL");
        create.addField(DBMaps.Objects.COST, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addUnique(DBMaps.Objects.OBJECTID);
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.Objects.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally {
            try { if (st != null) st.close(); } catch (Exception e) {}
        }
        //-------------------------------------------------------------------
    }    
    //***********************************************************************
    private void createLocationPoints () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.LocationPoints.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.LocationPoints.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMaps.LocationPoints.OBJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.LocationPoints.POINTINDEX, "INTEGER NOT NULL");
        create.addField(DBMaps.LocationPoints.LATITUDE, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addField(DBMaps.LocationPoints.LONGITUDE, "FLOAT (10,6) NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.LocationPoints.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally {
            try { if (st != null) st.close(); } catch (Exception e) {}
        }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
}
//***************************************************************************
