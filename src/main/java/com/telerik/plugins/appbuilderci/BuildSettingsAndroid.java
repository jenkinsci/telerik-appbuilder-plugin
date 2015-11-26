package com.telerik.plugins.appbuilderci;

import org.kohsuke.stapler.DataBoundConstructor;

public class BuildSettingsAndroid {
    public String codesigningIdentity;
    
    @DataBoundConstructor
    public BuildSettingsAndroid(
        String codesigningIdentityAndroid) {
        this.codesigningIdentity = codesigningIdentityAndroid;
    }
    
    public boolean isEmpty() { 
        return this.codesigningIdentity == null || this.codesigningIdentity.isEmpty();
    }
}