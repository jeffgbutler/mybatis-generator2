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

/**
 * @author Jeff Butler
 */
public class Document extends XmlDomNode<Document> {
    
    private String publicId;
    private String systemId;
    private XmlElement rootElement;

    private Document() {
        super();
    }

    public XmlElement getRootElement() {
        return rootElement;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getSystemId() {
        return systemId;
    }

    @Override
    public void accept(XmlDomVisitor visitor) {
        if (visitor.visit(this)) {
            rootElement.accept(visitor);
        }
    }
    
    @Override
    public Document deepCopy() {
        return Document.of(publicId, systemId, rootElement.deepCopy());
    }

    public static Document of(XmlElement rootElement) {
        return of(null, null, rootElement);
    }
    
    public static Document of(String publicId, String systemId, XmlElement rootElement) {
        Document document = new Document();
        document.publicId = publicId;
        document.systemId = systemId;
        document.rootElement = rootElement;
        document.rootElement.parent = document;
        return document;
    }
}
