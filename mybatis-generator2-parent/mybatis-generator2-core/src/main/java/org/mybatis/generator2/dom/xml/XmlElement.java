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
public class XmlElement extends AbstractElement<XmlElement> {
    
    private List<Attribute> attributes = new ArrayList<>();
    private List<AbstractElement<?>> children = new ArrayList<>();
    private String name;

    private XmlElement() {
        super();
    }
    
    public Stream<Attribute> attributes() {
        return attributes.stream();
    }

    public Stream<AbstractElement<?>> children() {
        return children.stream();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public String getName() {
        return name;
    }

    public XmlElement withAttribute(Attribute attribute) {
        return new Builder()
                .withAttributes(attributes())
                .withAttribute(attribute)
                .withChildren(children())
                .withName(getName())
                .build();
    }
    
    public XmlElement withChild(AbstractElement<?> child) {
        return new Builder()
                .withAttributes(attributes())
                .withChildren(children())
                .withChild(child)
                .withName(getName())
                .build();
    }
    
    @Override
    public void accept(XmlDomVisitor visitor) {
        if (visitor.visit(this)) {
            attributes.stream().forEach(a -> a.accept(visitor));
            children.stream().forEach(e -> e.accept(visitor));
        }
    }
    
    @Override
    public XmlElement deepCopy() {
        return new Builder()
                .withName(name)
                .withAttributes(attributes().map(Attribute::deepCopy))
                .withChildren(children().map(AbstractElement::deepCopy))
                .build();
    }

    public static XmlElement of(String name) {
        return new Builder()
                .withName(name)
                .build();
    }
    
    public static class Builder {
        private XmlElement xmlElement = new XmlElement();
        
        public Builder withName(String name) {
            xmlElement.name = name;
            return this;
        }
        
        public Builder withAttributes(Stream<Attribute> attributes) {
            attributes.forEach(a -> {
                a.parent = xmlElement;
                xmlElement.attributes.add(a);
            });
            return this;
        }
        
        public Builder withAttribute(Attribute attribute) {
            attribute.parent = xmlElement;
            xmlElement.attributes.add(attribute);
            return this;
        }

        public Builder withChildren(Stream<AbstractElement<?>> children) {
            children.forEach(c -> {
                c.parent = xmlElement;
                xmlElement.children.add(c);
            });
            return this;
        }

        public Builder withChild(AbstractElement<?> child) {
            child.parent = xmlElement;
            xmlElement.children.add(child);
            return this;
        }
        
        public XmlElement build() {
            return xmlElement;
        }
    }
}
