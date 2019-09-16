package com.silrais.sss.license;

public class SimpleLicenseContext {

    protected String licResBundleName;
    
    public SimpleLicenseContext(String licResBundleName) {
        this.licResBundleName = licResBundleName;
    }

    public void setLicenseResourceBundleName(String licResBundleName) {
       this.licResBundleName = licResBundleName; 
    }


    public String getLicenseResourceBundleName() {
        return licResBundleName;
    }
    
}
