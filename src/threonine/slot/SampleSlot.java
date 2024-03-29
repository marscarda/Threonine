package threonine.slot;
//**************************************************************************
public class SampleSlot {
    long universeid = 0;
    long subsetid = 0;
    long sampleid = 0;
    long projectid = 0;
    //-------------------------------------------------------------
    Object sample = null;
    //=============================================================
    public void setUniverseId (long universeid) { this.universeid = universeid; }
    public void setSubsetId (long subsetid) { this.subsetid = subsetid; }
    public void setSampleId (long sampleid) { this.sampleid = sampleid; }
    public void setProjectId (long projectid) { this.projectid = projectid; }
    //-------------------------------------------------------------
    public void setSample (Object s) { sample = s; }
    //=============================================================
    public long universeID () { return universeid; }
    public long subsetID () { return subsetid; }
    public long sampleID () { return sampleid; }
    //-------------------------------------------------------------
    public Object getSample () { return sample; }
    //*************************************************************
}
//**************************************************************************