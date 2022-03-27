package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.Alcyone;
import methionine.sql.MySQLEngine;
import methionine.sql.SQLCreateTable;
//**************************************************************************
public class TabsUniverse extends Alcyone {
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
        if (!checkTableExists(DBUniverse.UniverseTemplate.TABLE, tables)) createTemplateUniverse();
        if (!checkTableExists(DBUniverse.SubSets.TABLE, tables)) createSubSets();
        if (!checkTableExists(DBUniverse.SubsetTemplate.TABLE, tables)) createTemplateSubSets();
        if (!checkTableExists(DBUniverse.SubsetMapFeature.TABLE, tables)) createMapFeature();
        if (!checkTableExists(DBUniverse.SubsetMapFeatureTemplate.TABLE, tables)) createTemplateMapFeature();
        if (!checkTableExists(DBUniverse.LocationPoints.TABLE, tables)) createLocationPoints();
        if (!checkTableExists(DBUniverse.TemplateLocationPoints.TABLE, tables)) createTemplateLocationPoints();
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
        create.addUnique(DBUniverse.Universe.UNIVERSEID);
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
    private void createTemplateUniverse () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.UniverseTemplate.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.UniverseTemplate.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBUniverse.UniverseTemplate.NAME, "VARCHAR (100) NOT NULL");
        create.addField(DBUniverse.UniverseTemplate.DESCRIPTION, "VARCHAR (300) NOT NULL");
        create.addField(DBUniverse.UniverseTemplate.WEIGHTED, "INTEGER NOT NULL DEFAULT 0");
        create.addUnique(DBUniverse.UniverseTemplate.UNIVERSEID);
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
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
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
        create.addField(DBUniverse.SubSets.POPULATION, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.WEIGHT, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.MAPSTATUS, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.SUBSETCOST, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubSets.MAPCOST, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addUnique(DBUniverse.SubSets.SUBSETID);
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
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
    private void createTemplateSubSets () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.SubsetTemplate.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.SubsetTemplate.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetTemplate.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetTemplate.PARENTSUBSET, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetTemplate.NAME, "VARCHAR (50) NOT NULL");
        create.addField(DBUniverse.SubsetTemplate.POPULATION, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubsetTemplate.WEIGHT, "INTEGER NOT NULL DEFAULT 0");
        create.addField(DBUniverse.SubsetTemplate.MAPSTATUS, "INTEGER NOT NULL DEFAULT 0");
        create.addUnique(DBUniverse.SubsetTemplate.SUBSETID);
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
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
    private void createMapFeature () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.SubsetMapFeature.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.SubsetMapFeature.FEATUREID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetMapFeature.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetMapFeature.OBJTYPE, "INTEGER NOT NULL");
        create.addUnique(DBUniverse.SubsetMapFeature.FEATUREID);
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.SubsetMapFeature.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
    private void createTemplateMapFeature () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.SubsetMapFeatureTemplate.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.SubsetMapFeatureTemplate.FEATUREID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetMapFeatureTemplate.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.SubsetMapFeatureTemplate.FEATURETYPE, "INTEGER NOT NULL");
        create.addUnique(DBUniverse.SubsetMapFeatureTemplate.FEATUREID);
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.SubsetMapFeatureTemplate.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
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
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
    private void createTemplateLocationPoints () throws Exception {
        //-------------------------------------------------------------------
        SQLCreateTable create = new SQLCreateTable(DBUniverse.TemplateLocationPoints.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBUniverse.TemplateLocationPoints.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBUniverse.TemplateLocationPoints.FEATUREID, "BIGINT NOT NULL");
        create.addField(DBUniverse.TemplateLocationPoints.POINTINDEX, "INTEGER NOT NULL");
        create.addField(DBUniverse.TemplateLocationPoints.LATITUDE, "FLOAT (10,6) NOT NULL DEFAULT 0");
        create.addField(DBUniverse.TemplateLocationPoints.LONGITUDE, "FLOAT (10,6) NOT NULL DEFAULT 0");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBUniverse.TemplateLocationPoints.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
        //-------------------------------------------------------------------
    }
    //***********************************************************************
}
//***************************************************************************
