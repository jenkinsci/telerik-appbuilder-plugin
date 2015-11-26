package com.telerik.plugins.appbuilderci;

import org.kohsuke.stapler.DataBoundConstructor;

public class BuildSettingsiOS {
    public String mobileProvisionIdentifier;
    public String codesigningIdentity;
    
    @DataBoundConstructor
    public BuildSettingsiOS(
        String mobileProvisionIdentifieriOS,
        String codesigningIdentityiOS) {
        this.mobileProvisionIdentifier = mobileProvisionIdentifieriOS;
        this.codesigningIdentity = codesigningIdentityiOS;
    }
    
    public boolean isEmpty() { 
        return (this.codesigningIdentity == null || this.codesigningIdentity.isEmpty()) || 
               (this.mobileProvisionIdentifier == null || this.mobileProvisionIdentifier.isEmpty());
    }
}