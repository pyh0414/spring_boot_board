package kr.co.userinsight.edu.starter.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "freeboard")
@NoArgsConstructor
public class FreeBoard implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name="department_id")
  private User writer;

  @Column(nullable = false)
  private String title;

  private String text;

  private Date date;


}
