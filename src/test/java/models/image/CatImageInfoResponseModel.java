package specs.image;

import lombok.Data;

@Data
public class CatImageInfoResponseModel {
    private String id;
    private String url;
    private int width;
    private int height;
}
