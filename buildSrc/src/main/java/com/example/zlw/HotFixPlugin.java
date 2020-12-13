package com.example.zlw;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * 浙江集商优选电子商务有限公司
 *
 * @author zenglw
 * @date 12/14/20 12:34 AM
 */

public class HotFixPlugin implements Plugin<Project> {


    @Override
    public void apply(Project project) {
        System.out.println("1111111111111");
        project.getExtensions().create("patch", PatchExtensions.class);
//        new Test().test();

    }
}
