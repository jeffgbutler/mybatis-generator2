package org.mybatis.generator2.dom.java;

public class ImportDefinition extends JavaDomNode implements Comparable<ImportDefinition> {
    private String typeName;
    private boolean isStatic;
    private boolean isWildcard;

    private ImportDefinition() {
        super();
    }

    @Override
    public void accept(JavaDomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public JavaNodeType getNodeType() {
        return JavaNodeType.IMPORT;
    }
    
    @Override
    public boolean allowsModifier(JavaModifier javaModifier) {
        return false;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isNonStatic() {
        return !isStatic;
    }

    public boolean isWildcard() {
        return isWildcard;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("import ");
        
        if (isStatic) {
            buffer.append("static ");
        }
        
        buffer.append(typeName);
        
        if (isWildcard) {
            buffer.append(".*");
        }
        
        buffer.append(';');
        
        return buffer.toString();
    }
    
    @Override
    public int compareTo(ImportDefinition other) {
        if (isStatic == other.isStatic) {
            return toString().compareTo(other.toString());
        } else {
            // follow the convention that statics come before non-statics
            return isStatic ? -1 : 1;
        }
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ImportDefinition)) {
            return false;
        }
        
        return this.toString().equals(other.toString());
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    public static ImportDefinition of(String typeName) {
        return of(typeName, false, false);
    }

    public static ImportDefinition of(String typeName, boolean isStatic, boolean isWildcard) {
        ImportDefinition importDefinition = new ImportDefinition();
        importDefinition.typeName = typeName;
        importDefinition.isStatic = isStatic;
        importDefinition.isWildcard = isWildcard;
        return importDefinition;
    }
}
