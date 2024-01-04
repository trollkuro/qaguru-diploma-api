package models.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddToFavoriteBodyModel {

    @JsonProperty("image_id")
    private String imageId;
}
