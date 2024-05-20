package vn.aptech.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
	private Long id;
	private String groupname;
	private String description;
	private boolean is_active;
	private boolean is_public;
	private LocalDateTime created_at;
	private String image_group;
	private String background_group;
	public GroupDto(
			String groupname,
			String description	
			){
		this.groupname = groupname;
		this.description = description;
		this.created_at = LocalDateTime.now();
	}
}
