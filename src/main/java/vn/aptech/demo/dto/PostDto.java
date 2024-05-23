package vn.aptech.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private Long id;
	private String text;
	@Nullable
	private List<GalleryDto> media;
	private Long user_id;
	@Nullable
	private Long group_id;
	private LocalDateTime create_at;
	private String user_fullname;
	@Nullable
	private String user_image;
	private String user_gender;
	@Nullable
	private String group_name;
	@Nullable
	private boolean public_group;
	@Nullable
	private String group_image;
	private String setting_post;
	
	public PostDto(
			String text,
			Long user_id,
			LocalDateTime create_at
			) {
		this.text = text;
		this.user_id = user_id;
		this.create_at = create_at;		
	}
	public PostDto(
			Long id, 
			String text, 
			List<GalleryDto> media, 
			Long user_id, 
			LocalDateTime create_at, 
			String user_fullname, 
			String user_image, 
			String user_gender, 
			String setting_type ) {
		this.id = id;
		this.text = text;
		this.media = media;
		this.user_id = user_id;
		this.create_at = create_at;
		this.user_fullname = user_fullname;
		this.user_image = user_image;
		this.user_gender = user_gender;
		this.setting_post=setting_type;
	}
	@Override
	public String toString() {
		return "Post "+this.id+": "+this.text+"\tMedia: "+this.media;
	}
}
