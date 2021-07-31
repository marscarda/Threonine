package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.Alcyone;
import methionine.sql.MySQLEngine;
import methionine.sql.SQLCreateTable;
//**************************************************************************
public class QueryUniverseTabs extends Alcyone {
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
        if (!checkTableExists(DBUniverse.Universe.TABLE, tables)) createUniverses();
        if (!checkTableExists(DBUniverse.DBSubSets.TABLE, tables)) createSubSets();
        if (!checkTableExists(DBUniverseUsers.TABLE, tables)) createUniverseUsers();
        if (!checkTableExists(DBMapRecords.TABLE, tables)) createMapRecords();
        if (!checkTableExists(DBMapObjects.TABLE, tables)) createMapObjects();
        //===================================================================
    }
    //***********************************************************************
    private void createUniverses () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.Universe.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.Universe.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBUniverse.Universe.PROJECTID, "BIGINT NOT NULL");
        create.addField(DBUniverse.Universe.NAME, "VARCHAR (100) NOT NULL");
        create.addField(DBUniverse.Universe.DESCRIPTION, "VARCHAR (300) NOT NULL");
        create.addField(DBUniverse.Universe.WEIGHTED, "INTEGER NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.Universe.TABLE);
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
    private void createSubSets () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.DBSubSets.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.DBSubSets.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.DBSubSets.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBUniverse.DBSubSets.PARENTSUBSET, "BIGINT NOT NULL");
        create.addField(DBUniverse.DBSubSets.NAME, "VARCHAR (50) NOT NULL");
        create.addField(DBUniverse.DBSubSets.DESCRIPTION, "VARCHAR (100) NULL");
        create.addField(DBUniverse.DBSubSets.POPULATION, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.DBSubSets.WEIGHT, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.DBSubSets.MAPRECORDID, "BIGINT NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.DBSubSets.TABLE);
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
    private void createUniverseUsers () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverseUsers.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverseUsers.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBUniverseUsers.USERID, "BIGINT NOT NULL");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverseUsers.TABLE);
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
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBMapRecords.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMapRecords.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMapRecords.CATALOG, "VARCHAR (100) NOT NULL");
        create.addField(DBMapRecords.TAG, "VARCHAR (30) NOT NULL");
        create.addField(DBMapRecords.NAME, "VARCHAR (100) NOT NULL");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMapRecords.TABLE);
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
        SQLCreateTable create = new SQLCreateTable(DBMapObjects.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBMapObjects.RECORDID, "BIGINT NOT NULL");
        create.addField(DBMapObjects.OBJECTCODE, "VARCHAR (20) NOT NULL");
        create.addField(DBMapObjects.POINTINDEX, "INTEGER NOT NULL");
        create.addField(DBMapObjects.LATITUDE, "FLOAT NOT NULL DEFAULT 0");
        create.addField(DBMapObjects.LONGITUDE, "FLOAT NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBMapObjects.TABLE);
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
