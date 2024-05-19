package vn.aptech.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
	@NotEmpty(message = "Password can not be emty")
	private String password;
	@NotEmpty(message = "Fullname can not emty")
	private String fullname;
	@NotEmpty(message = "Email can not emty")
	private String email;
	@NotEmpty(message = "Gender is required")
	private String gender;
	@NotEmpty(message = "Phone is required")
	@Pattern(regexp = "^(84|0[35789])+([0-9]{8})$",message = "Phone is invalid")
	private String phone;
}
