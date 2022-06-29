package spring.framework.books.classes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Volumes")
public class BookVolume {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long volumeId;



    private String volume;

    private String fileUri;


    @ManyToOne
    @JoinColumn(name="volumeCode")
    @JsonIgnore
    private Book bookId;
}
