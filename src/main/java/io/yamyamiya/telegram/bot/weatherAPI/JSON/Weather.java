package io.yamyamiya.telegram.bot.weatherAPI.JSON;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "icon",
            "code",
            "description"
    })
    public class Weather {

        @JsonProperty("icon")
        private String icon;
        @JsonProperty("code")
        private Integer code;
        @JsonProperty("description")
        private String description;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        @JsonProperty("icon")
        public String getIcon() {
            return icon;
        }

        @JsonProperty("icon")
        public void setIcon(String icon) {
            this.icon = icon;
        }

        @JsonProperty("code")
        public Integer getCode() {
            return code;
        }

        @JsonProperty("code")
        public void setCode(Integer code) {
            this.code = code;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        @JsonProperty("description")
        public void setDescription(String description) {
            this.description = description;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

