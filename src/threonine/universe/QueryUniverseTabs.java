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
        if (!checkTableExists(DBUniverse.SubSets.TABLE, tables)) createSubSets();
        if (!checkTableExists(DBUniverse.SubsetMapObject.TABLE, tables)) createMapObject();
        if (!checkTableExists(DBUniverse.LocationPoints.TABLE, tables)) createLocationPoints();
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
        create.addField(DBUniverse.Universe.PUBLIC, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.Universe.PRICE, "FLOAT (10,6) NOT NULL DEFAULT 0");
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
        SQLCreateTable create = new SQLCreateTable(DBUniverse.SubSets.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.SubSets.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubSets.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubSets.PARENTSUBSET, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubSets.NAME, "VARCHAR (50) NOT NULL");
        create.addField(DBUniverse.SubSets.DESCRIPTION, "VARCHAR (100) NULL");
        create.addField(DBUniverse.SubSets.POPULATION, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.WEIGHT, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.SUBSETCOST, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.MAPCOST, "FLOAT (10,6) NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.SubSets.TABLE);
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
    private void createMapObject () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.SubsetMapObject.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.SubsetMapObject.OBJECTID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetMapObject.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetMapObject.OBJTYPE, "INTEGER NOT NULL");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.SubsetMapObject.TABLE);
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
        SQLCreateTable create = new SQLCreateTable(DBUniverse.LocationPoints.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.LocationPoints.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.LocationPoints.OBJECTID, "BIGINT NOT NULL");
        create.addField(DBUniverse.LocationPoints.POINTINDEX, "INTEGER NOT NULL");
        create.addField(DBUniverse.LocationPoints.LATITUDE, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addField(DBUniverse.LocationPoints.LONGITUDE, "FLOAT (10,6) NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.LocationPoints.TABLE);
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
