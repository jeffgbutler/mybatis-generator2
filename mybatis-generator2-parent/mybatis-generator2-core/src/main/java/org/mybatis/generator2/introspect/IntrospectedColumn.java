package org.mybatis.generator2.introspect;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntrospectedColumn {

    private String columnName;
    private int dataType;
    private String typeName;
    private int columnSize;
    private boolean nullable;
    private int decimalDigits;
    private String remarks;
    private String columnDefault;
    private int ordinalPosition;
    private boolean isAutoIncrement;
    private boolean isGeneratedColumn;
    boolean isPrimaryKeyColumn;
    
    /**
     * -1 if not a primary key column
     */
    short keySequence = -1;
    
    private IntrospectedColumn() {
    }
    
    public boolean isPrimaryKeyColumn() {
        return isPrimaryKeyColumn;
    }

    public boolean isBlobColumn() {
        boolean isBlob;

        switch(dataType) {
        case Types.BINARY:
        case Types.BLOB:
        case Types.CLOB:
        case Types.LONGNVARCHAR:
        case Types.LONGVARBINARY:
        case Types.LONGVARCHAR:
        case Types.VARBINARY:
        case Types.NCLOB:
            isBlob = true;
            break;
            
        default:
            isBlob = false;
        }
        
        return isBlob;
    }
    
    public boolean isBaseColumn() {
        return !isBlobColumn() && !isPrimaryKeyColumn();
    }
    
    public String getColumnName() {
        return columnName;
    }

    public int getDataType() {
        return dataType;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public int getColumnSize() {
        return columnSize;
    }
    
    public boolean isNullable() {
        return nullable;
    }
    
    public int getDecimalDigits() {
        return decimalDigits;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public String getColumnDefault() {
        return columnDefault;
    }
    
    public int getOrdinalPosition() {
        return ordinalPosition;
    }
    
    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }
    
    public boolean isGeneratedColumn() {
        return isGeneratedColumn;
    }

    public static IntrospectedColumn fromCurrentFow(ResultSet rs, ResultSetCapabilities capabilities) throws SQLException {
        IntrospectedColumn introspectedColumn = new IntrospectedColumn();

        introspectedColumn.dataType = rs.getInt("DATA_TYPE"); //$NON-NLS-1$
        introspectedColumn.columnSize = rs.getInt("COLUMN_SIZE"); //$NON-NLS-1$
        introspectedColumn.columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
        introspectedColumn.nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable; //$NON-NLS-1$
        introspectedColumn.decimalDigits = rs.getInt("DECIMAL_DIGITS"); //$NON-NLS-1$
        introspectedColumn.remarks = rs.getString("REMARKS"); //$NON-NLS-1$
        introspectedColumn.columnDefault = rs.getString("COLUMN_DEF"); //$NON-NLS-1$
        introspectedColumn.ordinalPosition = rs.getInt("ORDINAL_POSITION"); //$NON-NLS-1$
        introspectedColumn.typeName = rs.getString("TYPE_NAME"); //$NON-NLS-1$

        if (capabilities.supportsIsAutoIncrement()) {
            introspectedColumn.isAutoIncrement = "YES".equals(rs.getString("IS_AUTOINCREMENT")); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (capabilities.supportsIsGeneratedColumn()) {
            introspectedColumn.isGeneratedColumn = "YES".equals(rs.getString("IS_GENERATEDCOLUMN")); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return introspectedColumn;
    }
}
