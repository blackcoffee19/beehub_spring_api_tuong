package vn.aptech.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
	private Integer id;
	private Long sender_id;
	private Long target_user_id;
	private Long target_group_id;
	private Long target_post_id;
	private Integer type_id;
	private String add_description;
	private LocalDateTime create_at;
	private LocalDateTime update_at;
	
	public ReportDto(
			Long sender_id,
			Integer type_id,
			LocalDateTime create_at,
			LocalDateTime update_at) {
		this.sender_id = sender_id;
		this.type_id =type_id;
		this.create_at = create_at;
		this.update_at =update_at;
	}
}
