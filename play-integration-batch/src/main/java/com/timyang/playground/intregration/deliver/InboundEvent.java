package com.timyang.playground.intregration.deliver;

public class InboundEvent {

    private String id;
    private String body;
    private String time;
    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "InboundEvent{" +
                "id='" + id + '\'' +
                ", body='" + body + '\'' +
                ", time='" + time + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
