package com.hansholdings.basic.utils;

import freemarker.template.*;
import org.joda.time.base.AbstractInstant;

@SuppressWarnings("deprecation")
public class FreemarkerObjectWrapper extends DefaultObjectWrapper {
    
    @Override
    public TemplateModel wrap(final Object obj) throws TemplateModelException {
        if (obj instanceof AbstractInstant) return new SimpleDate(((AbstractInstant) obj).toDate(), TemplateDateModel.DATETIME);

        return super.wrap(obj);
    }
}
