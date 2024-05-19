package vn.aptech.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberDto {
	private int id;
	private Long user_id;
	private Long group_id;
	
	private String role;
	public GroupMemberDto(
			Long user_id,
			Long group_id,
			String role
			) {
		this.group_id = group_id;
		this.user_id = user_id;
		this.role = role;
	};

}
