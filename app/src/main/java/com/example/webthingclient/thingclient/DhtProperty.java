package com.example.webthingclient.thingclient;

public class DhtProperty {
    float humidity;
    float temperature;

    public DhtProperty(float humidity, float temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
