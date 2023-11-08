package io.yamyamiya.telegram.bot.weatherAPI.JSON;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "wind_cdir",
            "rh",
            "pod",
            "lon",
            "pres",
            "timezone",
            "ob_time",
            "country_code",
            "clouds",
            "vis",
            "wind_spd",
            "gust",
            "wind_cdir_full",
            "app_temp",
            "state_code",
            "ts",
            "h_angle",
            "dewpt",
            "weather",
            "uv",
            "aqi",
            "station",
            "sources",
            "wind_dir",
            "elev_angle",
            "datetime",
            "precip",
            "ghi",
            "dni",
            "dhi",
            "solar_rad",
            "city_name",
            "sunrise",
            "sunset",
            "temp",
            "lat",
            "slp"
    })
    public class Datum {

        @JsonProperty("wind_cdir")
        private String windCdir;
        @JsonProperty("rh")
        private Integer rh;
        @JsonProperty("pod")
        private String pod;
        @JsonProperty("lon")
        private Double lon;
        @JsonProperty("pres")
        private Double pres;
        @JsonProperty("timezone")
        private String timezone;
        @JsonProperty("ob_time")
        private String obTime;
        @JsonProperty("country_code")
        private String countryCode;
        @JsonProperty("clouds")
        private Integer clouds;
        @JsonProperty("vis")
        private Integer vis;
        @JsonProperty("wind_spd")
        private Double windSpd;
        @JsonProperty("gust")
        private Integer gust;
        @JsonProperty("wind_cdir_full")
        private String windCdirFull;
        @JsonProperty("app_temp")
        private Double appTemp;
        @JsonProperty("state_code")
        private String stateCode;
        @JsonProperty("ts")
        private Integer ts;
        @JsonProperty("h_angle")
        private Integer hAngle;
        @JsonProperty("dewpt")
        private Double dewpt;
        @JsonProperty("weather")
        private Weather weather;
        @JsonProperty("uv")
        private Integer uv;
        @JsonProperty("aqi")
        private Integer aqi;
        @JsonProperty("station")
        private String station;
        @JsonProperty("sources")
        private List<String> sources;
        @JsonProperty("wind_dir")
        private Integer windDir;
        @JsonProperty("elev_angle")
        private Integer elevAngle;
        @JsonProperty("datetime")
        private String datetime;
        @JsonProperty("precip")
        private Integer precip;
        @JsonProperty("ghi")
        private Double ghi;
        @JsonProperty("dni")
        private Integer dni;
        @JsonProperty("dhi")
        private Integer dhi;
        @JsonProperty("solar_rad")
        private Integer solarRad;
        @JsonProperty("city_name")
        private String cityName;
        @JsonProperty("sunrise")
        private String sunrise;
        @JsonProperty("sunset")
        private String sunset;
        @JsonProperty("temp")
        private Double temp;
        @JsonProperty("lat")
        private Double lat;
        @JsonProperty("slp")
        private Double slp;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        @JsonProperty("wind_cdir")
        public String getWindCdir() {
            return windCdir;
        }

        @JsonProperty("wind_cdir")
        public void setWindCdir(String windCdir) {
            this.windCdir = windCdir;
        }

        @JsonProperty("rh")
        public Integer getRh() {
            return rh;
        }

        @JsonProperty("rh")
        public void setRh(Integer rh) {
            this.rh = rh;
        }

        @JsonProperty("pod")
        public String getPod() {
            return pod;
        }

        @JsonProperty("pod")
        public void setPod(String pod) {
            this.pod = pod;
        }

        @JsonProperty("lon")
        public Double getLon() {
            return lon;
        }

        @JsonProperty("lon")
        public void setLon(Double lon) {
            this.lon = lon;
        }

        @JsonProperty("pres")
        public Double getPres() {
            return pres;
        }

        @JsonProperty("pres")
        public void setPres(Double pres) {
            this.pres = pres;
        }

        @JsonProperty("timezone")
        public String getTimezone() {
            return timezone;
        }

        @JsonProperty("timezone")
        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        @JsonProperty("ob_time")
        public String getObTime() {
            return obTime;
        }

        @JsonProperty("ob_time")
        public void setObTime(String obTime) {
            this.obTime = obTime;
        }

        @JsonProperty("country_code")
        public String getCountryCode() {
            return countryCode;
        }

        @JsonProperty("country_code")
        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        @JsonProperty("clouds")
        public Integer getClouds() {
            return clouds;
        }

        @JsonProperty("clouds")
        public void setClouds(Integer clouds) {
            this.clouds = clouds;
        }

        @JsonProperty("vis")
        public Integer getVis() {
            return vis;
        }

        @JsonProperty("vis")
        public void setVis(Integer vis) {
            this.vis = vis;
        }

        @JsonProperty("wind_spd")
        public Double getWindSpd() {
            return windSpd;
        }

        @JsonProperty("wind_spd")
        public void setWindSpd(Double windSpd) {
            this.windSpd = windSpd;
        }

        @JsonProperty("gust")
        public Integer getGust() {
            return gust;
        }

        @JsonProperty("gust")
        public void setGust(Integer gust) {
            this.gust = gust;
        }

        @JsonProperty("wind_cdir_full")
        public String getWindCdirFull() {
            return windCdirFull;
        }

        @JsonProperty("wind_cdir_full")
        public void setWindCdirFull(String windCdirFull) {
            this.windCdirFull = windCdirFull;
        }

        @JsonProperty("app_temp")
        public Double getAppTemp() {
            return appTemp;
        }

        @JsonProperty("app_temp")
        public void setAppTemp(Double appTemp) {
            this.appTemp = appTemp;
        }

        @JsonProperty("state_code")
        public String getStateCode() {
            return stateCode;
        }

        @JsonProperty("state_code")
        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        @JsonProperty("ts")
        public Integer getTs() {
            return ts;
        }

        @JsonProperty("ts")
        public void setTs(Integer ts) {
            this.ts = ts;
        }

        @JsonProperty("h_angle")
        public Integer gethAngle() {
            return hAngle;
        }

        @JsonProperty("h_angle")
        public void sethAngle(Integer hAngle) {
            this.hAngle = hAngle;
        }

        @JsonProperty("dewpt")
        public Double getDewpt() {
            return dewpt;
        }

        @JsonProperty("dewpt")
        public void setDewpt(Double dewpt) {
            this.dewpt = dewpt;
        }

        @JsonProperty("weather")
        public Weather getWeather() {
            return weather;
        }

        @JsonProperty("weather")
        public void setWeather(Weather weather) {
            this.weather = weather;
        }

        @JsonProperty("uv")
        public Integer getUv() {
            return uv;
        }

        @JsonProperty("uv")
        public void setUv(Integer uv) {
            this.uv = uv;
        }

        @JsonProperty("aqi")
        public Integer getAqi() {
            return aqi;
        }

        @JsonProperty("aqi")
        public void setAqi(Integer aqi) {
            this.aqi = aqi;
        }

        @JsonProperty("station")
        public String getStation() {
            return station;
        }

        @JsonProperty("station")
        public void setStation(String station) {
            this.station = station;
        }

        @JsonProperty("sources")
        public List<String> getSources() {
            return sources;
        }

        @JsonProperty("sources")
        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        @JsonProperty("wind_dir")
        public Integer getWindDir() {
            return windDir;
        }

        @JsonProperty("wind_dir")
        public void setWindDir(Integer windDir) {
            this.windDir = windDir;
        }

        @JsonProperty("elev_angle")
        public Integer getElevAngle() {
            return elevAngle;
        }

        @JsonProperty("elev_angle")
        public void setElevAngle(Integer elevAngle) {
            this.elevAngle = elevAngle;
        }

        @JsonProperty("datetime")
        public String getDatetime() {
            return datetime;
        }

        @JsonProperty("datetime")
        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        @JsonProperty("precip")
        public Integer getPrecip() {
            return precip;
        }

        @JsonProperty("precip")
        public void setPrecip(Integer precip) {
            this.precip = precip;
        }

        @JsonProperty("ghi")
        public Double getGhi() {
            return ghi;
        }

        @JsonProperty("ghi")
        public void setGhi(Double ghi) {
            this.ghi = ghi;
        }

        @JsonProperty("dni")
        public Integer getDni() {
            return dni;
        }

        @JsonProperty("dni")
        public void setDni(Integer dni) {
            this.dni = dni;
        }

        @JsonProperty("dhi")
        public Integer getDhi() {
            return dhi;
        }

        @JsonProperty("dhi")
        public void setDhi(Integer dhi) {
            this.dhi = dhi;
        }

        @JsonProperty("solar_rad")
        public Integer getSolarRad() {
            return solarRad;
        }

        @JsonProperty("solar_rad")
        public void setSolarRad(Integer solarRad) {
            this.solarRad = solarRad;
        }

        @JsonProperty("city_name")
        public String getCityName() {
            return cityName;
        }

        @JsonProperty("city_name")
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @JsonProperty("sunrise")
        public String getSunrise() {
            return sunrise;
        }

        @JsonProperty("sunrise")
        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        @JsonProperty("sunset")
        public String getSunset() {
            return sunset;
        }

        @JsonProperty("sunset")
        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        @JsonProperty("temp")
        public Double getTemp() {
            return temp;
        }

        @JsonProperty("temp")
        public void setTemp(Double temp) {
            this.temp = temp;
        }

        @JsonProperty("lat")
        public Double getLat() {
            return lat;
        }

        @JsonProperty("lat")
        public void setLat(Double lat) {
            this.lat = lat;
        }

        @JsonProperty("slp")
        public Double getSlp() {
            return slp;
        }

        @JsonProperty("slp")
        public void setSlp(Double slp) {
            this.slp = slp;
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

