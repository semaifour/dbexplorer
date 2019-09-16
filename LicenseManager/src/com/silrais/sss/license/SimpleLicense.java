package com.silrais.sss.license;

import com.silrais.sss.license.exception.LicenseFileNotFoundException;

public class SimpleLicense {

    protected String    typeName; //Evaluation, Restricted, Unlimitted
    protected int       typeId; //Evaluation, Restricted, Unlimitted
    protected String    clientName;
    protected int       duration; //days
    protected int       maxCNCSessions; //CNC - concurrent
    protected int       maxUNQSessions; //UNQ - Unique
    protected int       maxCMLHits; //CML - cumulative
    
    public SimpleLicense(SimpleLicenseContext lctx) throws
        LicenseFileNotFoundException {

    }
}
