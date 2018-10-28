package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OakPropertyTypeTest {

    private static final int TYPE = 6;

    @Test
    public void shouldGetPropertyType() throws Exception {
        OakPropertyType oakPropertyType = new OakPropertyType(TYPE);

        int propertyType = oakPropertyType.getType();

        assertThat(propertyType, is(TYPE));
    }

}