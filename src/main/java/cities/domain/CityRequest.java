package cities.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityRequest {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
