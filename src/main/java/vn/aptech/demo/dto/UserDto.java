package vn.aptech.demo.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	@NotEmpty(message = "Username can not be emty")
	private String username;
	@NotEmpty(message = "Fullname can not emty")
	private String fullname;
	@NotEmpty(message = "Gender is required")
	private String gender;
	@Nullable
	private String image;
}
