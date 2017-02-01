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

import java.util.Optional;

/**
 * @author Jeff Butler
 */
public class Document extends XmlDomNode<Document> {
    
    private XmlElement rootElement;
    private ExternalDTD<?> externalDTD;

    private Document() {
        super();
    }

    public XmlElement rootElement() {
        return rootElement;
    }
    
    public Optional<ExternalDTD<?>> externalDTD() {
        return Optional.ofNullable(externalDTD);
    }

    @Override
    public Document deepCopy() {
        if (externalDTD == null) {
            return Document.of(rootElement.deepCopy());
        } else {
            return Document.of(externalDTD.deepCopy(), rootElement.deepCopy());
        }
    }

    @Override
    public <S> S accept(XmlDomVisitor<S> visitor) {
        return visitor.visit(this);
    }

    public static Document of(XmlElement rootElement) {
        Document document = new Document();
        document.rootElement = rootElement;
        document.rootElement.parent = document;
        return document;
    }
    
    public static Document of(ExternalDTD<?> externalDTD, XmlElement rootElement) {
        Document document = new Document();
        document.externalDTD = externalDTD;
        document.externalDTD.parent = document;
        document.rootElement = rootElement;
        document.rootElement.parent = document;
        return document;
    }
}
