package vn.aptech.demo.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
    @Size(max = 50)
    private String groupname;
	@Value("${some.key:true}")
	private boolean public_group;
	
	@Size(max = 250,min = 0)
	private String description;
	@Value("${some.key:true}")
	private boolean active;
	@NotNull
    private LocalDateTime created_at;
	@Nullable
	private String image_group;
	@Nullable
	private String background_group;
	
	@OneToMany(mappedBy = "group_receiver", cascade = CascadeType.ALL)
	private List<Requirement> requirements;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<GroupMember> group_members;
	
	@OneToMany(mappedBy = "group",cascade =  CascadeType.ALL)
	private List<Post> posts;
	
	public Group(
			String groupname,
			String description	
			){
		this.groupname = groupname;
		this.description = description;
		this.created_at = LocalDateTime.now();
	}
}
