package threonine.slot;
//**************************************************************************
import methionine.AppException;
//**************************************************************************
public class SlotAtlas extends SlotQ1 {
    //**********************************************************************
    /**
     * Set a sample to a universe subset.
     * @param slot
     * @throws Exception 
     */
    public void setSample (SampleSlot slot) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        if (this.selectSubsetSlotCount(slot) != 0) this.updateSampleSlot(slot);
        else this.insertSlot(slot);
        //==================================================================
    }
    //**********************************************************************
    /**
     * 
     * @param universeid
     * @param subsetid
     * @return
     * @throws Exception 
     */
    public SampleSlot getSlot (long universeid, long subsetid) throws Exception {
        //==================================================================
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        return this.selectSlot(universeid, subsetid);
    }
    //**********************************************************************
    /**
     * Set a subset free of samples.
     * @param universeid
     * @param subsetid
     * @throws Exception 
     */
    public void freeSlot (long universeid, long subsetid) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        this.deleteSlots(universeid, subsetid, 0, 0);
        //==================================================================
    }
    //**********************************************************************
    /**
     * Clear all samples from a universe
     * @param universeid
     * @throws Exception 
     */
    public void clearSamples (long universeid) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        this.deleteSlots(universeid, 0, 0, 0);
        //==================================================================
    }
    //**********************************************************************
}
//**************************************************************************
