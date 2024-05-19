package vn.aptech.demo.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(mappedBy = "post_report", cascade = CascadeType.ALL)
	private List<Report> reports;
	
	@Nullable
	@OneToOne(mappedBy = "post")
	private UserSetting user_setting;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Nullable
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	
	@NotBlank
	private String text;
	@Nullable
	private String media;
	@NotNull
	private LocalDateTime create_at;
	
	public Post(
			String text,
			User user,
			LocalDateTime create_at
			) {
		this.text = text;
		this.user = user;
		this.create_at = create_at;		
	}
	@Override
	public String toString() {
		return "Post "+this.id+": "+this.text+"\t User: "+this.user.getUsername();
	}

}
