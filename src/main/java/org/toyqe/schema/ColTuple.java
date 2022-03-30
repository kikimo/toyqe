package org.toyqe.schema;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class ColTuple {
    public ColDef colDef;
    public PrimitiveValue value;

    public ColTuple(ColDef colDef, PrimitiveValue value) {
        this.colDef = colDef;
        this.value = value;
    }
}
