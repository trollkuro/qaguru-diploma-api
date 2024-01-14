package models.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UploadCatImageResponseModel {
    private String id;
    private String url;
    private int width;
    private int height;
    @JsonProperty("original_filename")
    private String originalFilename;
    private int pending;
    private int approved;
}
