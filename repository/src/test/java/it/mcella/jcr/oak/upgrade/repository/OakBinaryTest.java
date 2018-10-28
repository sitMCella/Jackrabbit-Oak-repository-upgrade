package it.mcella.jcr.oak.upgrade.repository;

import org.junit.Before;
import org.junit.Test;

import javax.jcr.Binary;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OakBinaryTest {

    private final Binary binary = mock(Binary.class);

    private OakBinary oakBinary;

    @Before
    public void setUp() throws Exception {
        oakBinary = new OakBinary(binary);
    }

    @Test
    public void shouldGetBinary() throws Exception {
        assertThat(oakBinary.getBinary(), is(binary));
    }

    @Test
    public void shouldGetBinaryInputStream() throws Exception {
        oakBinary.getInputStream();

        verify(binary).getStream();
    }

    @Test
    public void shouldDisposeBinary() throws Exception {
        oakBinary.dispose();

        verify(binary).dispose();
    }

}