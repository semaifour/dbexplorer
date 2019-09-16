package com.silrais.sss.license;

import javax.servlet.http.HttpServletRequest;

import com.silrais.sss.license.exception.InvalidLicenseException;

public class SimpleLicenseManager {

    SimpleLicense license;

    public SimpleLicenseManager(SimpleLicenseContext lctx) 
        throws InvalidLicenseException {
        license = new SimpleLicense(lctx);
    }

    public boolean checkLicense(HttpServletRequest request) 
        throws InvalidLicenseException {
        return true;
    }

}
