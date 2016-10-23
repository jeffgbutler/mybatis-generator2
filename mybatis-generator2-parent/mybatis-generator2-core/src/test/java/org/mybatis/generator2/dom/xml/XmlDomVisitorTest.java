package org.mybatis.generator2.dom.xml;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class XmlDomVisitorTest {

    @Test
    public void testDefaultVisitorIsCalledCorrectly() {
        XmlDomVisitor visitor = spy(new XmlDomVisitor(){});
        Document document = DocumentTest.setupDocument();
        document.accept(visitor);
        
        verify(visitor, times(4)).visit(any(Attribute.class));
        verify(visitor, times(1)).visit(any(Document.class));
        verify(visitor, times(2)).visit(any(TextElement.class));
        verify(visitor, times(3)).visit(any(XmlElement.class));
    }

    @Test
    public void testVisitorIsStoppedAtDocument() {
        XmlDomVisitor visitor = spy(new XmlDomVisitor(){
            @Override
            public boolean visit(Document document) {
                return false;
            }
        });
        Document document = DocumentTest.setupDocument();
        document.accept(visitor);
        
        verify(visitor, times(0)).visit(any(Attribute.class));
        verify(visitor, times(1)).visit(any(Document.class));
        verify(visitor, times(0)).visit(any(TextElement.class));
        verify(visitor, times(0)).visit(any(XmlElement.class));
    }

    @Test
    public void testVisitorIsStoppedAtFirstXmlElement() {
        XmlDomVisitor visitor = spy(new XmlDomVisitor(){
            @Override
            public boolean visit(XmlElement xmlElement) {
                return false;
            }
        });
        Document document = DocumentTest.setupDocument();
        document.accept(visitor);
        
        verify(visitor, times(0)).visit(any(Attribute.class));
        verify(visitor, times(1)).visit(any(Document.class));
        verify(visitor, times(0)).visit(any(TextElement.class));
        verify(visitor, times(1)).visit(any(XmlElement.class));
    }

    @Test
    public void testVisitorIsNotStoppedByAttributesOrTextElements() {
        XmlDomVisitor visitor = spy(new XmlDomVisitor(){
            @Override
            public boolean visit(Attribute attribute) {
                return false;
            }
            @Override
            public boolean visit(TextElement textElement) {
                return false;
            }
        });
        Document document = DocumentTest.setupDocument();
        document.accept(visitor);
        
        verify(visitor, times(4)).visit(any(Attribute.class));
        verify(visitor, times(1)).visit(any(Document.class));
        verify(visitor, times(2)).visit(any(TextElement.class));
        verify(visitor, times(3)).visit(any(XmlElement.class));
    }
}
