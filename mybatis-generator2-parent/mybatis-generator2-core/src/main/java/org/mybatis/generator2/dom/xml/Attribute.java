/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator2.dom.xml;

public class Attribute implements XmlDomNode, Comparable<Attribute>{
    
    private String name;
    private String value;
    XmlElement parent;

    public Attribute(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(XmlDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public XmlElement getParent() {
        return parent;
    }

    @Override
    public int compareTo(Attribute other) {
        int rc = compareString(this.name, other.name);
        if (rc == 0) {
            rc = compareString(this.value, other.value);
        }
        return rc;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        Attribute other = (Attribute) obj;
        return compareString(this.name, other.name) == 0 &&
                compareString(this.value, other.value) == 0;
    }

    private int compareString(String s1, String s2) {
        if (s1 == null) {
            return s2 == null ? 0 : -1;
        } else {
            return s2 == null ? 1 : s1.compareTo(s2);
        }
    }
}
