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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Jeff Butler
 */
public class XmlElement extends AbstractElement {
    
    private List<Attribute> attributes = new ArrayList<>();
    private List<AbstractElement> elements = new ArrayList<>();
    private String name;

    public XmlElement(String name) {
        super();
        this.name = name;
    }
    
    public Stream<Attribute> attributes() {
        return attributes.stream();
    }

    public void addAttribute(Attribute attribute) {
        attribute.parent = this;
        attributes.add(attribute);
    }

    public Stream<AbstractElement> elements() {
        return elements.stream();
    }

    public void addElement(AbstractElement element) {
        element.parent = this;
        elements.add(element);
    }

    public boolean hasElements() {
        return !elements.isEmpty();
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(XmlDomVisitor visitor) {
        if (visitor.visit(this)) {
            attributes.stream().forEach(a -> a.accept(visitor));
            elements.stream().forEach(e -> e.accept(visitor));
        }
    }
}
