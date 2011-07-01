package com.page5of4.scaffold;

import java.beans.IntrospectionException;

public interface MetadataResolver {

   ClassMetadata resolve(Class<?> objectClass) throws IntrospectionException;

}
