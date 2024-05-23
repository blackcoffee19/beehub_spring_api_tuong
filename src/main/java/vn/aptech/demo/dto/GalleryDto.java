package vn.aptech.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryDto {
	private Integer id;
	private Long user_id;
	private Long post_id;
	private String media;
	private String media_type;
	public GalleryDto(
			Long user_id,
			String media,
			String media_type) {
		this.user_id = user_id;
		this.media = media;
		this.media_type = media_type;
	}
}
