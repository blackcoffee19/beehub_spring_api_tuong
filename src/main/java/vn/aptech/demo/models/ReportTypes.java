package vn.aptech.demo.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="report_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportTypes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String description;

	@OneToMany(mappedBy = "report_type", cascade = CascadeType.ALL)
	private List<Report> reports;
	
	public ReportTypes(String title, String deString) {
		this.title = title;
		this.description = deString;
	}
}
