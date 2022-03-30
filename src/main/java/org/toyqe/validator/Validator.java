package org.toyqe.validator;

import net.sf.jsqlparser.schema.PrimitiveType;

public interface Validator {
    PrimitiveType validate() throws ValidateException;
}
