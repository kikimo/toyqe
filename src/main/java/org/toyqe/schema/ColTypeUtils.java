package org.toyqe.schema;

import org.toyqe.engine.SqlException;

import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.create.table.ColDataType;

public class ColTypeUtils {
    public static PrimitiveType toPrimitiveType(ColDataType dataType) throws SqlException {
        switch (dataType.getDataType()) {
        case "string":
            return PrimitiveType.STRING;
        case "varchar":
            return PrimitiveType.STRING;
        case "char":
            return PrimitiveType.STRING;
        case "int":
            return PrimitiveType.LONG;
        case "decimal":
            return PrimitiveType.LONG;
        case "date":
            return PrimitiveType.DATE;
        default:
            throw new SqlException("unknown data type: " + dataType.getDataType());
        }
    }

    public static ColDataType tColDataType(PrimitiveType primitiveType) throws SqlException {
        switch (primitiveType) {
        case DATE: {
            ColDataType colDataType = new ColDataType();
            colDataType.setDataType("date");
            return colDataType;
        }
        case LONG: {
            ColDataType colDataType = new ColDataType();
            colDataType.setDataType("int");
            return colDataType;
        }
        case STRING: {
            ColDataType colDataType = new ColDataType();
            colDataType.setDataType("string");
            return colDataType;
        }
        case DOUBLE: {
            ColDataType colDataType = new ColDataType();
            colDataType.setDataType("decima");
            return colDataType;
        }
        case BOOL:
            throw new SqlException("unsupported type: bool");
        case TIME:
            throw new SqlException("unsupported type: double");
        case TIMESTAMP:
            throw new SqlException("unsupported type: double");
        default:
            throw new SqlException("unknon primitive type: " + primitiveType);
        }
    }
}
