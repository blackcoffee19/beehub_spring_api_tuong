package vn.aptech.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDto {
	private Integer id;
	private UserDto sender;
	private UserDto receiver;
	private GroupDto group;
	private String type;
	private boolean is_accept;
	private LocalDateTime create_at;
//	public RequirementDto(
//			Long sender_id,
//			String type,
//			LocalDate create_at
//			) {
//		this.sender_id = sender_id;
//		this.type=type;
//		this.create_at =create_at;
//	}
}
