package vn.aptech.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMediaDto {
	private Long id;
	private String media;
	private String media_type;
	private LocalDateTime create_at;
	private Long user_id;
	private Long group_id;
}
