package threonine.slot;
//**************************************************************************

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
}
//**************************************************************************
