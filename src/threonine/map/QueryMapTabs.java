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
        if (!checkTableExists(DBMaps.MapLayer.TABLE, tables)) createLayer();
        if (!checkTableExists(DBMaps.LayerUse.TABLE, tables)) createLayerUse();
        if (!checkTableExists(DBMaps.MapRecords.TABLE, tables)) createMapRecords();
        if (!checkTableExists(DBMaps.MapFeature.TABLE, tables)) createMapObjects();
        if (!checkTableExists(DBMaps.LocationPoints.TABLE, tables)) createLocationPoints();
        //===================================================================
    }
    //***********************************************************************
    private void createLayer () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.MapLayer.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.MapLayer.LAYERID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapLayer.PROJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapLayer.LAYERNAME, "VARCHAR (100) NOT NULL");
        create.addField(DBMaps.MapLayer.DESCRIPTION, "VARCHAR (300) NOT NULL");
        create.addField(DBMaps.MapLayer.FORPUB, "INTEGER NOT NULL DEFAULT 0");
        create.addUnique(DBMaps.MapLayer.LAYERID);
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.MapLayer.TABLE);
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
    private void createLayerUse () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMaps.LayerUse.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.LayerUse.PROJECTID, "BIGINT NOT NULL");
        create.addField(DBMaps.LayerUse.LAYERID, "BIGINT NOT NULL");
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
        create.addField(DBMaps.MapRecords.LAYERID, "BIGINT NOT NULL");
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
        SQLCreateTable create = new SQLCreateTable(DBMaps.MapFeature.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMaps.MapFeature.FEATUREID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapFeature.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMaps.MapFeature.OBJTYPE, "INTEGER NOT NULL");
        create.addField(DBMaps.MapFeature.COST, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addUnique(DBMaps.MapFeature.FEATUREID);
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMaps.MapFeature.TABLE);
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
