package com.avvillas.infrastructure.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FilteredOutputStream extends OutputStream {
    private final OutputStream original;
    private final ByteArrayOutputStream buffer;

    public FilteredOutputStream(OutputStream original, ByteArrayOutputStream buffer) {
        this.original = original;
        this.buffer = buffer;
    }

    @Override
    public void write(int b) {
        buffer.write(b);
    }

    @Override
    public void flush() {}

@Override
public void close() throws IOException {
    buffer.flush();
    String xml = buffer.toString("UTF-8");

    System.out.println("⏳[SOAP ORIGINAL XML] =======================");
    System.out.println(xml);

    xml = xml.replaceFirst("<(ns\\d+):os_consultarFacturaEstandar", "<os:os_consultarFacturaEstandar");
    xml = xml.replaceFirst("</(ns\\d+):os_consultarFacturaEstandar", "</os:os_consultarFacturaEstandar");

    if (!xml.contains("xmlns:os=")) {
        xml = xml.replaceFirst("<os:os_consultarFacturaEstandar", "<os:os_consultarFacturaEstandar xmlns:os=\"http://organizacion.com/wsEstandar/\"");
    }

    xml = xml.replaceAll("<(/?)ns\\d+:", "<$1");
    xml = xml.replaceAll("xmlns:ns\\d+=\"[^\"]*\"", "");

    System.out.println("✅[SOAP MODIFICADO FINAL] =======================");
    System.out.println(xml);

    original.write(xml.getBytes("UTF-8"));
    original.flush();
    original.close();
}

}
