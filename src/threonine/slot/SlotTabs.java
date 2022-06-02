package threonine.slot;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.Alcyone;
import methionine.sql.MySQLEngine;
import methionine.sql.SQLCreateTable;
//**************************************************************************
public class SlotTabs extends Alcyone {
    //**********************************************************************
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
        if (!checkTableExists(DBSlot.SampleSlot.TABLE, tables)) createSampleSlot();
    }
    //**********************************************************************
    private void createSampleSlot () throws Exception {
        SQLCreateTable create = new SQLCreateTable(DBSlot.SampleSlot.TABLE);
        create.setEngine(MySQLEngine.INNODB);
        create.addField(DBSlot.SampleSlot.UNIVERSEID, "BIGINT NOT NULL");
        create.addField(DBSlot.SampleSlot.SUBSETID, "BIGINT NOT NULL");
        create.addField(DBSlot.SampleSlot.SAMPLEID, "BIGINT NOT NULL");
        create.addField(DBSlot.SampleSlot.PROJECTID, "BIGINT NOT NULL");
        //-------------------------------------------------------------------
        PreparedStatement st = null;
        this.setDataBase();
        try {
            st = connection.prepareStatement(create.getText());
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder err = new StringBuilder("Failed to create ");
            err.append(DBSlot.SampleSlot.TABLE);
            err.append(" table\n");
            err.append(e.getMessage());
            throw new Exception(err.toString());
        }
        finally { try { if (st != null) st.close(); } catch (Exception e) {} }
        //-------------------------------------------------------------------
    }
    //**********************************************************************
    
    
    //**********************************************************************
}
//**************************************************************************