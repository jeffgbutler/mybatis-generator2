package org.mybatis.generator2.db.node;

import java.sql.Types;

public class IntrospectedColumn {

    String columnName;
    int dataType;
    String typeName;
    int columnSize;
    boolean nullable;
    int decimalDigits;
    String remarks;
    String columnDefault;
    int ordinalPosition;
    boolean isAutoIncrement;
    boolean isGeneratedColumn;
    boolean isPrimaryKeyColumn;
    
    /**
     * 0 if not a primary key column
     */
    short keySequence;
    
    IntrospectedColumn() {
    }
    
    public boolean isPrimaryKeyColumn() {
        return isPrimaryKeyColumn;
    }

    public boolean isBlobColumn() {
        return dataType == Types.BINARY
               || dataType == Types.BLOB
               || dataType == Types.CLOB
               || dataType == Types.LONGNVARCHAR
               || dataType == Types.LONGVARBINARY
               || dataType == Types.LONGVARCHAR
               || dataType == Types.VARBINARY
               || dataType == Types.NCLOB;
    }
    
    public boolean isBaseColumn() {
        return !isBlobColumn() && !isPrimaryKeyColumn();
    }
}
