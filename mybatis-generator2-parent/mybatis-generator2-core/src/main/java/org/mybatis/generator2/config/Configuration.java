package org.mybatis.generator2.config;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private List<Context> contexts = new ArrayList<>();

    public Configuration() {
        super();
    }

    public void addContext(Context context) {
        contexts.add(context);
    }
    
    public List<Context> contexts() {
        return contexts;
    }
}
