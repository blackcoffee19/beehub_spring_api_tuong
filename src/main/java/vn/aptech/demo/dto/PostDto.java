package vn.aptech.demo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private Long id;
	private String text;
	private String media;
	private Long user_id;
	private Long group_id;
	private LocalDateTime create_at;
	public PostDto(
			String text,
			Long user_id,
			LocalDateTime create_at
			) {
		this.text = text;
		this.user_id = user_id;
		this.create_at = create_at;		
	}
	@Override
	public String toString() {
		return "Post "+this.id+": "+this.text+"\tMedia: "+this.media;
	}
}
