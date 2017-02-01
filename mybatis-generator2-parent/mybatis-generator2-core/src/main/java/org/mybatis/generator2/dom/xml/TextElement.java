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

public class TextElement extends AbstractElement<TextElement> {
    
    private String content;

    private TextElement() {
        super();
    }

    public String content() {
        return content;
    }

    @Override
    public TextElement deepCopy() {
        return TextElement.of(content);
    }
    
    @Override
    public <S> S accept(XmlDomVisitor<S> visitor) {
        return visitor.visit(this);
    }

    public static TextElement of(String content) {
        TextElement textElement = new TextElement();
        textElement.content = content;
        return textElement;
    }
}
