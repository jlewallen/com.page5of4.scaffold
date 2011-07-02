package com.page5of4.scaffold.metadata;

import java.beans.IntrospectionException;

public interface MetadataResolver {

   ClassMetadata resolve(Class<?> objectClass) throws IntrospectionException;

}
