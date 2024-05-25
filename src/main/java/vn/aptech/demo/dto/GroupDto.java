package vn.aptech.demo.dto;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
	private Long id;
	private String groupname;
	private boolean public_group;
	private String description;
	private boolean active;
	private LocalDateTime created_at;
	private String image_group;
	private String background_group;
	private boolean joined;
	@Nullable
	private String member_role;
	private int member_count;
	public GroupDto(
			String groupname,
			String description	
			){
		this.groupname = groupname;
		this.description = description;
		this.created_at = LocalDateTime.now();
	}
	public GroupDto(
			String groupname,
			String description,
			String image_group,
			LocalDateTime create_at,
			boolean public_group,
			boolean joined
			){
		this.groupname = groupname;
		this.description = description;
		this.image_group = image_group;
		this.created_at = create_at;
		this.public_group = public_group;
		this.joined = joined;
		

	}
}
