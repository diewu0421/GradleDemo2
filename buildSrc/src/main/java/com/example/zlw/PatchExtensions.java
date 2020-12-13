package com.example.zlw;

import org.codehaus.groovy.ast.GenericsType;

/**
 * 浙江集商优选电子商务有限公司
 *
 * @author zenglw
 * @date 12/14/20 12:40 AM
 */

class PatchExtensions {
    String name;
    boolean depOn;

    public PatchExtensions() {
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDepOn() {
        return depOn;
    }

    public void setDepOn(boolean depOn) {
        this.depOn = depOn;
    }
}
