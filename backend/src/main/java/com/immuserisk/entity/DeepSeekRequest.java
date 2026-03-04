package com.immuserisk.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeepSeekRequest {
    private String model;
    private List<Message> messages;

    public void setStream(boolean b) {
        
    }

    @Data
    public static class Message {
        private String role;
        private String content;

        @JsonCreator
        public Message(@JsonProperty("role") String role, @JsonProperty("content") String content) {
            this.role = role;
            this.content = content;
        }
    }
}