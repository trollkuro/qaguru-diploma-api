package models.favorite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavoriteRecordByIdResponseModel {

    private int id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("sub_id")
    private String subId;
    @JsonProperty("created_at")
    private String createdAt;
    Image image;

    @Data
    public static class Image {
        private String id;
        private String url;
    }
}
