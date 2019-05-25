package com.pb138.brno;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "crimes")
public class BrnoDataEntities {
    @JacksonXmlProperty(localName = "crime")
    @JacksonXmlElementWrapper(useWrapping = false)
    private BrnoDataEntity[] crimes;

    public BrnoDataEntities(BrnoDataEntity[] crimes) {
        this.crimes = crimes;
    }


    public BrnoDataEntity[] getCrimes() {
        return crimes;
    }

    public void setCrimes(BrnoDataEntity[] crimes) {
        this.crimes = crimes;
    }
}
