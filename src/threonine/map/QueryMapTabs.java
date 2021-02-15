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
        if (!checkTableExists(DBMaps.MapRecords.TABLE, tables)) createMapRecords();
        if (!checkTableExists(DBMaps.MapObjects.TABLE, tables)) createMapObjects();
        //===================================================================
    }
    //***********************************************************************
    private void createFolderTree () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.FolderTree.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.FolderTree.FOLDERID, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderTree.PARENTFOLDER, "BIGINT NOT NULL");
        create.addField(DBMaps.FolderTree.FOLDERNAME, "VARCHAR (100) NOT NULL");
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
    private void createMapRecords () throws Exception {
        SQLCreateTable create = new SQLCreateTable(DBMaps.MapRecords.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.MapRecords.RECORDID, "BIGINT NOT NULL");
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
        SQLCreateTable create = new SQLCreateTable(DBMaps.MapObjects.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.MapObjects.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapObjects.OBJECTCODE, "VARCHAR (20) NOT NULL");
        create.addField(DBMaps.MapObjects.POINTINDEX, "INTEGER NOT NULL");
        create.addField(DBMaps.MapObjects.LATITUDE, "FLOAT NOT NULL DEFAULT 0");
        create.addField(DBMaps.MapObjects.LONGITUDE, "FLOAT NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.MapObjects.TABLE);
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
