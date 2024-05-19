package vn.aptech.demo.models;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requirements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	@Nullable
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private User receiver;
	@Nullable
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group_receiver;
	@NotNull
	private String type;
	@Value("${some.key:true}")
	private boolean is_accept;
	@NotNull
	private LocalDate create_at;
	
}
